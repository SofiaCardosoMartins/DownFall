package com.mygdx.game.model.entities;

import com.mygdx.game.controller.entities.Context;

public class PlayerModel extends EntityModel {

    private BoostStrategy boostStrategy;
    private Context context;

    public PlayerModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
        boostStrategy = new NaturalBoost();
        context = new Context();
    }

    @Override
    public ModelType getType() {
        return ModelType.PLAYER;
    }
}
