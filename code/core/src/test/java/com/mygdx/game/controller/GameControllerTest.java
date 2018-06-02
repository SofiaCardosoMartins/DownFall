package com.mygdx.game.controller;


import com.mygdx.game.controller.entities.*;
import com.mygdx.game.model.GameModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameControllerTest {

    @Test
    public void testControllerCreation() {
        GameController gc = GameController.getInstance();
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




}