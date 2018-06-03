package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

/**
 * A class to represent the state of a player
 */
public abstract class State {
    /**
     * Updates the player's state given a new input from the user
     * @param dir The direction of the player's movement
     * @return The new state after the update
     */
    public abstract State handleInput(GameController.Direction dir);

    /**
     * Updates the current state according to the player's velocity
     * @param vx Velocity in the x direction
     * @param vy Velocity in the y direction
     * @return The new state after the update
     */
    public abstract State update(float vx, float vy);
}
