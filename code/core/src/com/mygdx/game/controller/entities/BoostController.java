package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BoostModel;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;


public class BoostController extends EntityController implements BoostStrategy {

    public static final double ACTIVE_TIME = 10;  //in seconds

    long lastTimeMeasurement; //in nanoseconds
    long elapsedTime;
    boolean TIMEOUT;
    boolean CAUGHT;

    public boolean isTIMEOUT() {
        return TIMEOUT;
    }

    public BoostController(){
        this.lastTimeMeasurement = System.nanoTime();
        this.elapsedTime = 0;
        TIMEOUT = false;
        CAUGHT = false;
    }
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

    @Override
    public void moveRight(PlayerController player) {
        player.getBody().applyForceToCenter(40, 0, true);
    }

    @Override
    public void moveLeft(PlayerController player) {
        player.getBody().applyForceToCenter(-40, 0, true);
    }

    @Override
    public void jump(PlayerController player) {

    }

    @Override
    public void collisionHandler(PlayerController player) {

    }

    @Override
    public void updateRemainingTime() {
        if(CAUGHT)
            this.elapsedTime += System.nanoTime() - this.lastTimeMeasurement;
        this.lastTimeMeasurement = System.nanoTime();
        if(roundTime() >= ACTIVE_TIME) {
            this.TIMEOUT = true;
        }
    }

    private int roundTime()
    {
        return (int)Math.floor(elapsedTime/ Math.pow(10,9));
    }

    @Override
    public double getTime() {
        return elapsedTime;
    }

    public void setLastTimeMeasurement(long lastTimeMeasurement)
    {
        this.lastTimeMeasurement = lastTimeMeasurement;
    }

    @Override
    public double getRemainingTime() {return ACTIVE_TIME - roundTime();}


    public void setCAUGHT() {
        this.CAUGHT = true;
    }

}
