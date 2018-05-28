package com.mygdx.game.controller.entities;

public interface BoostStrategy {
    void moveRight(PlayerController player);
    void moveLeft(PlayerController player);
    void jump(PlayerController player);
    void collisionHandler(PlayerController player);
    void updateRemainingTime();
    double getTime();
}
