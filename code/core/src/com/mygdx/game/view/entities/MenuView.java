package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.DownFall;
import com.mygdx.game.model.GameModel;

import javax.xml.soap.Text;

import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;

public class MenuView extends AppView {

    private static final int BUTTON_WIDTH = 220;
    private static final int BUTTON_HEIGHT = 80;
    private static final float FONT_SCALE = 0.8f;
    private static final float BTN_DISTANCE = 100;  //distance between buttons

    protected Stage stage;
    protected Skin btnSkin;

    public MenuView(DownFall game) {
        super(game);
        this.loadAssets();
        this.createCamera();
        stage = new Stage(this.viewport);
        Gdx.input.setInputProcessor(stage);
        btnSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        createBtns();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
    public void setBtnSkin(Skin btnSkin) {
        this.btnSkin = btnSkin;
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
        this.game.getAssetManager().load("downFall.png", Texture.class);
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
        drawTitle(delta);
    }

    @Override
    protected void drawBackground(){
        Texture background = game.getAssetManager().get("menu_background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (WORLD_WIDTH / PIXEL_TO_METER), (int) (WORLD_HEIGHT / PIXEL_TO_METER));
    }

    private void createBtns()
    {
        TextButton singlePlayerBtn = createBtn("Single Player",camera.viewportHeight / 2, BUTTON_WIDTH,stage);
        singlePlayerBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                game.switchToGameView(1);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        TextButton multiplayerBtn = createBtn("Muliplayer",camera.viewportHeight / 2 - BTN_DISTANCE, BUTTON_WIDTH,stage);
        multiplayerBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
                if (accelerometerAvail)
                    game.switchToNetworkView();
                else
                    game.switchToGameView(2);

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        TextButton exitBtn = createBtn("Exit",camera.viewportHeight / 2 - 2*BTN_DISTANCE, BUTTON_WIDTH,stage);
        exitBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void drawTitle(float delta) {
        Texture title = game.getAssetManager().get("downFall.png",Texture.class);
        game.getBatch().draw(title,camera.viewportWidth/2 - (title.getWidth()/2),(camera.viewportHeight/2)+2*title.getHeight());
    }
}
