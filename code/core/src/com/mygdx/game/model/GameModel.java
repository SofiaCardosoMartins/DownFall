package com.mygdx.game.model;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.ObstacleModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;

import java.util.List;

public class GameModel {
    private static final int PLATFORM_COUNT = 4;    //debtable
    private static int PLAYERS_COUNT;  //not final. Number of players might change across games

    private static GameModel instance;

    private List<PlayerModel> players;

    private Pool<PlatformModel> freePlatforms;
    private List<PlatformModel> platformsInUse;

    private Pool<ObstacleModel> freeObstacles;
    private List<ObstacleModel> obstaclesInUse;

    private GameModel() {

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