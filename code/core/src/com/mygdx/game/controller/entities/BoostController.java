package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BoostModel;
import com.mygdx.game.model.entities.EntityModel;

/**
 * A class to represent the boost controllers
 * @see EntityController
 * @see BoostStrategy
 */
public abstract class BoostController extends EntityController implements BoostStrategy {

    protected static final float ACTIVE_TIME = 100000;  //in seconds
    protected static final float UP_FORCE =  200;
    protected static final float SIDE_FORCE = 5;

    long lastTimeMeasurement; //in nanoseconds
    long elapsedTime;
    boolean TIMEOUT;
    boolean CAUGHT;

    /**
     * Constructor without arguments of the BoostController class
     */
    public BoostController(){
        this.lastTimeMeasurement = System.nanoTime();
        this.elapsedTime = 0;
        TIMEOUT = false;
        CAUGHT = false;
    }

    /**
     * Constructor with arguments of the BoostController class. Sets the body's fixtures
     * @param world A Box2D world
     * @param boostModel Object of class BoostModel to be set as the body's user data
     * @see EntityController#EntityController(World, EntityModel, BodyDef.BodyType, boolean)
     */
    public BoostController(World world, BoostModel boostModel)
    {
        super(world,boostModel,BodyDef.BodyType.StaticBody,true);
        float density = 1f, friction = 0.4f, restitution = 0f;
        int width = 55, height = 55;

        // Fixture needs to be convex so we need two of them.
        createFixture(body, new float[]{
                0,0,55,0,55,55,0,55
        }, width, height, density, friction, restitution, BOOST_BITS, PLAYER_BITS);

        this.lastTimeMeasurement = System.nanoTime();
        this.elapsedTime = 0;
        TIMEOUT = false;
    }

    /**
     * Applies a force in the positive x direction so that the specified player moves to the right
     * @param player The player in which the movement is applied
     */
    @Override
    public void moveRight(PlayerController player) {
        player.getBody().applyForceToCenter(SIDE_FORCE, 0, true);
    }

    /**
     * Applies a force in the negative x direction so that the specified player moves to the left
     * @param player The player in which the movement is applied
     */
    @Override
    public void moveLeft(PlayerController player) {
        player.getBody().applyForceToCenter(-SIDE_FORCE, 0, true);
    }

    @Override
    public abstract void jump(PlayerController player);

    @Override
    public abstract void collisionHandler(PlayerController player);

    /**
     * Updates the remaining time for the boost to take effect
     */
    @Override
    public void updateRemainingTime() {
        if(CAUGHT)
            this.elapsedTime += System.nanoTime() - this.lastTimeMeasurement;
        this.lastTimeMeasurement = System.nanoTime();
        if(roundTime() >= ACTIVE_TIME) {
            this.TIMEOUT = true;
        }
    }

    /**
     * Returns the elapsedTime in seconds
     * @return elapsedTime in seconds
     */
    private int roundTime()
    {
        return (int)Math.floor(elapsedTime/ Math.pow(10,9));
    }

    /**
     * Returns the timeout flag indicating if the boost no longer affects the player
     * @return True if a timeout occurred and false otherwise
     */
    public boolean isTIMEOUT() {
        return TIMEOUT;
    }

    /**
     * Sets the data member lastTimeMeasurement
     * @param lastTimeMeasurement Last time since the boost's remaining time was measured
     */
    public void setLastTimeMeasurement(long lastTimeMeasurement)
    {
        this.lastTimeMeasurement = lastTimeMeasurement;
    }

    /**
     * Returns the remaining time until a timeout occurs
     * @return Time left for a timeout
     */
    @Override
    public double getRemainingTime() {return ACTIVE_TIME - roundTime();}

    /**
     * Sets the flag CAUGHT to true, meaning that the boost is affecting a player
     */
    public void setCAUGHT() {
        this.CAUGHT = true;
    }

}
