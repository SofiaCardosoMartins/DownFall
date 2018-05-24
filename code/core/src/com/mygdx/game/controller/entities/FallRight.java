package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public class FallRight extends State{

    @Override
    public State handleInput(GameController.Direction dir) {
        switch (dir) {
            case UP:
                return this;
            case LEFT:
                return new FallLeft();
            case RIGHT:
                return this;
        }
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if((vx == 0) && (vy == 0))
            return new Idle();
        else if(vy == 0)
            return new MoveRight();
        return this;
    }
}
