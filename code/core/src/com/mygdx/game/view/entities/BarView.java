package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.entities.PlayerController;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

/**
 * The bar's view. A bar allows to register the position of the player in the world.
 */
public class BarView extends EntityView {
    boolean doublePlayer;
    ArrayList<ScorePointerView> pointers = new ArrayList<ScorePointerView>();

    /**
     * Constructor with arguments of the BarView class
     * @param game The game this bar belongs to
     * @param doublePlayer True if the current game has to players and false otherwise
     */
    BarView(DownFall game, boolean doublePlayer) {
        super(game);
        this.doublePlayer = doublePlayer;
        sprite = createSprite(game);
        pointers.add(new ScorePointerView(game));
        if (doublePlayer)
            pointers.add(new ScorePointerView(game));
    }

    /**
     * Draws the sprite from this view using a sprite batch
     * @param batch The sprite batch to be used for drawing
     */
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for(ScorePointerView pointerView: pointers)
           pointerView.draw(batch);
    }

    /**
     * Creates the sprite representing the bar
     * @param game the game this view belongs to. Needed to access the asset manager to get textures
     * @return The bar's sprite
     */
    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture;
        if (doublePlayer)
            texture = game.getAssetManager().get("largeBarDouble.png");
        else texture = game.getAssetManager().get("largeBarSingle.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    /**
     * Updates the sprite's position
     * @param x The position in the x direction
     * @param y The position in the y direction
     */
    public void update(float x, float y) {
        sprite.setCenter(x / PIXEL_TO_METER, (y / PIXEL_TO_METER) - (sprite.getHeight() / 2));
        List<PlayerController> players =  GameController.getInstance().getPlayerControllers();
        for (int i = 0;i<players.size();i++)
            pointers.get(i).update(players.get(i).getY(), y, i+1);
    }

    /**
     * @return The sprite representing the bar
     */
    @Override
    public Sprite getSprite() {
        return super.getSprite();
    }
}
