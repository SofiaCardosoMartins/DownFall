package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.entities.*;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.*;
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
    public static final Vector2 GRAVITY = new Vector2(0, -9f);
    private final World world;
    private List<PlayerController> playerControllers;
    private List<PlatformController> platformControllers;
    private List<ObstacleController> obstacleControllers;
    private List<BoostController> boostControllers;
    private final LavaController lavaController;
    private float accumulator;

    public enum Direction {LEFT, RIGHT, UP}

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

        //Create obstacle bodies
        obstacleControllers = new ArrayList<ObstacleController>();
        List<ObstacleModel> obstacles = GameModel.getInstance().getObstaclesInUse();
        for (ObstacleModel obstacle : obstacles)
            obstacleControllers.add(new ObstacleController(world, obstacle));

        //Create boost bodies
        boostControllers = new ArrayList<BoostController>();
        //List<BoostModel> boosts = GameModel.getInstance().getBoostsInUse();
        //for (BoostModel boost : boosts)
        //    boostControllers.add(new BoostController(world, boost));

        //Create lava body
        this.lavaController = new LavaController(world, GameModel.getInstance().getLava(), BodyDef.BodyType.StaticBody, false);
        world.setContactListener(this);
    }

    public List<EntityController> getAllControllers() {
        List<EntityController> controllers = new ArrayList<EntityController>();
        controllers.addAll(platformControllers);
        controllers.addAll(playerControllers);
        controllers.addAll(obstacleControllers);
        return controllers;
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    private void updateLava(OrthographicCamera camera)
    {
        EntityModel em = (EntityModel)lavaController.getBody().getUserData();
        em.setY(getMinCameraY(camera)+ (PIXEL_TO_METER * (lavaController.getHeight()/2)));
        this.lavaController.setY(getMinCameraY(camera) + (PIXEL_TO_METER * (lavaController.getHeight()/2)));
    }

    public void updatePlatforms() {
        this.platformControllers.clear();
        List<PlatformModel> platforms = GameModel.getInstance().getPlatformsInUse();
        for (PlatformModel platform : platforms)
            platformControllers.add(new PlatformController(world, platform));
    }

    public void updateObstacles(float minCameraY, float maxCameraY) {
        for(ObstacleController obstacle: obstacleControllers)
            world.destroyBody(obstacle.getBody());

        this.obstacleControllers.clear();

        List<ObstacleModel> obstacles = GameModel.getInstance().getObstaclesInUse();
        for (ObstacleModel obstacle : obstacles) {
            ObstacleController oc = new ObstacleController(world, obstacle);
            obstacleControllers.add(oc);
            float obstacleY = obstacle.getY();
            if((obstacleY < maxCameraY) && (obstacleY > minCameraY))
                oc.move();
        }
    }

    public void update(float delta, OrthographicCamera camera) {
        GameModel.getInstance().update(delta, camera);

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        this.updateLava(camera);
        this.updatePlatforms();
        this.updateObstacles(getMinCameraY(camera),getMaxCameraY(camera));

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        List<EntityController> controllers = getAllControllers();
        for (EntityController controller : controllers) {
            Body body = controller.getBody();
            verifyBounds(controller, camera);
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
            controller.update();
        }
    }

    private void endGame()
    {
        System.out.println("O jogo terminou");
        System.exit(121);
    }

    @Override
    public void beginContact(Contact contact) {

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        if(((body1.getUserData() instanceof LavaModel) && (body2.getUserData() instanceof PlayerModel)) ||
                ((body1.getUserData() instanceof PlayerModel) && (body2.getUserData() instanceof LavaModel)))
            this.endGame();

        if(body1.getUserData() instanceof PlayerModel && body2.getUserData() instanceof BoostModel)
            playerBoostCollision(body1, body2);
        else if (body2.getUserData() instanceof  PlayerModel && body1.getUserData() instanceof  BoostModel)
            playerBoostCollision(body2, body1);

    }

    private void playerBoostCollision(Body player, Body boost){
        PlayerController pc = null;

        for (PlayerController playerController: playerControllers){
            if (playerController.getBody() == player)
                pc = playerController;
        }
        BoostController bc = null;
        for (BoostController boostController: boostControllers){
            if (boostController.getBody() == boost)
                bc = boostController;
        }
        pc.setStrategy(bc);
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

    public void handleInput(Direction dir, int playerNum)
    {
        PlayerController player = playerControllers.get(playerNum - 1);
        player.handleInput(dir);
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
        if((ec instanceof PlatformController) || (ec instanceof ObstacleController)) return;
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
