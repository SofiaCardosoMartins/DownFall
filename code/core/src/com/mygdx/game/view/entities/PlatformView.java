package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

/**
 * The view that belongs to a PlatformModel
 */
public class PlatformView extends EntityView {

    /**
     * Constructor with arguments of the PlatformView class
     * @param game The game the platform belongs to
     */
    PlatformView(DownFall game)
    {
        super(game);
    }

    /**
     * Creates the sprite representing the platform
     * @param game the game this view belongs to. Needed to access the asset manager to get textures
     * @return The platform's sprite
     */
    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("platform.png");
        Sprite sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        return sprite;
    }
}
