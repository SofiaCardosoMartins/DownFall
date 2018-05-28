package com.mygdx.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.entities.*;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class GameModel {
    private static final int OBSTACLE_COUNT = 2;
    private static final int PLATFORM_COUNT = 15;    //debatable
    private static final int BOOST_COUNT = 1;
    private static final float VERTICAL_DISTANCE_PLATFORM = 3f;
    private static final int HORIZONTAL_DISTANCE_PLATFORM = 2;
    private static final int MIN_PLATFORMS_BETWEEN_OBSTACLES = 1;
    private static final int MAX_PLATFORMS_BETWEEN_OBSTACLES = 1;
    private static int PLAYERS_COUNT;  //not final. Number of players might change across games

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
    private int platformsToNextObstacles;   //remaing platforms until the next obstacle appears
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
                return new BoostModel(0, 0, 0);
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
        this.obstacleY = 0;
        this.initializePlatforms();
        this.initializeObstacles();
        this.initializeBoosts();

        BoostModel bm = freeBoosts.obtain();
        bm.setPosition(5, 10);
        boostsInUse.add(bm);
      players.add(new PlayerModel(5, 50, 0));
      // players.add(new PlayerModel(2, 50, 0));

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
            this.obstacleY = model.getY() + 10;
        } else if (model instanceof ObstacleModel) {
            obstaclesInUse.remove(model);
            freeObstacles.free((ObstacleModel) model);
        } else if (model instanceof BoostModel) {
        boostsInUse.remove(model);
        freeBoosts.free((BoostModel) model);
    }
    }

    private void updatePlatforms(OrthographicCamera camera) {
        //check platforms out of display
        EntityView ev = ViewFactory.makeView(AppView.game, new PlatformModel());
        float minCameraY = PIXEL_TO_METER * (camera.position.y - (camera.viewportHeight / 2));
        float maxCameraY = PIXEL_TO_METER * (camera.position.y + (camera.viewportHeight / 2));

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
            if(platformsToNextObstacles == 0){
                createNewObstacle();
            }
        }
    }

    private void createNewObstacle()
    {
        //check obstacles out of display
        EntityView ev = ViewFactory.makeView(AppView.game, new ObstacleModel());

        this.platformsToNextObstacles = random(MIN_PLATFORMS_BETWEEN_OBSTACLES,MAX_PLATFORMS_BETWEEN_OBSTACLES);
        ObstacleModel om = freeObstacles.obtain();
        om.setFlaggedForRemoval(false);
        om.setY(obstacleY + ((ev.getSprite().getHeight()/2)*PIXEL_TO_METER));
        om.setX(-(ev.getSprite().getWidth()/2) * PIXEL_TO_METER);
        this.obstaclesInUse.add(om);
    }

    private void updateObstacles(OrthographicCamera camera)
    {
        //check obstacles in use
        for (ObstacleModel om : new ArrayList<ObstacleModel>(obstaclesInUse))
            om.checkBounds(GameController.getInstance().getMinCameraY(camera));
    }

    public void update(float delta, OrthographicCamera camera) {

        this.updatePlatforms(camera);
        this.updateObstacles(camera);
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

    public LavaModel getLava(){return lava;}
}