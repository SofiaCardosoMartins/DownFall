package com.mygdx.game.model.entities;

public class PlatformModel extends EntityModel {

    private float vx;
    private float vy;

    public PlatformModel(float x, float y,float rotation)
    {
        super(x,y,rotation);
        this.vx = 0;
        this.vy = 0;
    }

    @Override
    public ModelType getType() {
        return ModelType.PLATFORM;
    }


}
