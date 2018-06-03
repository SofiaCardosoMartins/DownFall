package com.mygdx.game.model.entities;

/**
 *  A class representing a fly boost entity in the game
 */
public class FlyBoostModel extends BoostModel {

    /**
     * Constructs the boost model
     * @param x The boost's x position
     * @param y The boost's y position
     * @param rotation The boost's angle
     */
    public FlyBoostModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    /**
     * Constructor without arguments of a FlyBoostModel
     */
    public FlyBoostModel() {
        super();
    }

    @Override
    public ModelType getType() {
        return ModelType.FLY_BOOST;
    }
}
