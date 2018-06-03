package com.mygdx.game.model;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.entities.*;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

/**
 * A model representing a game.
 */
public class GameModel {
    private static final int OBSTACLE_COUNT = 2;
    private static final int PLATFORM_COUNT = 15;
    private static final int BOOST_COUNT = 5;
    private static final float VERTICAL_DISTANCE_PLATFORM = 2;
    private static final int HORIZONTAL_DISTANCE_PLATFORM = 2;
    private static final int MIN_PLATFORMS_BETWEEN_OBSTACLES = 15;
    private static final int MAX_PLATFORMS_BETWEEN_OBSTACLES = 25;
    private static final int MIN_PLATFORMS_BETWEEN_BOOSTS = 20;
    private static final int MAX_PLATFORMS_BETWEEN_BOOSTS = 25;
    private static int PLAYERS_COUNT;

    private static GameModel instance;

    private List<PlayerModel> players;

    private Pool<PlatformModel> freePlatforms;
    private List<PlatformModel> platformsInUse;

    private Pool<ObstacleModel> freeObstacles;
    private List<ObstacleModel> obstaclesInUse;

    private Pool<BoostModel> freeBoosts;
    private List<BoostModel> boostsInUse;

    private LavaModel lava;

    private float maxPlatformY;
    private float platformX;
    private int platformsToNextObstacles;   //remaining platforms until the next obstacle appears
    private int platformsToNextBoosts;      //remaining platforms until the next boost appears
    private float obstacleY;

    /**
     * Constructs the game model, with its entities (players, platforms, obstacles and boosts)
     */
    private GameModel() {
        this.lava = new LavaModel();
        players = new ArrayList<PlayerModel>();
        platformsInUse = new ArrayList<PlatformModel>();
        obstaclesInUse = new ArrayList<ObstacleModel>();
        boostsInUse = new ArrayList<BoostModel>();
        freeObstacles = new Pool<ObstacleModel>(OBSTACLE_COUNT) {
            @Override
            protected ObstacleModel newObject() {
                return new ObstacleModel(0, 0, 0);
            }
        };
        freeBoosts = new Pool<BoostModel>(BOOST_COUNT) {
            @Override
            protected BoostModel newObject() {
                return new BoostModel();
            }
        };
        freePlatforms = new Pool<PlatformModel>() {
            @Override
            protected PlatformModel newObject() {
                return new PlatformModel(0, 0, 0);
            }
        };

        this.maxPlatformY = 0;
        this.platformX = WORLD_WIDTH / 2;
        this.platformsToNextObstacles = random(MIN_PLATFORMS_BETWEEN_OBSTACLES, MAX_PLATFORMS_BETWEEN_OBSTACLES);
        this.platformsToNextBoosts = random(MIN_PLATFORMS_BETWEEN_BOOSTS, MAX_PLATFORMS_BETWEEN_BOOSTS);
        this.obstacleY = 0;
        this.initializePlatforms();
        this.initializeObstacles();
        this.initializeBoosts();

        int x = 2;
        for (int i = 0; i < PLAYERS_COUNT; i++) {
            players.add(new PlayerModel(x, 50, 0, i + 1));
            x += 2;
        }

    }

    /**
     * Initializes the freeBoosts pool
     */
    private void initializeBoosts() {
        for (int i = 0; i < BOOST_COUNT; i++)
            this.freeBoosts.free(new BoostModel());

    }

    /**
     * Initializes the freePlatforms pool
     */
    private void initializePlatforms() {
        for (int i = 0; i < PLATFORM_COUNT; i++)
            this.freePlatforms.free(new PlatformModel());
    }

    /**
     * Initializes the freeObstacles pool
     */
    private void initializeObstacles() {
        for (int i = 0; i < OBSTACLE_COUNT; i++)
            this.freeObstacles.free(new ObstacleModel());
    }

