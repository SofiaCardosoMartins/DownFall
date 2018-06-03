package com.mygdx.game.model.entities;

/**
 *  A class representing a no collision boost boost entity in the game
 */
public class NoCollisionsBoostModel extends BoostModel {
    /**
     * Constructs the boost model
     * @param x The boost's x position
     * @param y The boost's y position
     * @param rotation The boost's angle
     */
    public NoCollisionsBoostModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    /**
     * Constructor without arguments of a NoCollisionsBoostModel
     */
    public NoCollisionsBoostModel(){
        super();
    }

    @Override
    public ModelType getType() {
        return ModelType.NO_COLLISIONS_BOOST;
    }
}
