package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

/**
 * A class to represent a player's context
 */
public class Context {

    State currentState;
    PlayerController player;

    /**
     * Constructor with arguments of the Context class
     * @param player The player at which the context belongs
     */
    public Context(PlayerController player){
        currentState = new Idle();
        this.player = player;
    }

    /**
     * Moves the player according to the direction of movement and its current strategy
     * @param dir Direction of movement
     */
    public void handleInput(GameController.Direction dir){
        if (dir == GameController.Direction.RIGHT)
            player.moveRight();
        else if (dir == GameController.Direction.LEFT)
            player.moveLeft();
        if (dir == GameController.Direction.UP )
            if ((player.strategy.getClass() == NaturalBoostController.class) || (player.strategy.getClass() == NoCollisionsBoostController.class)) {
            if ((currentState instanceof Idle))
                    player.jump();
            } else player.jump();
        currentState = currentState.handleInput(dir);

    }

    /**
     * Setter of the state data member
     * @param state The value to be assigned to the state data member
     */
    public void setState(State state)
    {
        this.currentState = state;
    }

    /**
     * Updates the current state according to the player's velocity
     * @param vx Velocity in the x direction
     * @param vy Velocity in the y direction
     */
    public void update(float vx, float vy)
    {
        currentState = currentState.update(vx,vy);
    }

}
