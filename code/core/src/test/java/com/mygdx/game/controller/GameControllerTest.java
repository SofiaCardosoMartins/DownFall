package com.mygdx.game.controller;


import com.mygdx.game.controller.entities.*;

import static com.badlogic.gdx.math.MathUtils.random;

import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.FlyBoostModel;
import com.mygdx.game.model.entities.NoCollisionsBoostModel;
import com.mygdx.game.model.entities.ObstacleModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;
import com.mygdx.game.view.entities.GameView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class GameControllerTest {

    GameController gameControlller;
    private static final int PLAYER_WIDTH = 128;
    private static final int PLAYER_HEIGHT = 128;
    private static final float VIEWPORT_WIDTH = GameController.WORLD_WIDTH / GameView.PIXEL_TO_METER;
    private static final float VIEWPORT_HEIGHT = 934;



    @Before
    public void setUp() throws Exception {
        gameControlller = GameController.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        GameModel.setPlayersCount(0);
        GameController.delete();
        GameModel.delete();
        gameControlller  =null;
    }

    @Test
    public void testGameControllerInit() {
        assertEquals(0, gameControlller.getAllControllers().size());
        assertNotNull(gameControlller.getWorld());
        assertNotNull(gameControlller.getLavaController());
    }

    @Test
    public void testPlayerStateMachine() {
        State idle = new Idle();
        State jump = new Jump();
        State fall = new Fall();
        State dead = new Dead();

        State result = idle.handleInput(GameController.Direction.UP);
        assertEquals(jump.getClass(), result.getClass());

        result = fall.handleInput(GameController.Direction.UP);
        assertEquals(fall.getClass(), result.getClass());

        result = dead.handleInput(GameController.Direction.UP);
        assertEquals(dead.getClass(), result.getClass());
    }

    @Test
    public void testAddController() {

        int randomNumber = random.nextInt(20) + 1;

        for (int i = 0; i < randomNumber; i++) {
            gameControlller.add(new PlatformModel());
            gameControlller.add(new ObstacleModel());
            gameControlller.add(new FlyBoostModel());
            gameControlller.add(new NoCollisionsBoostModel());
        }

        assertEquals(randomNumber, gameControlller.getPlatformControllers().size());
        assertEquals(randomNumber, gameControlller.getPlatformControllers().size());
        assertEquals(randomNumber, gameControlller.getPlatformControllers().size());
        assertEquals(randomNumber, gameControlller.getPlatformControllers().size());
    }

    private void checkFlaggedForRemoval(EntityModel entityModel) {
        EntityController entityController = gameControlller.add(entityModel);
        gameControlller.remove(entityModel);
        assertEquals(true, entityController.isFlaggedForRemoval());
    }

    @Test
    public void testRemoveController() {
        checkFlaggedForRemoval(new PlatformModel());
        checkFlaggedForRemoval(new ObstacleModel());
        checkFlaggedForRemoval(new FlyBoostModel());
        checkFlaggedForRemoval(new NoCollisionsBoostModel());
    }

    @Test
    public void testPlayerLeftWallCollision() {
        GameController.delete();
        GameModel.setPlayersCount(1);
        PlayerModel playerModel =  GameModel.getInstance().getFirstPlayer();
        playerModel.setPosition(0,0);
        gameControlller = GameController.getInstance();
        PlayerController playerController = gameControlller.getFirstPlayer();
        assertEquals(1,gameControlller.getPlayerControllers().size());
        gameControlller.checkLeftWallCollision(playerController,PLAYER_WIDTH);
        assertNotEquals(0,playerController.getX());
    }

    @Test
    public void testPlayerRightWallCollision() {
        GameController.delete();
        GameModel.setPlayersCount(1);
        PlayerModel playerModel =  GameModel.getInstance().getFirstPlayer();
        playerModel.setPosition(VIEWPORT_WIDTH + PLAYER_WIDTH,0);
        gameControlller = GameController.getInstance();
        PlayerController playerController = gameControlller.getFirstPlayer();
        assertEquals(1,gameControlller.getPlayerControllers().size());
        gameControlller.checkRightWallCollision(playerController,PLAYER_WIDTH);
        assertNotEquals(VIEWPORT_WIDTH + PLAYER_WIDTH,playerController.getY());
    }

    @Test
    public void testPlayerUpperWallCollision() {
        GameController.delete();
        GameModel.setPlayersCount(1);
        PlayerModel playerModel =  GameModel.getInstance().getFirstPlayer();
        playerModel.setPosition(VIEWPORT_WIDTH /2,VIEWPORT_HEIGHT + PLAYER_HEIGHT);
        gameControlller = GameController.getInstance();
        PlayerController playerController = gameControlller.getFirstPlayer();
        assertEquals(1,gameControlller.getPlayerControllers().size());
        gameControlller.checkUpWallCollision(playerController,VIEWPORT_HEIGHT,PLAYER_HEIGHT);
        assertNotEquals(VIEWPORT_HEIGHT + PLAYER_HEIGHT,playerController.getY());
    }
}