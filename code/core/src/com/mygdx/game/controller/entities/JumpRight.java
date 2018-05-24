package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public class JumpRight extends State {

    @Override
    public State handleInput(GameController.Direction dir) {
        switch (dir) {
            case UP:
                return this;
            case LEFT:
                return new JumpLeft();
            case RIGHT:
                return this;
        }
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if(vy < 0)
            return new FallRight();

        return this;
    }
}
