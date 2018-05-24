package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public class MoveRight extends State {

    @Override
    public State handleInput(GameController.Direction dir) {
        switch (dir) {
            case UP:
                return new Jump();
            case LEFT:
                return new MoveLeft();
            case RIGHT:
                return this;
        }
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if(vx == 0)
            return new Idle();

        return this;
    }
}
