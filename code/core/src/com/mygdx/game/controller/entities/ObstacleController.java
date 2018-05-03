package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.ObstacleModel;

public class ObstacleController extends EntityController {

    public ObstacleController(World world, ObstacleModel obstacleModel)
    {
        super(world,obstacleModel, BodyDef.BodyType.KinematicBody);
        float density = 0.5f;
        float friction = 0.4f;
        float restitution = 0.5f;
        int width = 75;
        int height = 75;
    }
}
