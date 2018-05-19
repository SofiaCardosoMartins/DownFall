package com.mygdx.game.view.entities;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DownFall;

import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;

public abstract class AppView extends ScreenAdapter {

    public final static float PIXEL_TO_METER = 0.015f;   //debatable
    public static final float VIEWPORT_WIDTH = WORLD_WIDTH ;
    public static DownFall game;
    public OrthographicCamera camera;
    protected Viewport viewport;

    AppView(DownFall game)
    {
        this.game = game;
        this.createCamera();
        this.viewport.apply();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    protected abstract void createCamera();
    protected abstract void loadAssets();
    public abstract void render(float delta);
    protected abstract void handleInputs(float delta);
    protected abstract void drawEntities();
    protected abstract void drawBackground();
}
