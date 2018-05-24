package com.mygdx.game.model.entities;

import com.mygdx.game.controller.entities.BoostStrategy;

public class BoostModel extends EntityModel {

    public BoostModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
    }

    public BoostModel()
    {
        super(0, 0, 0);
    }

    @Override
    public ModelType getType() {
        return ModelType.BOOST;
    }
}
