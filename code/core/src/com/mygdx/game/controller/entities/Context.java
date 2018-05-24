package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public class Context {

    State currentState;
    PlayerController player;

    public Context(PlayerController player){
        currentState = new Idle();
        this.player = player;
    }
    public void handleInput(GameController.Direction dir){
        if (dir == GameController.Direction.RIGHT)
            player.moveRight();
        else if (dir == GameController.Direction.LEFT)
            player.moveLeft();
        System.out.println(currentState.getClass());
        if (dir == GameController.Direction.UP && currentState instanceof Idle)
            player.jump();
        currentState = currentState.handleInput(dir);

    }

    public void setState(State state)
    {
        this.currentState = state;
    }

    public void update(float vx, float vy)
    {
        currentState = currentState.update(vx,vy);

    }

}
