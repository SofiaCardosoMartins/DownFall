package com.mygdx.game.model.entities;

import com.mygdx.game.controller.entities.BoostStrategy;
import com.mygdx.game.controller.entities.Context;
import com.mygdx.game.controller.entities.NaturalBoost;

public class PlayerModel extends EntityModel {

    public PlayerModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.PLAYER;
    }
}
