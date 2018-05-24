package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BoostModel;


public class BoostController extends EntityController implements BoostStrategy {

    BoostController(World world, BoostModel boostModel)
    {
        super(world,boostModel,BodyDef.BodyType.DynamicBody,true);
        float density = 0.5f;
        float friction = 0.4f;
        float restitution = 0.5f;
        int width = 75;
        int height = 75;
    }

    @Override
    public void moveRight(PlayerController player) {

    }

    @Override
    public void moveLeft(PlayerController player) {

    }

    @Override
    public void jump(PlayerController player) {

    }

    @Override
    public void collisionHandler(PlayerController player) {

    }
}
