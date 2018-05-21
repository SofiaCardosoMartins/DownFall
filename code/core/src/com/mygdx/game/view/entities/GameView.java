package com.mygdx.game.view.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.DownFall;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.LavaModel;
import com.mygdx.game.model.entities.ObstacleModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;

public class GameView extends AppView {

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugCamera;
    Sprite backSprite;
    private static float CAMERA_SPEED = 1f;
    private static final boolean DEBUG_PHYSICS = true;

    public GameView(DownFall game)
    {
        super(game);
        loadAssets();
        createCamera();
    }

    @Override
    protected void createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        this.viewport = new ScreenViewport(camera);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

        camera.update();

        if(DEBUG_PHYSICS)
        {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1/PIXEL_TO_METER);
        }
        this.camera = camera;
    }

    @Override
    protected void loadAssets() {
        this.game.getAssetManager().load("landscape.png", Texture.class);
        this.game.getAssetManager().load("platform.png", Texture.class);
        this.game.getAssetManager().load("player.png", Texture.class);
        this.game.getAssetManager().load("background.png",Texture.class);
        this.game.getAssetManager().load("obstacle.png",Texture.class);
<<<<<<< HEAD
        this.game.getAssetManager().load("endosphere.png", Texture.class);
=======
        this.game.getAssetManager().load("fire.png",Texture.class);
>>>>>>> a25cda552e948dd5700e42d42fd5099144813237

        //end
        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {
        handleInputs(delta);
        GameController.getInstance().update(delta, camera);

        //move camera upwards
        camera.position.y += CAMERA_SPEED;
        camera.update();

        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        drawLava(delta);
        game.getBatch().end();

        if(DEBUG_PHYSICS)
        {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1/PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(),debugCamera);
        }
    }

    @Override
    protected void handleInputs(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            GameController.getInstance().moveLeft(1);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            GameController.getInstance().moveRight(1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            GameController.getInstance().jump(1);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            GameController.getInstance().moveLeft(2);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            GameController.getInstance().moveRight(2);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W)){
            GameController.getInstance().jump(2);
        }

    }

    @Override
    protected void drawEntities() {

        //Platforms
        List<PlatformModel> platforms = GameModel.getInstance().getPlatformsInUse();
        for (PlatformModel platform : platforms) {
            EntityView view = ViewFactory.makeView(game, platform);
            view.update(platform);
            view.draw(game.getBatch());
        }

        //Obstacles

        List<ObstacleModel> obstacles = GameModel.getInstance().getObstaclesInUse();
        for (ObstacleModel obstacle: obstacles) {
            EntityView view = ViewFactory.makeView(game, obstacle);
            view.update(obstacle);
            view.draw(game.getBatch());
        }

        //Players

        List<PlayerModel> players = GameModel.getInstance().getPlayers();
        for (PlayerModel player : players) {
            EntityView view = ViewFactory.makeView(game, player);
            view.update(player);
            view.draw(game.getBatch());
        }

    }
    @Override
    protected void drawBackground() {
        Texture background = game.getAssetManager().get("endosphere.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (WORLD_WIDTH / PIXEL_TO_METER), (int) (WORLD_HEIGHT / PIXEL_TO_METER));
    }

    private void drawLava(float delta)
    {
        EntityView view = ViewFactory.makeView(game, GameModel.getInstance().getLava());
        ((LavaView)view).update(delta, GameModel.getInstance().getLava());
        view.draw(game.getBatch());
    }
}
