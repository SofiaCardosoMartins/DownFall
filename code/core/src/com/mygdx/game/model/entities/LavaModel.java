package com.mygdx.game.model.entities;

import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.view.entities.ViewFactory;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class LavaModel extends EntityModel{

    public LavaModel() {
        super(WORLD_WIDTH/2, 100*PIXEL_TO_METER ,0); //200: lava height
    }

    public LavaModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.LAVA;
    }
}