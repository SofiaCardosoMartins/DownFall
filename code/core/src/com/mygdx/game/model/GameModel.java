package com.mygdx.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.ObstacleModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.GameView;
import com.mygdx.game.view.entities.ViewFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class GameModel {
    private static final int PLATFORM_COUNT = 8;    //debatable
    private static final float VERTICAL_DISTANCE_PLATFORM = 1.5f;
    private static final int HORIZONTAL_DISTANCE_PLATFORM = 2;
    private static int PLAYERS_COUNT;  //not final. Number of players might change across games

    private static GameModel instance;

    private List<PlayerModel> players;

    private Pool<PlatformModel> freePlatforms;
    private List<PlatformModel> platformsInUse;

    private Pool<ObstacleModel> freeObstacles;
    private List<ObstacleModel> obstaclesInUse;

    private float maxPlatformY;
    private float minPlatformY;
    private float platformX;

    private GameModel() {
        players = new ArrayList<PlayerModel>();
        platformsInUse = new ArrayList<PlatformModel>();
        obstaclesInUse = new ArrayList<ObstacleModel>();
        freeObstacles = new Pool<ObstacleModel>(PLATFORM_COUNT) {
            @Override
            protected ObstacleModel newObject() {
                return new ObstacleModel(0, 0, 0);
            }
        };
        freePlatforms = new Pool<PlatformModel>() {
            @Override
            protected PlatformModel newObject() {
                return new PlatformModel(0, 0, 0);
            }
        };
        this.maxPlatformY = 0;
        this.minPlatformY = 0;
        this.platformX = WORLD_WIDTH / 2;
        this.initializePlatforms();

        players.add(new PlayerModel(10, 20, 0));

    }

    private void initializePlatforms() {
        for (int i = 0; i < PLATFORM_COUNT; i++)
            this.freePlatforms.free(new PlatformModel());
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
        } else if (model instanceof ObstacleModel) {
            obstaclesInUse.remove(model);
            freeObstacles.free((ObstacleModel) model);
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
            platformsInUse.add(pm);
            maxPlatformY += VERTICAL_DISTANCE_PLATFORM;
            pm.setRandomX(platformX, HORIZONTAL_DISTANCE_PLATFORM);
            this.platformX = pm.getX();
            pm.setY(maxPlatformY);
        }
    }

    public void update(float delta, OrthographicCamera camera) {
        this.updatePlatforms(camera);
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
}