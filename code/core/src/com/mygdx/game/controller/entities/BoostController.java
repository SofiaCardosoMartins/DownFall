package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.BoostModel;


public class BoostController extends EntityController implements BoostStrategy {
    @Override
    public double getTime() {
        return elapsedTime;
    }

    private static final double ACTIVE_TIME = 10;  //in seconds

    long beginTimeMeasurement; //in nanoseconds
    long elapsedTime;
    boolean TIMEOUT;
    boolean CAUGHT;

    public boolean isTIMEOUT() {
        return TIMEOUT;
    }

    public BoostController(){
        this.beginTimeMeasurement = System.nanoTime();
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

        this.beginTimeMeasurement = System.nanoTime();
        this.elapsedTime = 0;
        TIMEOUT = false;
    }

    @Override
    public void moveRight(PlayerController player) {

    }

    @Override
    public void moveLeft(PlayerController player) {

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
        this.elapsedTime = (long)((System.nanoTime() - this.beginTimeMeasurement)/Math.pow(10,9));
        if(elapsedTime >= ACTIVE_TIME) {
            this.TIMEOUT = true;
        }
    }


    public void setCAUGHT() {
        this.CAUGHT = true;
    }
}
