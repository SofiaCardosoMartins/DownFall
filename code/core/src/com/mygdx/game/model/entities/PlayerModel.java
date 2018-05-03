package com.mygdx.game.model.entities;

public class PlayerModel extends EntityModel {

    private BoostStrategy boostStrategy;
    private Context context;

    public PlayerModel(float x, float y)
    {
        super(x,y);
        boostStrategy = new NaturalBoost();
        context = new Context();
    }

    @Override
    public ModelType getType() {
        return ModelType.PLAYER;
    }
}
