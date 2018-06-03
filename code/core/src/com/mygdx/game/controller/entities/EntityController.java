package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.EntityModel;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

/**
 * A class to represent the controller of a model
 */
public abstract class EntityController {
    protected Body body;
    protected int width;
    protected int height;
    protected boolean flaggedForRemoval;

    public static final short PLAYER_BITS = 1;
    public static final short PLATFORM_BITS = 2;
    public static final short OBSTACLE_BITS = 4;
    public static final short LAVA_BITS = 8;
    public static final short BOOST_BITS = 16;

    /**
     * Constructor without arguments of the EntityController class
     */
    EntityController(){

    }

    /**
     * Constructor with arguments of the EntityController class
     * @param world A Box2D world
     * @param model The model belonging to the controller
     * @param bodyType A Box2D BodyType
     * @param rotate True if the body should rotate and false otherwise
     */
    EntityController(World world, EntityModel model, BodyDef.BodyType bodyType, boolean rotate)
    {
        BodyDef bodyDef= new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(model.getX(),model.getY());
        bodyDef.fixedRotation = !rotate;
        body = world.createBody(bodyDef);
        body.setUserData(model);
        flaggedForRemoval = false;
    }

    /**
     * Helper method to create a polygon fixture represented by a set of vertexes.
     * @param body The body the fixture is to be attached to.
     * @param vertexes The vertexes defining the fixture in pixels so it is
     *                 easier to get them from a bitmap image.
     * @param width The width of the bitmap the vertexes where extracted from.
     * @param height The height of the bitmap the vertexes where extracted from.
     * @param density The density of the fixture. How heavy it is in relation to its area.
     * @param friction The friction of the fixture. How slippery it is.
     * @param restitution The restitution of the fixture. How much it bounces.
     * @param category
     * @param mask
     */
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

    /**
     * Wraps the getX method from the Box2D body class.
     *
     * @return the x-coordinate of this body.
     */
    public float getX()
    {
        return body.getPosition().x;
    }

    /**
     * Wraps the getY method from the Box2D body class.
     *
     * @return the y-coordinate of this body.
     */
    public float getY()
    {
        return body.getPosition().y;
    }

    /**
     * Returns the data member body
     * @return The data member body
     */
    public Body getBody()
    {
        return body;
    }

    /**
     * Wraps the setTransform method from the Box2D body class.
     *
     * @param x the new x-coordinate for this body
     * @param y the new y-coordinate for this body
     * @param angle the new rotation angle for this body
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /**
     * Returns the userdata of the body
     * @return Userdata of the data member body
     */
    public Object getUserData()
    {
        return body.getUserData();
    }

    /**
     * Handles a collision with the world's left wall
     */
    public  void leftWallCollision() {}

    /**
     * Handles a collision with the world's right wall
     */
    public  void rightWallCollision() {}

    /**
     * Sets the body's x position in the world
     * @param x The body's x position in the world
     */
    public void setX(float x)
    {
        this.body.setTransform(x,body.getPosition().y,body.getAngle());
    }

    /**
     * Sets the body's y position in the world
     * @param y The body's y position in the world
     */
    public void setY(float y)
    {
        this.body.setTransform(body.getPosition().x,y,body.getAngle());
    }

    /**
     * Sets the body's angle
     * @param angle The body's angle
     */
    public void setAngle(float angle)
    {
        this.body.setTransform(body.getPosition().x,body.getPosition().y,angle);
    }

    /**
     * Returns the maximum width of the body's fixtures
     * @return The maximum width of the body's fixtures
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the maximum width of the body's fixtures
     * @param width The maximum width of the body's fixtures
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the maximum height of the body's fixtures
     * @return The maximum height of the body's fixtures
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the maximum height of the body's fixtures
     * @param height The maximum height of the body's fixtures
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Updates the body's fixtures
     */
    public void update(){}

    /**
     * Returns true if the body should be removed from the world
     * @return True if the body should be removed from the world and false otherwise
     */
    public boolean isFlaggedForRemoval() {
        return flaggedForRemoval;
    }

    /**
     * Sets the value of the data member flaggedForRemoval
     * @param flaggedForRemoval True if the body should be removed from the world and false otherwise
     */
    public void setFlaggedForRemoval(boolean flaggedForRemoval) {
        this.flaggedForRemoval = flaggedForRemoval;
    }
}
