package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.DownFall;

import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;

public class PausedView extends AppView {

    private Stage stage;

    public PausedView(DownFall game) {
        super(game);
        this.loadAssets();
        this.createCamera();
        stage = new Stage(this.viewport);
        Gdx.input.setInputProcessor(stage);
        createBtns();
    }

    @Override
    protected void createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) 14 / (float) 10));
        this.viewport = new StretchViewport(WORLD_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) 14 / (float) 10) );
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        camera.update();
        this.camera = camera;

    }

    @Override
    protected void loadAssets() {
        this.game.getAssetManager().load("menu_background.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        game.getBatch().begin();
        drawBackground();
        drawEntities(delta);
        game.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    protected void handleInputs(float delta) {

    }

    @Override
    protected void drawEntities(float delta) {

    }

    @Override
    protected void drawBackground() {
        Texture background = game.getAssetManager().get("menu_background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (WORLD_WIDTH / PIXEL_TO_METER), (int) (WORLD_HEIGHT / PIXEL_TO_METER));
    }

    private void createBtns()
    {
        TextButton returnGameBtn = createBtn("Return",camera.viewportHeight / 2, BUTTON_WIDTH,stage);
        returnGameBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.resumeGame();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        TextButton mainMenuBtn = createBtn("Go to main menu",camera.viewportHeight / 2 - BTN_DISTANCE, BUTTON_WIDTH,stage);
        mainMenuBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.switchToMenuView();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }
}
