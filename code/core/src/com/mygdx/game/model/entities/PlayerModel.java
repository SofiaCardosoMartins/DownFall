package com.mygdx.game.model.entities;


public class PlayerModel extends EntityModel {

    int num;
    double remainingTime;
    boolean boostPresent;

    public PlayerModel(float x, float y, float rotation, int num)
    {
        super(x,y,rotation);
        this.num = num;
        this.remainingTime = 0;
        this.boostPresent = false;
    }

    @Override
    public ModelType getType() {
        return ModelType.PLAYER;
    }

    public int getNum() {
        return num;
    }

    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public boolean isBoostPresent() {
        return boostPresent;
    }

    public void setBoostPresent(boolean boostPresent) {
        this.boostPresent = boostPresent;
    }
}
