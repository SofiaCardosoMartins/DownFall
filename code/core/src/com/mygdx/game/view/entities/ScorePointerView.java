package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DownFall;

import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class ScorePointerView extends EntityView {


    ScorePointerView(DownFall game) {
        super(game);
    }
    public void update(float playerY, float maxCamY, int playerNum)
    {
        float x;
        if (playerNum == 1)
            x = ((267*(playerY/PIXEL_TO_METER))/(WORLD_HEIGHT / PIXEL_TO_METER)) + 7.5f;
        else
            x = (WORLD_WIDTH / PIXEL_TO_METER) - ((267*(playerY/PIXEL_TO_METER))/(WORLD_HEIGHT / PIXEL_TO_METER)) - 5.837f;

        sprite.setCenter(x ,(maxCamY / PIXEL_TO_METER) - 19.637f);  //pixel to meter: na appview
    }
    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("scorePointer.png");
        Sprite sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        return sprite;
    }
}
