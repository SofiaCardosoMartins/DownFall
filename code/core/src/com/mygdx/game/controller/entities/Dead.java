package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

/**
 * A class to represent the dead state of the player
 */
public class Dead extends State {

    /**
     * Updates the player's state according to the direction of movement
     *
     * @param dir Direction of movement
     * @return The new state according to the direction of movement
     */
    @Override
    public State handleInput(GameController.Direction dir) {
        return this;
    }

    /**
     * Updates the player's state according to its velocity
     * @param vx Velocity in the x direction
     * @param vy Velocity in the y direction
     * @return The new state after the update
     */
    @Override
    public State update(float vx, float vy) {
        return this;
    }
}
