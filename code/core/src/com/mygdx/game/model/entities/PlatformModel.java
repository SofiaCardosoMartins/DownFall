package com.mygdx.game.model.entities;

import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class PlatformModel extends EntityModel {

    private float vx;
    private float vy;

    public PlatformModel()
    {
        super();
        this.vx = 0;
        this.vy = 0;
    }

    public PlatformModel(float x, float y,float rotation)
    {
        super(x,y,rotation);
        this.vx = 0;
        this.vy = 0;
    }

    @Override
    public ModelType getType() {
        return ModelType.PLATFORM;
    }

    public void checkBounds(float minCameraY)
    {
        EntityView platformView =  ViewFactory.makeView(AppView.game, this);
        float height = platformView.getSprite().getHeight() * PIXEL_TO_METER;

        if((this.y + height) < minCameraY) {
            this.setFlaggedForRemoval(true);
            GameModel.getInstance().remove(this);
        }
    }


}
