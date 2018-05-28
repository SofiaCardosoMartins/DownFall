package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController.Direction;

public class Jump extends State {
    @Override
    public State handleInput(Direction dir) {
        return this;
    }

    @Override
    public State update(float vx, float vy) {
        if(vy < 0)
            return new Fall();
        else if (vy == 0)
            return new Idle();
        return this;
    }
}
