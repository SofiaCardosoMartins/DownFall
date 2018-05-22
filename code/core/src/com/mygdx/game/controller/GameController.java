package com.mygdx.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.entities.EntityController;
import com.mygdx.game.controller.entities.LavaController;
import com.mygdx.game.controller.entities.PlatformController;
import com.mygdx.game.controller.entities.PlayerController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;
import java.util.ArrayList;
import java.util.List;


import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

public class GameController implements ContactListener {

    private static GameController instance;
    public static final String TITLE = "DownFall";
    public static final int WORLD_WIDTH = 10;
    public static final int WORLD_HEIGHT = 1050;
    public static final Vector2 GRAVITY = new Vector2(0, -0.4f);
    private final World world;
    private List<PlayerController> playerControllers;
    private List<PlatformController> platformControllers;
    private final LavaController lavaController;
    private float accumulator;

    private GameController() {
        world = new World(GRAVITY, true);

        //Create players bodies
        playerControllers = new ArrayList<PlayerController>();
        List<PlayerModel> playerModels = GameModel.getInstance().getPlayers();
        for (PlayerModel player : playerModels)
            playerControllers.add(new PlayerController(world, player));

        //Create platform bodies
        platformControllers = new ArrayList<PlatformController>();
        List<PlatformModel> platforms = GameModel.getInstance().getPlatformsInUse();
        for (PlatformModel platform : platforms)
            platformControllers.add(new PlatformController(world, platform));

        //Create lava body
        this.lavaController = new LavaController(world, GameModel.getInstance().getLava(), BodyDef.BodyType.StaticBody, false);
        world.setContactListener(this);
    }

    public List<EntityController> getAllControllers() {
        List<EntityController> controllers = new ArrayList<EntityController>();
        controllers.addAll(platformControllers);
        controllers.addAll(playerControllers);
        return controllers;
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public void updatePlatforms() {
        this.platformControllers.clear();
        List<PlatformModel> platforms = GameModel.getInstance().getPlatformsInUse();
        for (PlatformModel platform : platforms)
            platformControllers.add(new PlatformController(world, platform));
    }

    public void update(float delta, OrthographicCamera camera) {
        GameModel.getInstance().update(delta, camera);

        this.updateLava(camera);

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        this.updatePlatforms();

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        List<EntityController> controllers = getAllControllers();
        for (EntityController controller : controllers) {
            Body body = controller.getBody();
            verifyBounds(controller, camera);
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }
    }

    private void endGame()
    {
        System.out.println("O jogo terminou");
        System.exit(121);
    }

    private void updateLava(OrthographicCamera camera)
    {
        EntityModel em = (EntityModel)lavaController.getBody().getUserData();
        em.setY(getMinCameraY(camera)+ (PIXEL_TO_METER * (lavaController.getHeight()/2)));
        this.lavaController.setY(getMinCameraY(camera) + (PIXEL_TO_METER * (lavaController.getHeight()/2)));
    }

    @Override
    public void beginContact(Contact contact) {
        EntityModel.ModelType mt1 = ((EntityModel)contact.getFixtureA().getBody().getUserData()).getType();
        EntityModel.ModelType mt2 = ((EntityModel)contact.getFixtureB().getBody().getUserData()).getType();

        if((mt1 == EntityModel.ModelType.LAVA && mt2 == EntityModel.ModelType.PLAYER) ||
                (mt2 == EntityModel.ModelType.LAVA && mt1 == EntityModel.ModelType.PLAYER))
            this.endGame();
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void moveLeft(int playerNum) {
        PlayerController player = playerControllers.get(playerNum - 1);
        player.getBody().applyForceToCenter(-50, 0, true);
    }

    public void moveRight(int playerNum) {
        PlayerController player = playerControllers.get(playerNum - 1);
        player.getBody().applyForceToCenter(50, 0, true);
    }

    public void jump(int playerNum) {
        PlayerController player = playerControllers.get(playerNum - 1);
        player.getBody().applyForceToCenter(0, 200, true);
    }

    private void checkLeftWallCollision(EntityController ec, float width) {
        Body body = ec.getBody();
        if (body.getPosition().x < (width / 2)) {
            body.setTransform((width / 2), body.getPosition().y, body.getAngle());
            ec.leftWallCollision();
        }
    }

    private void checkRightWallCollision(EntityController ec, float width) {
        Body body = ec.getBody();
        if (body.getPosition().x > (WORLD_WIDTH - (width / 2))) {
            body.setTransform(WORLD_WIDTH - (width / 2), body.getPosition().y, body.getAngle());
            ec.rightWallCollision();
        }
    }

    private void checkUpWallCollision(EntityController ec, float maxCameraY, float height) {
        Body body = ec.getBody();
        if ((body.getPosition().y + (height / 2)) > maxCameraY) {
            body.setLinearVelocity(0, 0);
            body.setAngularVelocity(0);
            body.setTransform(body.getPosition().x, maxCameraY - height, body.getAngle());
        }
    }

    private void checkDownWallCollision(EntityController ec, float minCameraY, float height) {
        Body body = ec.getBody();
        if (body.getPosition().y < (minCameraY + (height / 2))) {
            if (body.getUserData() instanceof PlayerModel)
                System.exit(31);
        }
    }

    public float getMaxCameraY(OrthographicCamera camera) {
        return PIXEL_TO_METER * (camera.position.y + (camera.viewportHeight / 2));
    }

    public float getMinCameraY(OrthographicCamera camera) {
        return PIXEL_TO_METER * (camera.position.y - (camera.viewportHeight / 2));
    }

    private void verifyBounds(EntityController ec, OrthographicCamera camera) {
        if(ec instanceof PlatformController) return;
        float maxCameraY = getMaxCameraY(camera);
        float minCameraY = getMinCameraY(camera);

        EntityView ev = ViewFactory.makeView(AppView.game, (EntityModel) ec.getBody().getUserData());
        float height = ev.getSprite().getHeight() * PIXEL_TO_METER;
        float width = ev.getSprite().getWidth() * PIXEL_TO_METER;

        this.checkLeftWallCollision(ec, width);
        this.checkRightWallCollision(ec, width);
        this.checkUpWallCollision(ec, maxCameraY, height);
        this.checkDownWallCollision(ec, minCameraY, height);
    }

    public boolean isDoublePlayer(){
        return (this.playerControllers.size() == 2);
    }
    public World getWorld() {
        return world;
    }

    public List<PlayerController> getPlayerControllers() {
        return playerControllers;
    }
}
