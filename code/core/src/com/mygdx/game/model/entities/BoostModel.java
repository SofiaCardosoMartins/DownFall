package com.mygdx.game.model.entities;

import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.ViewFactory;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

/**
 * A class representing a boost entity in the game
 */
public class BoostModel extends EntityModel {

    /**
     * Constructs the boost model
     * @param x The boost's x position
     * @param y The boost's y position
     * @param rotation The boost's angle
     */
    public BoostModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
    }

    /**
     * Constructor without arguments of a BoostModel
     */
    public BoostModel()
    {
        super(0, 0, 0);
    }

    @Override
    public ModelType getType() {
        return ModelType.NATURAL_BOOST;
    }

    /**
     * Checks if the boost is within the camera's section
     * @param minCameraY The minimum value of the camera's y position
     */
    public void checkBounds(float minCameraY) {
        if(this.getType() == ModelType.NATURAL_BOOST) return;

        float height = ViewFactory.getHeigth(AppView.game, this) * PIXEL_TO_METER;

        if ((this.y + (height/2)) < minCameraY ) {
            this.setFlaggedForRemoval(true);
            GameModel.getInstance().remove(this);
        }
    }
}
