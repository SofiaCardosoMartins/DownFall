package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.entities.BoostModel;
import com.mygdx.game.model.entities.PlayerModel;

/**
 * A class to represent the controller of a player model
 */
public class PlayerController extends EntityController {

    BoostStrategy strategy;
    Context context;

    /**
     * Constructor with arguments of the EntityController class
     * @param world A Box2D world
     * @param playerModel The model belonging to the controller
     */
    public PlayerController(World world, PlayerModel playerModel) {
        super(world, playerModel, BodyDef.BodyType.DynamicBody, false);
        float density = 0.6f; //heavy
        float friction = 0.9f;
        float restitution = 0.0f; //no restitution
        int width = 128;
        int height = 128;
        BoostModel boostModel = new BoostModel();
        strategy = new NaturalBoostController(world, boostModel);
        context = new Context(this);
        createFixture(body, new float[]{
                46,68,47,110,92,111,92,68,92,53
        }, width, height, density, friction, restitution, PLAYER_BITS, (short) (PLATFORM_BITS | LAVA_BITS | PLAYER_BITS | OBSTACLE_BITS | BOOST_BITS));

        createFixture(body, new float[]{
                89,43,82,33,68,30
        }, width, height, density, friction, restitution, PLAYER_BITS, (short) (PLATFORM_BITS | LAVA_BITS | PLAYER_BITS | OBSTACLE_BITS | BOOST_BITS));

        createFixture(body, new float[]{
                58,32,50,40,46,53,46,68
        }, width, height, density, friction, restitution, PLAYER_BITS, (short) (PLATFORM_BITS | LAVA_BITS | PLAYER_BITS | OBSTACLE_BITS | BOOST_BITS));

    }

    /**
     * Updates the player's state given a new input from the user
     * @param dir The direction of the player's movement
     */
    public void handleInput(GameController.Direction dir) {
        context.handleInput(dir);
    }

    /**
     * Sets the player's current strategy
     * @param strategy The current strategy of the player
     */
    public void setStrategy(BoostStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Moves the player to the left according to its current strategy
     */
    public void moveLeft() {
        strategy.moveLeft(this);
    }

    /**
     * Moves the player to the right according to its current strategy
     */
    public void moveRight() {
        strategy.moveRight(this);
    }

    /**
     * Moves the player up according to its current strategy
     */
    public void jump() {
        strategy.jump(this);
    }

    /**
     * Sets the mask bits of the player according to the current strategy
     */
    public void collisionHandler() {
        strategy.collisionHandler(this);
    }

    /**
     * Handles a collision between the player and the world's left wall
     */
    @Override
    public void leftWallCollision() {
        body.applyForceToCenter((float) (100 + 5 * Math.pow( body.getLinearVelocity().x, 2)), 0, true);
    }

    /**
     * Handles a collision between the player and the world's right wall
     */
    @Override
    public void rightWallCollision() {
        body.applyForceToCenter((float) (-100 - (5 * Math.pow(body.getLinearVelocity().x, 2))), 0, true);
    }

    /**
     * Updates the player's strategy and context
     */
    @Override
    public void update()
    {
        float vx = body.getLinearVelocity().x;
        float vy = body.getLinearVelocity().y;
        context.update(vx,vy);
        this.strategy.updateRemainingTime();
        ((PlayerModel)this.getUserData()).setRemainingTime(this.strategy.getRemainingTime());
        if(((BoostController)strategy).isTIMEOUT()) {
            this.strategy = new NaturalBoostController();
            ((PlayerModel) this.getUserData()).setBoostPresent(false);
        }
    }

    /**
     * Updates the last measurement of time of the player's strategy
     */
    public void updateStrategyTime()
    {
        this.strategy.setLastTimeMeasurement(System.nanoTime());
    }
}
