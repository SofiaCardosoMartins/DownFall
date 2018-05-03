package com.mygdx.game.model.entities;

public class ObstacleModel extends EntityModel {

    private float vx;

    ObstacleModel(float x, float y)
    {
        super(x,y);
        this.vx = 0;
    }

    @Override
    public ModelType getType() {
        return ModelType.OBSTACLE;
    }
}
