package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.DownFall;

public class TitleView extends EntityView {

    private Animation<TextureRegion> animation;
    private float stateTime = 0;

    TitleView(DownFall game) {
        super(game);
    }

    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("title.png");
        TextureRegion[][] textReg = TextureRegion.split(texture, texture.getWidth() / 20, texture.getHeight() / 1);
        TextureRegion[] frames = new TextureRegion[20];
        System.arraycopy(textReg[0], 0, frames, 0, 20);
        animation = new Animation<TextureRegion>(.25f, frames);
        Sprite sprite = new Sprite(this.animation.getKeyFrame(0));
        return sprite;
    }

    public void update(float delta) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime, true));
    }


}
