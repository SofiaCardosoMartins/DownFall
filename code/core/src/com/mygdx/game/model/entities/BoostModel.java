package com.mygdx.game.model.entities;

public class BoostModel extends EntityModel {

    BoostModel(float x, float y)
    {
        super(x,y);
    }

    @Override
    public ModelType getType() {
        return ModelType.BOOST;
    }
}
