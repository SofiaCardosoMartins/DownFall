package com.mygdx.game.model.entities;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

/**
 *  A class representing the lava entity in the game
 */
public class LavaModel extends EntityModel{

    /**
     * Constructs a new lava model with the default values (x,y and angle)
     */
    public LavaModel() {
        super(WORLD_WIDTH/2, 100*PIXEL_TO_METER ,0);
    }

    /**
     * Constructs the lava model
     * @param x The lava's x position
     * @param y The lava's y position
     * @param rotation The lava's angle
     */
    public LavaModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.LAVA;
    }
}