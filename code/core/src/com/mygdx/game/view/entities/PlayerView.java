package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

public class PlayerView extends EntityView {

    int num;

    PlayerView(DownFall game, int playerNum)
    {
        super(game);
        this.num = playerNum;
    }

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
