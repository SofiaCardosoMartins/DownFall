package com.mygdx.game.model.entities;

import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.ViewFactory;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class BoostModel extends EntityModel {

    public BoostModel(float x, float y, float rotation)
    {
        super(x,y,rotation);
    }

    public BoostModel()
    {
        super(0, 0, 0);
    }

    @Override
    public ModelType getType() {
        return ModelType.NATURAL_BOOST;
    }

    public void checkBounds(float minCameraY) {
        if(this.getType() == ModelType.NATURAL_BOOST) return;

        float height = ViewFactory.getHeigth(AppView.game, this) * PIXEL_TO_METER;

        if ((this.y + (height/2)) < minCameraY ) {
            this.setFlaggedForRemoval(true);
            GameModel.getInstance().remove(this);
        }
    }
}
