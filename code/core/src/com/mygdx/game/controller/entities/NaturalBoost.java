package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BoostModel;

public class NaturalBoost extends BoostController {


    NaturalBoost(World world, BoostModel boostModel) {
        super(world, boostModel);
    }

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
        player.getBody().applyForceToCenter(0, 800, true);
    }

    @Override
    public void collisionHandler(PlayerController player) {

    }
}
