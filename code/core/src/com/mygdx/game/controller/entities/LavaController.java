package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.EntityModel;

public class LavaController extends EntityController {

    public LavaController(World world, EntityModel model, BodyDef.BodyType bodyType, boolean rotate) {
        super(world, model, bodyType, rotate);
        float density = 0.5f;
        float friction = 0.1f; //low friction
        float restitution = 0.0f; //no restitution
        int width = 680;
        int height = 200;

        createFixture(body, new float[]{
                0,0,0,200,680,0,680,200
        }, width, height, density, friction, restitution, LAVA_BITS , PLAYER_BITS);
    }
}
