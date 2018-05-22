package com.mygdx.game.controller.entities;

import com.mygdx.game.model.entities.PlayerModel;

public class Context {

    State currentState;
    PlayerModel player;

    void handleInput()
    {

    }

    void setState(State state)
    {
        this.currentState = state;
    }
}
