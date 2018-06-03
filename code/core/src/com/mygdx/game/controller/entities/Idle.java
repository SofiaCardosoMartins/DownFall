package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

import static com.mygdx.game.controller.GameController.Direction.*;

public class Idle extends State {

    @Override
    public State handleInput(GameController.Direction dir) {
        if (dir == GameController.Direction.UP)
            return new Jump();
        else return this;
    }

    @Override
    public State update(float vx, float vy) {

        if(vy < -0.1)
            return new Fall();
        return this;
    }
}
