package com.mygdx.game.model.entities;


import com.mygdx.game.controller.GameController;

import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;
/**
 *  A class representing a player entity in the game
 */
public class PlayerModel extends EntityModel {

    int num;
    double remainingTime;
    boolean boostPresent;

    /**
     * Constructs the player model
     *
     * @param x        The platform's x position
     * @param y        The platform's y position
     * @param rotation The platform's angle
     * @param num The player's number (either 1 or 2)
     */
    public PlayerModel(float x, float y, float rotation, int num)
    {
        super(x,y,rotation);
        this.num = num;
        this.remainingTime = 0;
        this.boostPresent = false;
    }

    @Override
    public ModelType getType() {
        switch (num)
        {
            case 1:
                return ModelType.PLAYER1;
            case 2:
                return ModelType.PLAYER2;
        }
        return null;
    }

    /**
     * Returns the player's number
     * @return The player's number
     */
    public int getNum() {
        return num;
    }

    /**
     * Sets the remaining time of a boost to take effect over the player
     * @param remainingTime The remaining time of a boost to take effect over the player
     */
    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Returns the remaining time of a boost to take effect over the player
     * @return The remaining time of a boost to take effect over the player
     */
    public double getRemainingTime() {
        return remainingTime;
    }

    /**
     * Returns true if the player is being affected by a boost
     * @return True if the player is being affected by a boost and false otherwise
     */
    public boolean isBoostPresent() {
        return boostPresent;
    }

    /**
     * Sets the value of the data member
     * @param boostPresent  True if the player is being affected by a boost and false otherwise
     */
    public void setBoostPresent(boolean boostPresent) {
        this.boostPresent = boostPresent;
    }

    /**
     * Sets the position of the player and checks if game has ended with success
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     */
    public void setPosition(float x, float y)
    {
        super.setPosition(x,y);
        if(y == WORLD_HEIGHT)
            GameController.getInstance().endGame(false);
    }
}
