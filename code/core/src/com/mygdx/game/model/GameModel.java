package com.mygdx.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
    public static int PLAYERS_COUNT;

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
        this.platformsToNextObstacles = random(MIN_PLATFORMS_BETWEEN_OBSTACLES,MAX_PLATFORMS_BETWEEN_OBSTACLES);
        this.platformsToNextBoosts = random(MIN_PLATFORMS_BETWEEN_BOOSTS,MAX_PLATFORMS_BETWEEN_BOOSTS);
        this.obstacleY = 0;
        this.initializePlatforms();
        this.initializeObstacles();
        this.initializeBoosts();

        int x = 2;
        for(int i = 0;i<PLAYERS_COUNT;i++){
            players.add(new PlayerModel(x,50,0,i+1));
             x+=2;
        }

    }

    private void initializeBoosts(){
        for (int i = 0; i < BOOST_COUNT; i++)
            this.freeBoosts.free(new BoostModel());

    }
    private void initializePlatforms() {
        for (int i = 0; i < PLATFORM_COUNT; i++)
            this.freePlatforms.free(new PlatformModel());
    }

    private void initializeObstacles()
    {
        for (int i = 0; i < OBSTACLE_COUNT; i++)
            this.freeObstacles.free(new ObstacleModel());
    }

    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }

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

    private void updatePlatforms(float minViewportY) {
        //check platforms out of display
        EntityView ev = ViewFactory.makeView(AppView.game, new PlatformModel());

        //check platforms in use
        for (PlatformModel pm : new ArrayList<PlatformModel>(platformsInUse))
            pm.checkBounds(minViewportY);


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
            if(platformsToNextObstacles == 0)
                createNewObstacle();
            if(platformsToNextBoosts == 0)
                createNewBoost(pm.getX(),pm.getY());
        }
    }

    private void createNewObstacle()
    {
        //check obstacles out of display
        EntityView ev = ViewFactory.makeView(AppView.game, new ObstacleModel());

        this.platformsToNextObstacles = random(MIN_PLATFORMS_BETWEEN_OBSTACLES,MAX_PLATFORMS_BETWEEN_OBSTACLES);
        ObstacleModel om = freeObstacles.obtain();
        om.setFlaggedForRemoval(false);
        om.setY(10+obstacleY + ((ev.getSprite().getHeight()/2)*PIXEL_TO_METER));
        om.setX(-(ev.getSprite().getWidth()/2) * PIXEL_TO_METER);
        this.obstaclesInUse.add(om);
        GameController.getInstance().add(om);
    }

    private void createNewBoost(float x, float y)
    {
        ArrayList<BoostModel> boostModels = new ArrayList<BoostModel>(Arrays.asList(new FlyBoostModel(), new NoCollisionsBoostModel()));
        BoostModel boostModel = boostModels.get(random.nextInt(boostModels.size()));
        EntityView ev = ViewFactory.makeView(AppView.game, boostModel);
        float platformHeight = ViewFactory.getHeigth(AppView.game,new PlatformModel());

        this.platformsToNextBoosts = random(MIN_PLATFORMS_BETWEEN_BOOSTS,MAX_PLATFORMS_BETWEEN_BOOSTS);
        BoostModel bm = freeBoosts.obtain();
        bm = boostModel;
        bm.setFlaggedForRemoval(false);
        bm.setY(y + ((ev.getSprite().getHeight()/2)*PIXEL_TO_METER) + ((platformHeight/2)*PIXEL_TO_METER));
        bm.setX(x);
        this.boostsInUse.add(bm);
        GameController.getInstance().add(bm);
    }

    private void updateObstacles(float minViewportY)
    {
        //check obstacles in use
        for (ObstacleModel om : new ArrayList<ObstacleModel>(obstaclesInUse))
            om.checkBounds(minViewportY);
    }

    private void updateBoosts(float minViewportY)
    {
        //check obstacles in use
        for (BoostModel om : new ArrayList<BoostModel>(boostsInUse))
            om.checkBounds(minViewportY);
    }

    public void update(float delta, float minViewportY) {
        this.updatePlatforms(minViewportY);
        this.updateObstacles(minViewportY);
        this.updateBoosts(minViewportY);
    }

    public static void delete()
    {
        instance = null;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public List<PlatformModel> getPlatformsInUse() {
        return platformsInUse;
    }

    public List<ObstacleModel> getObstaclesInUse() {
        return obstaclesInUse;
    }

    public List<BoostModel> getBoostsInUse() {
        return boostsInUse;
    }

    public Pool<PlatformModel> getFreePlatforms() {
        return freePlatforms;
    }

    public Pool<ObstacleModel> getFreeObstacles() {
        return freeObstacles;
    }

    public Pool<BoostModel> getFreeBoosts() {
        return freeBoosts;
    }

    public LavaModel getLava(){return lava;}
}