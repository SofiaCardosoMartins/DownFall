package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DownFall;
import com.mygdx.game.model.entities.EntityModel;

import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public abstract class EntityView {

    Sprite sprite;

    EntityView(DownFall game)
    {
        sprite = createSprite(game);
    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public abstract Sprite createSprite(DownFall game);

    public void update(EntityModel model)
    {
        sprite.setPosition(model.getX()/PIXEL_TO_METER,model.getY()/PIXEL_TO_METER);  //pixel to meter: na appview
    }
}
