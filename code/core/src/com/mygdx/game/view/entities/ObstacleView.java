package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

/**
 * The view that belongs to an ObstacleModel
 */
public class ObstacleView extends EntityView {

    /**
     * Constructor with arguments of the ObstacleView class
     * @param game The game the obstacle belongs to
     */
    ObstacleView(DownFall game)
    {
        super(game);
    }

    /**
     * Creates the sprite representing the obstacle
     * @param game the game this view belongs to. Needed to access the asset manager to get textures
     * @return The obstacle's sprite
     */
    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("obstacle.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
