package com.mygdx.game.model;

import com.mygdx.game.DownFall;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {

    private static DownFall game;
    private static GameModel gameModel;

    private void init(int numPlayers)
    {
        game = new DownFall();
        GameModel.PLAYERS_COUNT = numPlayers;
        gameModel = GameModel.getInstance();
    }

    @Test
    public void testObjectsCreation() {
        init(1);
        assertNotEquals(null,gameModel.getPlayers());

        assertNotEquals(null,gameModel.getPlatformsInUse());
        assertNotEquals(0,gameModel.getFreePlatforms().getFree());

        assertNotEquals(null,gameModel.getObstaclesInUse());
        assertNotEquals(0,gameModel.getFreeObstacles().getFree());

        assertNotEquals(null,gameModel.getBoostsInUse());
        assertNotEquals(0,gameModel.getFreeBoosts().getFree());
    }

    @Test
    public void testUpdateMethod() {
        init(1);

    }
}