package com.mygdx.game.model.entities;

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
