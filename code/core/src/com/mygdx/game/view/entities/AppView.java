package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DownFall;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;

public abstract class AppView extends ScreenAdapter {

    protected static final int BUTTON_HEIGHT = 80;
    protected static final float FONT_SCALE = 0.8f;
    protected static final int BUTTON_WIDTH = 220;
    protected static final float BTN_DISTANCE = 100;  //distance between buttons

    public final static float PIXEL_TO_METER = 0.015f;   //debatable
    public static final float VIEWPORT_WIDTH = WORLD_WIDTH ;
    private Skin btnSkin;
    public static DownFall game;
    public OrthographicCamera camera;
    protected Viewport viewport;

    AppView(DownFall game)
    {
        this.game = game;
        this.createCamera();
        this.viewport.apply();
        btnSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    }

    public TextButton createBtn(String text, float y, float width, Stage stage)
    {
        TextButton textButton = new TextButton(text,btnSkin);
        textButton.getLabel().setFontScale(FONT_SCALE,FONT_SCALE);
        textButton.setSize(width,BUTTON_HEIGHT);
        textButton.setPosition((camera.viewportWidth/2)-(width/2),y - (BUTTON_HEIGHT/2));
        stage.addActor(textButton);
        return textButton;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    protected abstract void createCamera();
    protected abstract void loadAssets();
    public abstract void render(float delta);
    protected abstract void handleInputs(float delta);
    protected abstract void drawEntities(float delta);
    protected abstract void drawBackground();
}
