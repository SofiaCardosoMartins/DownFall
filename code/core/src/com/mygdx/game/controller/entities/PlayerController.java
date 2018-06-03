package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.BoostModel;
import com.mygdx.game.model.entities.PlayerModel;

public class PlayerController extends EntityController {

    BoostStrategy strategy;
    Context context;

    public PlayerController(World world, PlayerModel playerModel) {
        super(world, playerModel, BodyDef.BodyType.DynamicBody, false);
        float density = 0.6f; //heavy
        float friction = 0.9f;
        float restitution = 0.0f; //no restitution
        int width = 128;
        int height = 128;
        BoostModel boostModel = new BoostModel();
        strategy = new NaturalBoost(world, boostModel);
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

    public void handleInput(GameController.Direction dir) {
        context.handleInput(dir);
    }

    public void setStrategy(BoostStrategy strategy) {
        this.strategy = strategy;
    }

    public void moveLeft() {
        strategy.moveLeft(this);
    }

    public void moveRight() {
        strategy.moveRight(this);
    }

    public void jump() {
        strategy.jump(this);
    }

    public void collisionHandler() {
        strategy.collisionHandler(this);
    }

    @Override
    public void leftWallCollision() {
        body.applyForceToCenter((float) (100 + 5 * Math.pow((float) body.getLinearVelocity().x, 2)), 0, true);
    }

    @Override
    public void rightWallCollision() {
        body.applyForceToCenter((float) (-100 - (5 * Math.pow(body.getLinearVelocity().x, 2))), 0, true);
    }

    @Override
    public void update()
    {
        float vx = body.getLinearVelocity().x;
        float vy = body.getLinearVelocity().y;
        context.update(vx,vy);
        this.strategy.updateRemainingTime();
        System.out.println("STATE" + context.currentState.getClass());
        System.out.println("vx: " + vx + " vy: " + vy);
        ((PlayerModel)this.getUserData()).setRemainingTime(this.strategy.getRemainingTime());
        if(((BoostController)strategy).isTIMEOUT()) {
            this.strategy = new NaturalBoost();
            ((PlayerModel) this.getUserData()).setBoostPresent(false);
        }
    }

    public void updateStrategyTime()
    {
        this.strategy.setLastTimeMeasurement(System.nanoTime());
    }
}
