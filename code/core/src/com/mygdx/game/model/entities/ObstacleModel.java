package com.mygdx.game.model.entities;

import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.ViewFactory;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;
/**
 *  A class representing an obstacle entity in the game
 */
public class ObstacleModel extends EntityModel {

    private float vx;

    /**
     * Constructs a new obstacle model with the default values (x,y,angle and velocity in the x direction)
     */
    public ObstacleModel() {
        super();
        this.vx = 0;
    }

    /**
     * Constructs the obstacle model
     * @param x The obstacle's x position
     * @param y The obstacle's y position
     * @param rotation The obstacle's angle
     */
    public ObstacleModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
        this.vx = 0;
    }
    /**
     * Checks if the obstacle is within the camera's section
     * @param minCameraY The minimum value of the camera's y position
     */
    public void checkBounds(float minCameraY) {
        float height = ViewFactory.getHeigth(AppView.game, this) * PIXEL_TO_METER;
        float width = ViewFactory.getWidth(AppView.game, this) * PIXEL_TO_METER;

        if (((this.y + (height/2)) < minCameraY ) || ((this.x - (width/2)) > WORLD_WIDTH)) {
            this.setFlaggedForRemoval(true);
            GameModel.getInstance().remove(this);
        }
    }

    @Override
    public ModelType getType() {
        return ModelType.OBSTACLE;
    }
}
