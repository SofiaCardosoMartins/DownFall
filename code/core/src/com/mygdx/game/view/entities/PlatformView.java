package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

public class PlatformView extends EntityView {

    PlatformView(DownFall game)
    {
        super(game);
    }

    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("platform.png");
        Sprite sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        //sprite.setSize(200, 20);
        return sprite;
    }
}
