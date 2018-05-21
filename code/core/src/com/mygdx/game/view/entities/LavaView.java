package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.DownFall;
import com.mygdx.game.model.entities.EntityModel;

public class LavaView extends EntityView {

    private Animation<TextureRegion> animation;
    private float stateTime = 0;

    LavaView(DownFall game) {
        super(game);
    }

    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture = game.getAssetManager().get("fire.png");
        TextureRegion[][] textReg = TextureRegion.split(texture, texture.getWidth() / 3, texture.getHeight() / 3);
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(textReg[0], 0, frames, 0, 3);
        animation = new Animation<TextureRegion>(.25f, frames);
        Sprite sprite = new Sprite(this.animation.getKeyFrame(0));
        return sprite;
    }

    public void update(float delta,EntityModel model) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime, true));
        update(model);
    }


}
