package com.mygdx.game.controller.entities;

import com.mygdx.game.controller.GameController;

public abstract class State {
    public abstract State handleInput(GameController.Direction dir);
    public abstract State update(float vx, float vy);
}
