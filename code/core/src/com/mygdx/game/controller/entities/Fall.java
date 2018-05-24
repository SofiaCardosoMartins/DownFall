package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public class Fall extends State {

    @Override
    public State handleInput(GameController.Direction dir) {
        switch (dir) {
            case UP:
                return this;
            case LEFT:
                return new MoveLeft();
            case RIGHT:
                return new MoveRight();
        }
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if (vy == 0) {
            if (vx == 0)
                return new Idle();
            else if (vx > 0)
                return new MoveRight();
            else
                return new MoveLeft();
        }

        return this;
    }
}
