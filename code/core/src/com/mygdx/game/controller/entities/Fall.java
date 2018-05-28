package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public class Fall extends State {

    @Override
    public State handleInput(GameController.Direction dir) {
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if (vy == 0)
                return new Idle();
        else return this;
    }
}
