package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.ObstacleModel;


public class ObstacleController extends EntityController {

    private static final float DX = 0.05f;  //increment in the x direction

    public ObstacleController(World world, ObstacleModel obstacleModel)
    {
        super(world,obstacleModel, BodyDef.BodyType.KinematicBody,true);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 35, height = 35;

        createFixture(body, new float[]{
                0,23, 3,2, 27,2, 34,18, 17,33
        }, width, height, density, friction, restitution, OBSTACLE_BITS, PLAYER_BITS);
    }

    public void move()
    {
        //body.setLinearVelocity(new Vector2(5,0));
        //GameController.getInstance().getWorld().step(1 / 60f, 6, 2);
        //body.applyForceToCenter(1,0,true);
        body.setTransform(body.getPosition().x + DX, body.getPosition().y, 0);
    }
}
