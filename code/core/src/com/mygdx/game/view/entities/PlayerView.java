package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

public class PlayerView extends EntityView {

    PlayerView(DownFall game)
    {
        super(game);
    }

    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("player.png");
        Sprite sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        //sprite.setSize(50, 50);
        return sprite;
    }
}
