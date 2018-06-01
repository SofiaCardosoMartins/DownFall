package com.mygdx.game.view.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.DownFall;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.BoostModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.ObstacleModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;

import java.util.List;

import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;

public class GameView extends AppView {

    long lastCameraSpeedIncreaseTime; //in nanoseconds
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugCamera;
    private static float CAMERA_SPEED = 1;
    private static final boolean DEBUG_PHYSICS = false;
    private static final float CAMERA_SPEED_INC = 0; //camera speed increment
    private static final float TIME_TO_NEXT_INC = 10;   //time between camera's speed increment (in seconds)
    private static final float FONT_SCALE  = 1.2f;
    private BarView barView;

    public GameView(DownFall game, int numPlayers) {
        super(game);
        this.lastCameraSpeedIncreaseTime = System.nanoTime();
        loadAssets();
        createCamera();
        GameModel.PLAYERS_COUNT = numPlayers;
        barView = new BarView(game, GameController.getInstance().isDoublePlayer());
    }

    @Override
    protected void createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) 14 / (float) 10));
        this.viewport = new StretchViewport(WORLD_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) 14 / (float) 10) );
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }
        this.camera = camera;
    }

    @Override
    protected void loadAssets() {
        this.game.getAssetManager().load("landscape.png", Texture.class);
        this.game.getAssetManager().load("platform.png", Texture.class);
        this.game.getAssetManager().load("player.png", Texture.class);
        this.game.getAssetManager().load("endosphere.png", Texture.class);
        this.game.getAssetManager().load("astenosphere.png", Texture.class);
        this.game.getAssetManager().load("mesosphere.png", Texture.class);
        this.game.getAssetManager().load("litosphere.png", Texture.class);
        this.game.getAssetManager().load("transition1.png", Texture.class);
        this.game.getAssetManager().load("transition2.png", Texture.class);
        this.game.getAssetManager().load("transition3.png", Texture.class);
        this.game.getAssetManager().load("obstacle.png", Texture.class);
        this.game.getAssetManager().load("fire.png", Texture.class);
        this.game.getAssetManager().load("largeBarDouble.png", Texture.class);
        this.game.getAssetManager().load("largeBarSingle.png", Texture.class);
        this.game.getAssetManager().load("scorePointer.png", Texture.class);
        this.game.getAssetManager().load("flyBoost.png", Texture.class);
        this.game.getAssetManager().load("noCollisionsBoost.png", Texture.class);

        //end
        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {

        //update camera speed
        long elapsedTime = System.nanoTime() - this.lastCameraSpeedIncreaseTime;
        if (elapsedTime >= (TIME_TO_NEXT_INC * Math.pow(10, 9))) {
            CAMERA_SPEED += CAMERA_SPEED_INC;
            this.lastCameraSpeedIncreaseTime = System.nanoTime();
        }

        checkPausedGame();

        if(!GameController.getPAUSED()){
            handleInputs(delta);
            GameController.getInstance().update(delta, camera);}

        //move camera upwards
        camera.position.y += CAMERA_SPEED;
        camera.update();



        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        game.getBatch().begin();
        drawBackground();
        drawEntities(delta);
        drawLava(delta);
        drawBar();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }

    private void checkPausedGame()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                game.switchToPausedView();
                GameController.setPAUSED(true); }
    }

    @Override
    protected void handleInputs(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            GameController.getInstance().handleInput(GameController.Direction.LEFT,1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            GameController.getInstance().handleInput(GameController.Direction.RIGHT,1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            GameController.getInstance().handleInput(GameController.Direction.UP,1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            GameController.getInstance().handleInput(GameController.Direction.LEFT,2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameController.getInstance().handleInput(GameController.Direction.RIGHT,2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameController.getInstance().handleInput(GameController.Direction.UP,2);
        }
/*
        boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (accelerometerAvail) {
            float acceX = Gdx.input.getAccelerometerX();
            float acceY = Gdx.input.getAccelerometerY();
            float acceZ = Gdx.input.getAccelerometerZ();
            if (acceX < 0)
                GameController.getInstance().handleInput(GameController.Direction.RIGHT,1);
            else if (acceX > 0)
                GameController.getInstance().handleInput(GameController.Direction.LEFT,1);
        }
        if (Gdx.input.isTouched())
            GameController.getInstance().handleInput(GameController.Direction.UP,1);
*/
    }

    private void drawBitMapFont(PlayerModel playerModel)
    {
        if(playerModel.isBoostPresent())
        {
            float width = ViewFactory.getWidth(game,playerModel);
            float height = ViewFactory.getHeigth(game,playerModel);
            BitmapFont bm = new BitmapFont();
            bm.setColor(Color.FOREST);
            bm.getData().setScale(FONT_SCALE);
            bm.draw(game.getBatch(),String.valueOf(playerModel.getRemainingTime()),playerModel.getX()/PIXEL_TO_METER + width/2,playerModel.getY()/PIXEL_TO_METER + height/2);
        }
    }

    @Override
    protected void drawEntities(float delta) {

        //Platforms
        List<PlatformModel> platforms = GameModel.getInstance().getPlatformsInUse();
        for (PlatformModel platform : platforms) {
            EntityView view = ViewFactory.makeView(game, platform);
            view.update(platform);
            view.draw(game.getBatch());
        }

        //Obstacles

        List<ObstacleModel> obstacles = GameModel.getInstance().getObstaclesInUse();
        for (ObstacleModel obstacle : obstacles) {
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
            drawBitMapFont(player);
        }

        //Boosts

        List<BoostModel> boosts = GameModel.getInstance().getBoostsInUse();
        System.out.println("numero de boosts: " + boosts.size());
        for (BoostModel boost : boosts) {
            if(boost.getType() == EntityModel.ModelType.NATURAL_BOOST) continue;
            EntityView view = ViewFactory.makeView(game, boost);
            view.update(boost);
            view.draw(game.getBatch());
        }
    }

    @Override
    protected void drawBackground() {

        Texture background = game.getAssetManager().get("endosphere.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (WORLD_WIDTH / PIXEL_TO_METER), (int) (WORLD_HEIGHT / PIXEL_TO_METER));
        /*
        if(camera.position.y >= 0 && camera.position.y <= 9000) {

        }

        else {
            Texture background = game.getAssetManager().get("transition1.png", Texture.class);
            background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            game.getBatch().draw(background, 0, 0, 0, 0, (int) (WORLD_WIDTH / PIXEL_TO_METER), (int) (WORLD_HEIGHT / PIXEL_TO_METER));
        }
        */


    }

    private void drawLava(float delta) {
        EntityView view = ViewFactory.makeView(game, GameModel.getInstance().getLava());
        ((LavaView) view).update(delta, GameModel.getInstance().getLava());
        view.draw(game.getBatch());
    }

    private void drawBar(){
        barView.update(WORLD_WIDTH / 2, GameController.getInstance().getMaxCameraY(camera));
        barView.draw(game.getBatch());
    }
}
