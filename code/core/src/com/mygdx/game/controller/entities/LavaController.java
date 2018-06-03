package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.EntityModel;

/**
 * A class to represent the controller of a lava model
 */
public class LavaController extends EntityController {

    /**
     * Constructor with arguments of the LavaController class
     * @param world A Box2D world
     * @param model The model belonging to the controller
     * @param bodyType A Box2D BodyType
     * @param rotate True if the body should rotate and false otherwise
     */
    public LavaController(World world, EntityModel model, BodyDef.BodyType bodyType, boolean rotate) {
        super(world, model, bodyType, rotate);
        float density = 0.5f;
        float friction = 0.1f; //low friction
        float restitution = 0.0f; //no restitution
        int width = 680;
        int height = 200;

        createFixture(body, new float[]{
                247,161,153,51,78,42,6,61,10,200,259,198
        }, width, height, density, friction, restitution, LAVA_BITS, PLAYER_BITS);

        createFixture(body, new float[]{
                259,198,330,161,284,122,247,161
        }, width, height, density, friction, restitution, LAVA_BITS, PLAYER_BITS);

        createFixture(body, new float[]{
                680,193,529,121,330,161,259,198
        }, width, height, density, friction, restitution, LAVA_BITS, PLAYER_BITS);

        createFixture(body, new float[]{
                680,193,673,25,592,44,529,121
        }, width, height, density, friction, restitution, LAVA_BITS, PLAYER_BITS);
    }
}
