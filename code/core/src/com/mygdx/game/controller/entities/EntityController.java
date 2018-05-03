package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.EntityModel;

public abstract class EntityController {
    Body body;
    public static final short PLAYER_BITS = 1;  //d
    public static final short PLATFORM_BITS = 2;    //k
    public static final short OBSTACLE_BITS = 4;    //k
    public static final short BOOST_BITS = 8;  //s

    EntityController(World world, EntityModel model, BodyDef.BodyType bodyType)
    {
        BodyDef bodyDef= new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(model.getX(),model.getY());

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    final void createFixture(Body body, float[] vertexes, int width, int height, float density, float friction, float restitution, short category, short mask)
    {

    }

    public float getX()
    {
        return body.getPosition().x;
    }

    public float getY()
    {
        return body.getPosition().y;
    }

    public Object getUserData()
    {
        return body.getUserData();
    }
}