    /**
     * Returns a singleton instance of the game model
     *
     * @return the singleton instance
     */
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }

    /**
     * Removes the specified model from the game model
     *
     * @param model The model which is out of the game
     */
    public void remove(EntityModel model) {
        if (model instanceof PlatformModel) {
            platformsInUse.remove(model);
            freePlatforms.free((PlatformModel) model);
            this.platformsToNextObstacles--;
            this.platformsToNextBoosts--;
            this.obstacleY = model.getY();
        } else if (model instanceof ObstacleModel) {
            obstaclesInUse.remove(model);
            freeObstacles.free((ObstacleModel) model);
        } else if (model instanceof BoostModel) {
            boostsInUse.remove(model);
            freeBoosts.free((BoostModel) model);
        }
        GameController.getInstance().remove(model);
    }

    /**
     * Updates the platforms position and checks if new boosts or obstacles should be created
     *
     * @param minCameraY The minimum value of camera's y section
     */
    private void updatePlatforms(float minCameraY) {
        //check platforms out of display
        EntityView ev = ViewFactory.makeView(AppView.game, new PlatformModel());

        //check platforms in use
        for (PlatformModel pm : new ArrayList<PlatformModel>(platformsInUse))
            pm.checkBounds(minCameraY);


        int numberFree = freePlatforms.getFree();
        for (int i = 0; i < numberFree; i++) {
            PlatformModel pm = freePlatforms.obtain();
            pm.setFlaggedForRemoval(false);
            maxPlatformY += VERTICAL_DISTANCE_PLATFORM;
            pm.setRandomX(platformX, HORIZONTAL_DISTANCE_PLATFORM);
            this.platformX = pm.getX();
            pm.setY(maxPlatformY);
            platformsInUse.add(pm);
            GameController.getInstance().add(pm);
            if (platformsToNextObstacles == 0)
                createNewObstacle();
            if (platformsToNextBoosts == 0)
                createNewBoost(pm.getX(), pm.getY());
        }
    }

    /**
     * Adds a new obstacle to the game model
     */
    private void createNewObstacle() {
        //check obstacles out of display
        EntityView ev = ViewFactory.makeView(AppView.game, new ObstacleModel());

        this.platformsToNextObstacles = random(MIN_PLATFORMS_BETWEEN_OBSTACLES, MAX_PLATFORMS_BETWEEN_OBSTACLES);
        ObstacleModel om = freeObstacles.obtain();
        om.setFlaggedForRemoval(false);
        om.setY(10 + obstacleY + ((ev.getSprite().getHeight() / 2) * PIXEL_TO_METER));
        om.setX(-(ev.getSprite().getWidth() / 2) * PIXEL_TO_METER);
        this.obstaclesInUse.add(om);
        GameController.getInstance().add(om);
    }

    /**
     * Adds a new boost to the game model
     *
     * @param x The new boost's x position
     * @param y The new boost's y position
     */
    private void createNewBoost(float x, float y) {
        ArrayList<BoostModel> boostModels = new ArrayList<BoostModel>(Arrays.asList(new FlyBoostModel(), new NoCollisionsBoostModel()));
        BoostModel boostModel = boostModels.get(random.nextInt(boostModels.size()));
        EntityView ev = ViewFactory.makeView(AppView.game, boostModel);
        float platformHeight = ViewFactory.getHeigth(AppView.game, new PlatformModel());

        this.platformsToNextBoosts = random(MIN_PLATFORMS_BETWEEN_BOOSTS, MAX_PLATFORMS_BETWEEN_BOOSTS);
        BoostModel bm = freeBoosts.obtain();
        bm = boostModel;
        bm.setFlaggedForRemoval(false);
        bm.setY(y + ((ev.getSprite().getHeight() / 2) * PIXEL_TO_METER) + ((platformHeight / 2) * PIXEL_TO_METER));
        bm.setX(x);
        this.boostsInUse.add(bm);
        GameController.getInstance().add(bm);
    }

    /**
     * Updates the obstacles' state
     *
     * @param minCameraY The minimum value of camera's y section
     */
    private void updateObstacles(float minCameraY) {
        //check obstacles in use
        for (ObstacleModel om : new ArrayList<ObstacleModel>(obstaclesInUse))
            om.checkBounds(minCameraY);
    }

    /**
     * Updates the boosts' state
     *
     * @param minCameraY The minimum value of camera's y section
     */
    private void updateBoosts(float minCameraY) {
        //check obstacles in use
        for (BoostModel om : new ArrayList<BoostModel>(boostsInUse))
            om.checkBounds(minCameraY);
    }

    /**
     * Updates the platforms, obstacles and boosts in the game
     *
     * @param minCameraY The minimum value of camera's y section
     */
    public void update(float minCameraY) {
        this.updatePlatforms(minCameraY);
        this.updateObstacles(minCameraY);
        this.updateBoosts(minCameraY);
    }

    /**
     * Deletes the current game model instance
     */
    public static void delete() {
        instance = null;
    }

    /**
     * Sets the PLAYERS_COUNT flag value
     *
     * @param playersCount The number of players in the current game
     */
    public static void setPlayersCount(int playersCount) {
        PLAYERS_COUNT = playersCount;
    }

    /**
     * @return The number of players in the current game
     */
    public static int getPlayersCount() {
        return PLAYERS_COUNT;
    }

    /**
     * @return The list of players in the current game
     */
    public List<PlayerModel> getPlayers() {
        return players;
    }

    /**
     * @return The list of platforms in use in the current game
     */
    public List<PlatformModel> getPlatformsInUse() {
        return platformsInUse;
    }

    /**
     * @return The list of obstacles in use in the current game
     */
    public List<ObstacleModel> getObstaclesInUse() {
        return obstaclesInUse;
    }

    /**
     * @return The list of boosts in use in the current game
     */
    public List<BoostModel> getBoostsInUse() {
        return boostsInUse;
    }

    /**
     * @return The pool of free platforms in the game
     */
    public Pool<PlatformModel> getFreePlatforms() {
        return freePlatforms;
    }

    /**
     * @return The pool of free obstacles in the game
     */
    public Pool<ObstacleModel> getFreeObstacles() {
        return freeObstacles;
    }

    /**
     * @return The pool of free boosts in the game
     */
    public Pool<BoostModel> getFreeBoosts() {
        return freeBoosts;
    }

    /**
     * @return The lava model
     */
    public LavaModel getLava() {
        return lava;
    }

    /**
     * @return The first player in the game
     */
    public PlayerModel getFirstPlayer() {
        if (!players.isEmpty())
            return players.get(0);
        else return null;
    }

    /**
     * Clears the list of platforms in use
     */
    public void erasePlatforms() {
        this.platformsInUse.clear();
    }
}