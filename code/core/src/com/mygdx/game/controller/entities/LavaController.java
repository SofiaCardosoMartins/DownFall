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
        }, width, height, density, friction, restitution, LAVA_BITS, PLAYER_BITS);

/*

        createFixture(body, new float[]{
                249,190,153,155,73,151,6,161,1,200,250,199
        }, width, height, density, friction, restitution, LAVA_BITS , PLAYER_BITS);

        createFixture(body, new float[]{
               293,199,332,190,278,177,249,190,250,199
        }, width, height, density, friction, restitution, LAVA_BITS , PLAYER_BITS);

        createFixture(body, new float[]{
                679,198,546,172,332,190,293,199
        }, width, height, density, friction, restitution, LAVA_BITS , PLAYER_BITS);

        createFixture(body, new float[]{
               679,198,677,147,594,149,546,172
        }, width, height, density, friction, restitution, LAVA_BITS , PLAYER_BITS);
        */

    }
}
