package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.EntityModel;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public abstract class EntityController {
    protected Body body;
    protected int width;
    protected int height;

    public static final short PLAYER_BITS = 1;
    public static final short PLATFORM_BITS = 2;
    public static final short OBSTACLE_BITS = 4;
    public static final short LAVA_BITS = 8;
    public static final short BOOST_BITS = 8;

    EntityController(World world, EntityModel model, BodyDef.BodyType bodyType, boolean rotate)
    {
        BodyDef bodyDef= new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(model.getX(),model.getY());
        bodyDef.fixedRotation = !rotate;
        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    final void createFixture(Body body, float[] vertexes, int width, int height, float density, float friction, float restitution, short category, short mask)
    {
        this.width = width;
        this.height = height;

        // Transform pixels into meters, center and invert the y-coordinate
      for (int i = 0; i < vertexes.length; i++) {
         if (i % 2 == 0) vertexes[i] -= width / 2;   // center the vertex x-coordinate
            if (i % 2 != 0) vertexes[i] -= height / 2;  // center the vertex y-coordinate

            if (i % 2 != 0) vertexes[i] *= -1;          // invert the y-coordinate

            vertexes[i] *= PIXEL_TO_METER;              // scale from pixel to meter
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertexes);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;

        body.createFixture(fixtureDef);


        polygon.dispose();
    }

    public float getX()
    {
        return body.getPosition().x;
    }

    public float getY()
    {
        return body.getPosition().y;
    }

    public Body getBody()
    {
        return body;
    }

    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    public Object getUserData()
    {
        return body.getUserData();
    }

    public void leftWallCollision(){}
    public void rightWallCollision(){}

    public void setX(float x)
    {
        this.body.setTransform(x,body.getPosition().y,body.getAngle());
    }

    public void setY(float y)
    {
        this.body.setTransform(body.getPosition().x,y,body.getAngle());
    }

    public void setAngle(float angle)
    {
        this.body.setTransform(body.getPosition().x,body.getPosition().y,angle);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void update(){}
}
