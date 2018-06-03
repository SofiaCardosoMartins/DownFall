package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

/**
 * The fall state of a player
 */
public class Fall extends State {

    /**
     * Updates the player's state given a new input from the user
     * @param dir The direction of the player's movement
     * @return The new state after the update
     */
    @Override
    public State handleInput(GameController.Direction dir) {
        return this;
    }

    /**
     * Updates the current state according to the player's velocity
     * @param vx Velocity in the x direction
     * @param vy Velocity in the y direction
     */
    @Override
    public State update(float vx, float vy) {
        if ((vy < 0.002) && (vy > -0.002))
            return new Idle();
        else return this;
    }
}
