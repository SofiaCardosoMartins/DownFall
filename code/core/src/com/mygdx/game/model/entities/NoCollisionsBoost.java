package com.mygdx.game.model.entities;

public class NoCollisionsBoost extends BoostModel {
    public NoCollisionsBoost(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public NoCollisionsBoost(){
        super();
    }

    @Override
    public ModelType getType() {
        return ModelType.NO_COLLISIONS_BOOST;
    }
}
