package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

import static com.mygdx.game.controller.GameController.Direction.*;

public class Idle extends State {

    @Override
    public State handleInput(GameController.Direction dir) {
        switch (dir) {
            case UP:
                return new Jump();
            case LEFT:
                return new MoveLeft();
            case RIGHT:
                return new MoveRight();
        }
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if(vy < 0 )
            return new Fall();
        return this;
    }
}
