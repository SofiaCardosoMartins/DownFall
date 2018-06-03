package com.mygdx.game.model.entities;

import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.ViewFactory;

import static com.badlogic.gdx.math.MathUtils.random;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

/**
 * A class representing a platform entity in the game
 */
public class PlatformModel extends EntityModel {

    private float vx;
    private float vy;

    /**
     * Constructs a new platform model with the default values (x,y,angle and velocity)
     */
    public PlatformModel() {
        super();
        this.vx = 0;
        this.vy = 0;
    }

    /**
     * Constructs the platform model
     *
     * @param x        The platform's x position
     * @param y        The platform's y position
     * @param rotation The platform's angle
     */
    public PlatformModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.vx = 0;
        this.vy = 0;
    }

    @Override
    public ModelType getType() {
        return ModelType.PLATFORM;
    }

    /**
     * Checks if the platform is within the camera's section
     *
     * @param minCameraY The minimum value of the camera's y position
     */
    public void checkBounds(float minCameraY) {
        float height = ViewFactory.getHeigth(AppView.game, this) * PIXEL_TO_METER;

        if ((this.y + height) < minCameraY) {
            this.setFlaggedForRemoval(true);
            GameModel.getInstance().remove(this);
        }
    }

    /**
     * Sets the platform's x position
     * @param previousX x coordinate of the platform above itself
     * @param maxDistance maximum x distance between itself and the platform above, excluding the platform's width
     */
    public void setRandomX(float previousX, int maxDistance) {
        float width = ViewFactory.getWidth(AppView.game, this) * PIXEL_TO_METER;
        float tempX;
        do {
            tempX = previousX;
            boolean increaseX = random.nextBoolean(); //determine if the x coordinate will decrease or increase
            if (increaseX) {
                tempX += random.nextInt((int) maxDistance);
                tempX += (width / 2);
            } else {
                tempX -= random.nextInt((int) maxDistance);
                tempX -= (width / 2);
            }
        }
        while (((tempX + (width / 2)) > WORLD_WIDTH) || ((tempX - (width / 2)) < 0) || (tempX == previousX));
        this.x = tempX;
    }


}
