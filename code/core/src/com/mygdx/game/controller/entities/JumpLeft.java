package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public class JumpLeft extends State {

    @Override
    public State handleInput(GameController.Direction dir) {
        switch (dir) {
            case UP:
                return this;
            case LEFT:
                return this;
            case RIGHT:
                return new JumpRight();
        }
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if(vy < 0 )
            return  new FallLeft();
        return this;
    }
}
