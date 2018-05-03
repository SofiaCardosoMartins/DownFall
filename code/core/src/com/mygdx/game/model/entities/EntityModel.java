package com.mygdx.game.model.entities;

public abstract class EntityModel {

    public enum ModelType {PLAYER, OBSTACLE, BOOST, PLATFORM};
    private float x;
    private float y;
    private boolean flaggedForRemoval;

    EntityModel(float x, float y) {
        this.x = x;
        this.y = y;
        this.flaggedForRemoval = false;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public boolean isFlaggedForRemoval()
    {
        return flaggedForRemoval;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setPosition(float x, float y)
    {
        setX(x);
        setY(y);
    }

    public void setFlaggedForRemoval(boolean flaggedForRemoval)
    {
        this.flaggedForRemoval = flaggedForRemoval;
    }

    public abstract ModelType getType();
}
