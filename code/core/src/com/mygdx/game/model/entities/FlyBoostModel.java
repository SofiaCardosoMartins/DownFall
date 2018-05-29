package com.mygdx.game.model.entities;

public class FlyBoostModel extends BoostModel {

    public FlyBoostModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public FlyBoostModel() {
        super();
    }

    @Override
    public ModelType getType() {
        return ModelType.FLY_BOOST;
    }
}
