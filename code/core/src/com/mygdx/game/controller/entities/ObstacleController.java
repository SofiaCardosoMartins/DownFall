package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.ObstacleModel;

public class ObstacleController extends EntityController {

    public ObstacleController(World world, ObstacleModel obstacleModel)
    {
        super(world,obstacleModel, BodyDef.BodyType.KinematicBody,false);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 75, height = 75;

        // Fixture needs to be convex so we need two of them.
        createFixture(body, new float[]{
                0,33, 14,14, 46,7, 27,52, 7,52
        }, width, height, density, friction, restitution, OBSTACLE_BITS, PLAYER_BITS);

        createFixture(body, new float[]{
                46,7, 27,52, 45,67, 74,55, 71,29
        }, width, height, density, friction, restitution, OBSTACLE_BITS, PLAYER_BITS);
    }
}
