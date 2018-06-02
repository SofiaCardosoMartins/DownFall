package com.mygdx.game.model.entities;

public class NoCollisionsBoostModel extends BoostModel {
    public NoCollisionsBoostModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    public NoCollisionsBoostModel(){
        super();
    }

    @Override
    public ModelType getType() {
        return ModelType.NO_COLLISIONS_BOOST;
    }
}
