package com.mygdx.game.controller.entities;

public class NaturalBoost implements BoostStrategy {

    @Override
    public void moveRight(PlayerController player) {
        player.getBody().applyForceToCenter(50, 0, true);
    }

    @Override
    public void moveLeft(PlayerController player) {
        player.getBody().applyForceToCenter(-50, 0, true);
    }

    @Override
    public void jump(PlayerController player) {
        player.getBody().applyForceToCenter(0, 200, true);
    }

    @Override
    public void collisionHandler(PlayerController player) {

    }
}
