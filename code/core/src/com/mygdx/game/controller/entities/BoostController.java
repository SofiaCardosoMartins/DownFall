package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.World;

public class BoostController extends EntityController {

    public PlayerController(World world, BoostModel boostModel)
    {
        super(world,boostModel);
        float density = 0.5f;
        float friction = 0.4f;
        float restitution = 0.5f;
        int width = 75;
        int height = 75;
    }
}
