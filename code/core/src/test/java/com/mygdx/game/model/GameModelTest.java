package com.mygdx.game.model;

import com.mygdx.game.DownFall;
import com.mygdx.game.view.entities.GameView;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest  {

    //private static DownFall game;
    //private static GameView gameView;
/*
    private void init(int numPlayers)
    {
        game = new DownFall();
        gameView = new GameView(game, numPlayers);
    }
    */

    @Test
    public void testObjectsCreation() {
       // assertEquals(true, true);
       //init(1);
        GameModel gameModel = GameModel.getInstance();
        /*
        assertEquals(1,gameModel.getPlayers().size());

        assertNotEquals(null,gameModel.getPlatformsInUse());
        assertNotEquals(0,gameModel.getFreePlatforms().getFree());

        assertNotEquals(null,gameModel.getObstaclesInUse());
        assertNotEquals(0,gameModel.getFreeObstacles().getFree());

        assertNotEquals(null,gameModel.getBoostsInUse());
        assertNotEquals(0,gameModel.getFreeBoosts().getFree());
        */
    }

    @Test
    public void testUpdateMethod() {
       // init(1);

    }
}