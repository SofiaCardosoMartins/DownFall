package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.DownFall;
import com.mygdx.game.model.entities.EntityModel;

/**
 * The view that belongs to the lava
 */
public class LavaView extends EntityView {

    private Animation<TextureRegion> animation;
    private float stateTime = 0;

    /**
     * Constructor with arguments of the LavaView class
     * @param game The game the lava belongs to
     */
    LavaView(DownFall game) {
        super(game);
    }

    /**
     * Creates the sprite representing the lava
     * @param game the game this view belongs to. Needed to access the asset manager to get textures
     * @return The lava's sprite
     */
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

    /**
     * Updates the lava's animation
     * @param delta Time since last renders in seconds.
     * @param model The lava's model
     */
    public void update(float delta,EntityModel model) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime, true));
        update(model);
    }


}
