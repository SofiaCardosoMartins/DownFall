package com.mygdx.game.view.entities;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DownFall;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.model.GameModel;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;

public class GameView extends AppView {

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugCamera;
    Sprite backSprite;

    public GameView(DownFall game)
    {
        super(game);
        loadAssets();
        createCamera();
    }

    @Override
    protected void createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        this.camera = camera;

    }

    @Override
    protected void loadAssets() {
        this.game.getAssetManager().load("landscape.png", Texture.class);


        //end
        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        //drawEntities();
        game.getBatch().end();
    }

    @Override
    protected void handleInputs(float delta) {

    }

    @Override
    protected void drawEntities() {

    }
    @Override
    protected void drawBackground(){
        Texture background = game.getAssetManager().get("landscape.png", Texture.class);
        backSprite = new Sprite(background);
        //game.getBatch().draw(background, 0, 0, 0, 0, (int)(WORLD_WIDTH / PIXEL_TO_METER), (int) (WORLD_HEIGHT / PIXEL_TO_METER));
        backSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT );
        game.getBatch().draw(backSprite, 0, 0);
    }
}
