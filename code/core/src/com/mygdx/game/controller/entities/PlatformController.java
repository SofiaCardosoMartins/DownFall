package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.PlatformModel;

public class PlatformController extends EntityController {

    PlatformController(World world, PlatformModel platformModel)
    {
        super(world,platformModel, BodyDef.BodyType.KinematicBody);
        float density = 0.5f;
        float friction = 0.4f;
        float restitution = 0.5f;
        int width = 75;
        int height = 75;
    }
}
