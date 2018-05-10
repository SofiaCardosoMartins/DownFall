package com.mygdx.game.model.entities;

public class PlatformModel extends EntityModel {

    private float vx;
    private float vy;

    public PlatformModel(float x, float y)
    {
        super(x,y);
        this.vx = 0;
        this.vy = 0;
    }

    @Override
    public ModelType getType() {
        return ModelType.PLATFORM;
    }


}
