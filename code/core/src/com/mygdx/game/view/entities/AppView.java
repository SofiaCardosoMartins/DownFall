package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.DownFall;

public abstract class AppView {

    public final static float PIXEL_TO_METER = 0.04f;   //debatable

    DownFall game;
    OrthographicCamera camera;

    AppView(DownFall game)
    {
        this.game = game;
        this.createCamera();
    }

    protected abstract void createCamera();
    protected abstract void loadAssets();
    protected abstract void render(float delta);
    protected abstract void handleInputs(float delta);
    protected abstract void drawEntities();
}
