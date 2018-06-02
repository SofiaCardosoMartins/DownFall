package com.mygdx.game.model;

import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.view.entities.GameView;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest  {

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
        GameModel gameModel = GameModel.getInstance();

        assertEquals(1,gameModel.getPlayers().size());

        assertNotEquals(null,gameModel.getPlatformsInUse());
        assertNotEquals(0,gameModel.getFreePlatforms().getFree());

        assertNotEquals(null,gameModel.getObstaclesInUse());
        assertNotEquals(0,gameModel.getFreeObstacles().getFree());

        assertNotEquals(null,gameModel.getBoostsInUse());
        assertNotEquals(0,gameModel.getFreeBoosts().getFree());
    }

    @Test
    public void testUpdateMethod() {

    }
}