package com.mygdx.game.model;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.ObstacleModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;
import com.mygdx.game.view.entities.GameView;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class GameModel {
    private static final int PLATFORM_COUNT = 4;    //debatable
    private static int PLAYERS_COUNT;  //not final. Number of players might change across games

    private static GameModel instance;

    private List<PlayerModel> players;

    private Pool<PlatformModel> freePlatforms;
    private List<PlatformModel> platformsInUse;

    private Pool<ObstacleModel> freeObstacles;
    private List<ObstacleModel> obstaclesInUse;

    private GameModel() {
        players = new ArrayList<PlayerModel>();
        platformsInUse = new ArrayList<PlatformModel>();
        obstaclesInUse = new ArrayList<ObstacleModel>();
        //missing initialization of pools

        int height = 0, distance = 0;

        for(int i = 0; i< PLATFORM_COUNT; i++){
            do {
                height += random.nextInt(3) + 5;
                boolean plus = random.nextBoolean();
                if (plus)
                    distance += random.nextInt(10) + 10;
                else distance -= random.nextInt(10) + 10;
            } while (height > (WORLD_HEIGHT/PIXEL_TO_METER) || height < 0 || distance > (WORLD_WIDTH/PIXEL_TO_METER) || distance <0);
            //platformsInUse.add(new PlatformModel(distance, height,0));
            if (i==0)
                players.add(new PlayerModel(distance, height + 3,0));
        }
//platformsInUse.add(new PlatformModel(10,10));
//players.add(new PlayerModel(10,20));
//obstaclesInUse.add(new ObstacleModel(10,10));

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

    public void update(float delta)
    {

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