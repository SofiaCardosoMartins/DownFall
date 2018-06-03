package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

/**
 * The view that belongs to the FlyBoostModel
 */
public class FlyBoostView extends EntityView {

    /**
     * Constructor with arguments of the FlyBoostView class
     * @param game The game the boost belongs to
     */
    FlyBoostView(DownFall game)
    {
        super(game);
    }

    /**
     * Creates the sprite representing the boost
     * @param game the game this view belongs to. Needed to access the asset manager to get textures
     * @return The boost's sprite
     */
    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("flyBoost.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
