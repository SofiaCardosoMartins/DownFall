package com.mygdx.game.model.entities;

public class ObstacleModel extends EntityModel {

    private float vx;

    public ObstacleModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
        this.vx = 0;
    }

    @Override
    public ModelType getType() {
        return ModelType.OBSTACLE;
    }
}
