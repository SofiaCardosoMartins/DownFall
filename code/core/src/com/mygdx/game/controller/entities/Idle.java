package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

import static com.mygdx.game.controller.GameController.Direction.*;

/**
 * A class to represent the idle state of a player
 */
public class Idle extends State {

    /**
     * Updates the player's state given a new input from the user
     * @param dir The direction of the player's movement
     * @return The new state after the update
     */
    @Override
    public State handleInput(GameController.Direction dir) {
        if (dir == GameController.Direction.UP)
            return new Jump();
        else return this;
    }

    /**
     * Updates the current state according to the player's velocity
     * @param vx Velocity in the x direction
     * @param vy Velocity in the y direction
     */
    @Override
    public State update(float vx, float vy) {

        if(vy < -0.1)
            return new Fall();
        return this;
    }
}
