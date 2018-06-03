package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

/**
 * The view that belongs to a PlayerModel
 */
public class PlayerView extends EntityView {

    int num;

    /**
     * Constructor with arguments of the PlayerView class
     * @param game The game the player belongs to
     * @param playerNum The player's number in the game (either 1 or 2)
     */
    PlayerView(DownFall game, int playerNum)
    {
        super(game);
        this.num = playerNum;
    }

    /**
     * Creates the sprite representing the player
     * @param game the game this view belongs to. Needed to access the asset manager to get textures
     * @return The player's sprite
     */
    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture;
        if(num == 1)
            texture = game.getAssetManager().get("player1.png");
        else
            texture = game.getAssetManager().get("player2.png");
        Sprite sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        return sprite;
    }
}
