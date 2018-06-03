package com.mygdx.game.model;

import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.entities.*;
import com.mygdx.game.view.entities.GameView;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest  {

    private static DownFall game;
    private static GameModel gameModel;

    private void init(int numPlayers)
    {
        game = new DownFall();
        GameModel.setPlayersCount(numPlayers);
        gameModel = GameModel.getInstance();
    }

    @Test
    public void testGameModel() {
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
    public void testPlayerModel() {
        PlayerModel p = new PlayerModel(0, 1, 2, 2);

        assertEquals(2, p.getNum());

        assertEquals(0, p.getX(), 0);
        assertEquals(1, p.getY(), 0);
        p.setPosition(4, 5);
        assertEquals(4, p.getX(), 0);
        assertEquals(5, p.getY(), 0);

        assertEquals(EntityModel.ModelType.PLAYER2, p.getType());

        assertEquals(0, p.getRemainingTime(), 0.0f);
        p.setRemainingTime(1);
        assertEquals(1, p.getRemainingTime(), 0.0f);
    }

    @Test
    public void testEntityModel() {
        EntityModel em = new BoostModel();

        assertEquals(0, em.getX(), 0.0f);
        assertEquals(0, em.getX(), 0.0f);
        assertEquals(0, em.getRotation(), 0.0f);
        em.setX(2);
        em.setY(3);
        em.setRotation(2);
        assertEquals(2, em.getX(), 0.0f);
        assertEquals(3, em.getY(), 0.0f);
        assertEquals(2, em.getRotation(), 0.0f);

        assertEquals(false, em.isFlaggedForRemoval());
        em.setFlaggedForRemoval(true);
        assertEquals(true, em.isFlaggedForRemoval());
    }

    @Test
    public void testGetType(){
        FlyBoostModel fm = new FlyBoostModel();
        LavaModel lm = new LavaModel();
        NoCollisionsBoostModel nm = new NoCollisionsBoostModel();
        ObstacleModel om =  new ObstacleModel();
        PlatformModel pm = new PlatformModel();
        BoostModel bm = new BoostModel();

        assertEquals(EntityModel.ModelType.NATURAL_BOOST, bm.getType());
        assertEquals(EntityModel.ModelType.FLY_BOOST, fm.getType());
        assertEquals(EntityModel.ModelType.LAVA, lm.getType());
        assertEquals(EntityModel.ModelType.NO_COLLISIONS_BOOST, nm.getType());
        assertEquals(EntityModel.ModelType.OBSTACLE, om.getType());
        assertEquals(EntityModel.ModelType.PLATFORM, pm.getType());
    }


}