package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController.Direction;

/**
 * A class to represent the jump state of a player
 */
public class Jump extends State {

    /**
     * Updates the player's state given a new input from the user
     * @param dir The direction of the player's movement
     * @return The new state after the update
     */
    @Override
    public State handleInput(Direction dir) {
        return this;
    }

    /**
     * Updates the current state according to the player's velocity
     * @param vx Velocity in the x direction
     * @param vy Velocity in the y direction
     */
    @Override
    public State update(float vx, float vy) {
        if(vy < 0)
            return new Fall();
        else if (vy == 0)
            return new Idle();
        return this;
    }
}
