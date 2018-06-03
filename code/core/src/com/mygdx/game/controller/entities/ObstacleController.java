package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.ObstacleModel;
/**
 * A class to represent the controller of an obstacle model
 */
public class ObstacleController extends EntityController {

    private static final float DX = 0.05f;  //increment in the x direction

    /**
     * Constructor with arguments of the EntityController class
     * @param world A Box2D world
     * @param obstacleModel The model belonging to the controller
     */
    public ObstacleController(World world, ObstacleModel obstacleModel)
    {
        super(world,obstacleModel, BodyDef.BodyType.KinematicBody,true);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 70, height = 70;

        createFixture(body, new float[]{
                57,34,56,25,42,12
        }, width, height, density, friction, restitution, OBSTACLE_BITS, PLAYER_BITS);

        createFixture(body, new float[]{
                42,12,28,9,18,10,8,22,9,34,11,47
        }, width, height, density, friction, restitution, OBSTACLE_BITS, PLAYER_BITS);

        createFixture(body, new float[]{
                11,47,22,57,31,61,51,57,61,47,57,34,42,12
        }, width, height, density, friction, restitution, OBSTACLE_BITS, PLAYER_BITS);
    }

    /**
     * Applies movement to the obstacle (only in the x direction)
     */
    public void move()
    {
        body.setTransform(body.getPosition().x + DX, body.getPosition().y, 0);
    }
}
