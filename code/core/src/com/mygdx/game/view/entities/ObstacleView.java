package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

public class ObstacleView extends EntityView {

    ObstacleView(DownFall game)
    {
        super(game);
    }

    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("obstacle.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
