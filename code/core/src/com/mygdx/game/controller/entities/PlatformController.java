package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.PlatformModel;

public class PlatformController extends EntityController {

    public PlatformController(World world, PlatformModel platformModel) {
        super(world, platformModel, BodyDef.BodyType.KinematicBody,false);
        float density = 0.5f;
        float friction = 0.1f; //low friction
        float restitution = 0.0f; //no restitution
        int width = 171;
        int height = 30;

        createFixture(body, new float[]{
               0,0,0,30,171,30,171,0
        }, width, height, density, friction, restitution, PLATFORM_BITS, PLAYER_BITS);
    }
}
