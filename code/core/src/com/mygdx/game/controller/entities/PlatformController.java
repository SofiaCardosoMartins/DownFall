package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.PlatformModel;

/**
 * A class to represent the controller of a platform model
 */
public class PlatformController extends EntityController {
    /**
     * Constructor with arguments of the EntityController class
     * @param world A Box2D world
     * @param platformModel The model belonging to the controller
     */
    public PlatformController(World world, PlatformModel platformModel) {
        super(world, platformModel, BodyDef.BodyType.KinematicBody,false);
        float density = 0.1f;
        float friction = 1f; //low friction
        float restitution = 0.0f; //no restitution
        int width = 170;
        int height = 170;

        createFixture(body, new float[]{
            3,151,13,165,158,166,165,155,166,143,5,142
        }, width, height, density, friction, restitution, PLATFORM_BITS, PLAYER_BITS);
    }
}
