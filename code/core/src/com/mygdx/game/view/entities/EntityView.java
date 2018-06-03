package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DownFall;
import com.mygdx.game.model.entities.EntityModel;

import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public abstract class EntityView {

    Sprite sprite;

    /**
     * Constructor with arguments of the EntityView class
     * @param game The game this entity belongs to
     */
    EntityView(DownFall game)
    {
        sprite = createSprite(game);
    }

    /**
     * Draws the sprite from this view using a sprite batch
     * @param batch The sprite batch to be used for drawing
     */
    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public abstract Sprite createSprite(DownFall game);

    /**
     * Updates this view based on a certain model
     * @param model the model used to update this view
     */
    public void update(EntityModel model)
    {
        sprite.setCenter(model.getX()/PIXEL_TO_METER,model.getY()/PIXEL_TO_METER);  //pixel to meter: na appview
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }

    /**
     * @return The view's sprite
     */
    public Sprite getSprite(){
        return sprite;
    }
}
