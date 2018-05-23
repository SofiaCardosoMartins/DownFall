package com.mygdx.game.model.entities;

import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.ViewFactory;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class ObstacleModel extends EntityModel {

    private float vx;

    public ObstacleModel() {
        super();
        this.vx = 0;
    }

    public ObstacleModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
        this.vx = 0;
    }

    public void checkBounds(float minCameraY) {
        float height = ViewFactory.getHeigth(AppView.game, this) * PIXEL_TO_METER;
        float width = ViewFactory.getWidth(AppView.game, this) * PIXEL_TO_METER;

        if (((this.y + (height/2)) < minCameraY ) || ((this.x - (width/2)) > WORLD_WIDTH)) {
            System.out.println("--------------------------------");
            this.setFlaggedForRemoval(true);
            GameModel.getInstance().remove(this);
        }
    }

    @Override
    public ModelType getType() {
        return ModelType.OBSTACLE;
    }
}
