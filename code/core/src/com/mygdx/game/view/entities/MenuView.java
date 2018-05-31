package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
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

    private Stage stage;
    private Skin btnSkin;
    private TitleView titleView;

    public MenuView(DownFall game) {
        super(game);
        this.loadAssets();
        this.createCamera();
        this.titleView = new TitleView(game);
        stage = new Stage(this.viewport);
        Gdx.input.setInputProcessor(stage);
        btnSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
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
        this.game.getAssetManager().load("title.png", Texture.class);
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
        TextButton singlePlayerBtn = createBtn("Single Player",camera.viewportHeight / 2);
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
        TextButton multiplayerBtn = createBtn("Muliplayer",camera.viewportHeight / 2 - BTN_DISTANCE);
        multiplayerBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.switchToGameView(2);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        TextButton exitBtn = createBtn("Exit",camera.viewportHeight / 2 - 2*BTN_DISTANCE);
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

    private TextButton createBtn(String text, float y)
    {
        TextButton textButton = new TextButton(text,btnSkin);
        textButton.getLabel().setFontScale(FONT_SCALE,FONT_SCALE);
        textButton.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        textButton.setPosition((camera.viewportWidth/2)-(BUTTON_WIDTH/2),y - (BUTTON_HEIGHT/2));
        stage.addActor(textButton);
        return textButton;
    }

    private void drawTitle(float delta) {
        titleView.update(delta);
        titleView.sprite.setPosition(camera.viewportWidth/2 - (titleView.sprite.getWidth()/2),camera.viewportHeight-4*(titleView.sprite.getHeight()));
        titleView.draw(game.getBatch());
    }
}
