package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BoostModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.view.entities.EntityView;

public class FlyBoost extends BoostController {


    FlyBoost(World world, BoostModel boostModel) {
        super(world, boostModel);
        //createFixture
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
        player.getBody().applyForceToCenter(0, 200, true);
    }

    @Override
    public void collisionHandler(PlayerController player) {

    }
}
