package com.mygdx.game.model.entities;

public class BoostModel extends EntityModel implements BoostStrategy {

    BoostModel(float x, float y)
    {
        super(x,y);
    }

    @Override
    public ModelType getType() {
        return ModelType.BOOST;
    }

    @Override
    public void move() {

    }

    @Override
    public void jump() {

    }

    @Override
    public void collisionHandler() {

    }
}
