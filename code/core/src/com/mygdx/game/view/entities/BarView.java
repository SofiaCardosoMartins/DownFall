package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.entities.PlayerController;
import com.mygdx.game.model.entities.EntityModel;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class BarView extends EntityView {
    boolean doublePlayer;
    ArrayList<ScorePointerView> pointers = new ArrayList<ScorePointerView>();

    BarView(DownFall game, boolean doublePlayer) {
        super(game);
        this.doublePlayer = doublePlayer;
        sprite = createSprite(game);
        pointers.add(new ScorePointerView(game));
        if (doublePlayer)
            pointers.add(new ScorePointerView(game));
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for(ScorePointerView pointerView: pointers)
           pointerView.draw(batch);
    }

    @Override
    public Sprite createSprite(DownFall game) {
        Texture texture;
        if (doublePlayer)
            texture = game.getAssetManager().get("largeBarDouble.png");
        else texture = game.getAssetManager().get("largeBarSingle.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    public void update(float x, float y) {
        sprite.setCenter(x / PIXEL_TO_METER, (y / PIXEL_TO_METER) - (sprite.getHeight() / 2));  //pixel to meter: na appview
        List<PlayerController> players =  GameController.getInstance().getPlayerControllers();
        for (int i = 0;i<players.size();i++)
            pointers.get(i).update(players.get(i).getY(), y, i+1);
    }


    @Override
    public Sprite getSprite() {
        return super.getSprite();
    }
}
