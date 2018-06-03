package com.mygdx.game.view.entities;

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

/**
 * A view representing the game screen. Draws all the other model's views and
 * controls the camera.
 */
public class GameView extends AppView {

    long lastCameraSpeedIncreaseTime; //in nanoseconds
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugCamera;
    private static float CAMERA_SPEED = 1;
    private static final boolean DEBUG_PHYSICS = false;
    private static final float CAMERA_SPEED_INC = 0.3f; //camera speed increment
    private static final float TIME_TO_NEXT_INC = 10;   //time between camera's speed increment (in seconds)
    private static final float FONT_SCALE  = 1.2f;
    private BarView barView;

    /**
     * Creates this screen.
     * @param game The game this screen belongs to
     */
    public GameView(DownFall game, int numPlayers) {
        super(game);
        GameModel.setPlayersCount(numPlayers);
        this.lastCameraSpeedIncreaseTime = System.nanoTime();
        loadAssets();
        createCamera();
        barView = new BarView(game, GameController.getInstance().isDoublePlayer());
    }

    /**
     * Initializes the view's camera
     */
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

    /**
     * Loads the assets needed by this screen.
     */
    @Override
    protected void loadAssets() {
        this.game.getAssetManager().load("landscape.png", Texture.class);
        this.game.getAssetManager().load("platform.png", Texture.class);
        this.game.getAssetManager().load("player1.png", Texture.class);
        this.game.getAssetManager().load("player2.png", Texture.class);
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

        this.game.getAssetManager().finishLoading();

    }

    /**
     * Renders this screen.
     * @param delta time since last renders in seconds
     */
    @Override
    public void render(float delta) {

        //update camera speed
        long elapsedTime = System.nanoTime() - this.lastCameraSpeedIncreaseTime;
        if (elapsedTime >= (TIME_TO_NEXT_INC * Math.pow(10, 9))) {
            boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
            if (accelerometerAvail) {
                CAMERA_SPEED += CAMERA_SPEED_INC/4;
            }
            else CAMERA_SPEED += CAMERA_SPEED_INC;
            this.lastCameraSpeedIncreaseTime = System.nanoTime();
        }

        checkGameState();
        if(!GameController.getInstance().getPAUSED()){
            handleInputs(delta);
            GameController.getInstance().update(delta, getMinCameraY(),getMaxCameraY());}

        //move camera upwards
        boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (accelerometerAvail) {
            camera.position.y += CAMERA_SPEED/2;
        }
        else {
            camera.position.y += CAMERA_SPEED;
        }
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

    /**
     * Checks if the game is on a paused or ended state
     */
    private void checkGameState()
    {
        checkPausedGame();
        checkEndGame();
    }

    /**
     * Checks if the game is on a paused state
     */
    private void checkPausedGame()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                game.switchToPausedView();
                GameController.getInstance().setPAUSED(true); }
    }

    /**
     * Checks if the game is on an ended state
     */
    public void checkEndGame()
    {
        if(GameController.getInstance().isEndGame())
        {
            if(GameController.getInstance().isLost())
                game.switchToLostView();
            else
                game.switchToWonView();
        }
    }

    /**
     * Handles any inputs and passes them to the controller
     * @param delta time since last time inputs where handled in seconds
     */
    @Override
    protected void handleInputs(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            GameController.getInstance().handleInput(GameController.Direction.LEFT,  1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            GameController.getInstance().handleInput(GameController.Direction.RIGHT, 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            GameController.getInstance().handleInput(GameController.Direction.UP,  1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            GameController.getInstance().handleInput(GameController.Direction.LEFT, 2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameController.getInstance().handleInput(GameController.Direction.RIGHT,  2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameController.getInstance().handleInput(GameController.Direction.UP, 2);
        }

        if(GameModel.getPlayersCount() == 1) {
            boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
            if (accelerometerAvail) {
                float acceX = Gdx.input.getAccelerometerX();
                float velX = Gdx.input.getX();
                float velY = Gdx.input.getDeltaY();

                if ((acceX < -2) && (acceX >= -8))
                    GameController.getInstance().handleInput(GameController.Direction.RIGHT,   1);
                else if ((acceX > 2) && (acceX < 6))
                    GameController.getInstance().handleInput(GameController.Direction.LEFT,   1);
                }
            if (Gdx.input.isTouched()) {
                GameController.getInstance().handleInput(GameController.Direction.UP,  1);
            }
        }
    }

    /**
     * Displays (draws) the remaining time of a boost belonging to a certain PlayerModel
     * @param playerModel The player which has a boost whose remaining time needs to be displayed
     */
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

    /**
     * Draws the entities to the screen.
     */
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
        for (BoostModel boost : boosts) {
            if(boost.getType() == EntityModel.ModelType.NATURAL_BOOST) continue;
            EntityView view = ViewFactory.makeView(game, boost);
            view.update(boost);
            view.draw(game.getBatch());
        }
    }

    /**
     * Draws the background
     */
    @Override
    protected void drawBackground() {

        Texture background = game.getAssetManager().get("endosphere.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (WORLD_WIDTH / PIXEL_TO_METER), (int) (WORLD_HEIGHT / PIXEL_TO_METER));
    }

    /**
     * Draws the lava after updating the corresponding LavaView
     * @param delta time since last renders in seconds
     */
    private void drawLava(float delta) {
        EntityView view = ViewFactory.makeView(game, GameModel.getInstance().getLava());
        ((LavaView) view).update(delta, GameModel.getInstance().getLava());
        view.draw(game.getBatch());
    }

    /**
     * Draws the score bar
     */
    private void drawBar(){
        barView.update(WORLD_WIDTH / 2, getMaxCameraY());
        barView.draw(game.getBatch());
    }

    /**
     * @return The maximum camera's y
     */
    private float getMaxCameraY() {
        return PIXEL_TO_METER * (camera.position.y + (camera.viewportHeight / 2));
    }

    /**
     * @return The minimum camera's y
     */
    private float getMinCameraY() {
        return PIXEL_TO_METER * (camera.position.y - (camera.viewportHeight / 2));
    }
}
