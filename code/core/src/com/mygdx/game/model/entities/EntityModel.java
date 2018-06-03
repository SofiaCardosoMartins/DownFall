package com.mygdx.game.model.entities;

/**
 * An abstract model representing an entity belonging to a game model.
 */
public abstract class EntityModel {

    public enum ModelType {PLAYER1,PLAYER2, OBSTACLE, NATURAL_BOOST, FLY_BOOST, NO_COLLISIONS_BOOST, PLATFORM, LAVA};
    protected float x;
    protected float y;
    protected float rotation;
    protected boolean flaggedForRemoval;

    /**
     * Constructor without arguments of the EntityModel class
     */
    public EntityModel()
    {
        this.x = 0;
        this.y = 0;
        this.rotation = 0;
        this.flaggedForRemoval = false;
    }

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     * @param rotation The current rotation of this entity in radians.
     */
    public EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.flaggedForRemoval = false;
    }

    /**
     * Returns the x-coordinate of this entity.
     *
     * @return The x-coordinate of this entity in meters.
     */
    public float getX()
    {
        return x;
    }

    /**
     * Returns the y-coordinate of this entity.
     *
     * @return The y-coordinate of this entity in meters.
     */
    public float getY()
    {
        return y;
    }

    /**
     * Returns true if this entity has been flagged for removal
     *
     * @return True if this entity has been flagged for removal and false otherwise
     */
    public boolean isFlaggedForRemoval()
    {
        return flaggedForRemoval;
    }

    /**
     * Sets the model's x position
     * @param x Position in the x direction
     */
    public void setX(float x)
    {
        this.x = x;
    }

    /**
     * Sets the model's y position
     * @param y Position in the y direction
     */
    public void setY(float y)
    {
        this.y = y;
    }

    /**
     * Sets the position of this entity.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     */
    public void setPosition(float x, float y)
    {
        setX(x);
        setY(y);
    }

    /**
     * Sets the rotation of this entity.
     * @param rotation The current rotation of this entity in radians.
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Makes this model flagged for removal on next step
     * @param flaggedForRemoval The value of the flaggedForRemoval flag
     */
    public void setFlaggedForRemoval(boolean flaggedForRemoval)
    {
        this.flaggedForRemoval = flaggedForRemoval;
    }

    /**
     * Returns the rotation of this entity.
     * @return The rotation of this entity in radians.
     */
    public float getRotation() {
        return rotation;
    }

    public abstract ModelType getType();
}
