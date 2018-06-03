package com.mygdx.game.controller.entities;

public interface BoostStrategy {
    /**
     * Moves the specified player to the right
     * @param player The player in which the movement is applied
     */
    void moveRight(PlayerController player);
    /**
     * Moves the specified player to the left
     * @param player The player in which the movement is applied
     */
    void moveLeft(PlayerController player);
    /**
     * Moves the specified player jump
     * @param player The player in which the jump is applied
     */
    void jump(PlayerController player);
    /**
     * Sets the mask bits of the specified player according to the type of boost
     * @param player The player in which the collisions are applied
     */
    void collisionHandler(PlayerController player);
    /**
     * Updates the remaining time for the boost to take effect
     */
    void updateRemainingTime();
    /**
     * Sets the data member lastTimeMeasurement
     * @param lastTimeMeasurement Last time since the boost's remaining time was measured
     */
    void setLastTimeMeasurement(long lastTimeMeasurement);
    /**
     * Returns the remaining time until a timeout occurs
     * @return Time left for a timeout
     */
    double getRemainingTime();
}
