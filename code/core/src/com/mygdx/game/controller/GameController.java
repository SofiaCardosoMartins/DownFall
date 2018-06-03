package com.mygdx.game.controller;

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
import com.mygdx.game.controller.entities.NoCollisionsBoostController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.*;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.EntityView;
import com.mygdx.game.view.entities.ViewFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;

/**
 * Controls the physics aspect of the game.
 */
public class GameController implements ContactListener {

    private static GameController instance;
    public static final String TITLE = "DownFall";
    public static final int WORLD_WIDTH = 10;
    public static final int WORLD_HEIGHT = 1050;
    public static final Vector2 GRAVITY = new Vector2(0, -3f);
    private final World world;
    private List<PlayerController> playerControllers;
    private List<PlatformController> platformControllers;
    private List<ObstacleController> obstacleControllers;
    private List<BoostController> boostControllers;
    private final LavaController lavaController;
    private float accumulator;
    private boolean PAUSED;
    private  boolean LOST;
    private boolean END;

    public enum Direction {LEFT, RIGHT, UP}

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     */
    private GameController() {
        PAUSED=false;
        LOST = false;
        END = false;
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
        List<BoostModel> boosts = GameModel.getInstance().getBoostsInUse();
        for (BoostModel boost : boosts)
            boostControllers.add(new NaturalBoostController(world, boost));

        //Create lava body
        this.lavaController = new LavaController(world, GameModel.getInstance().getLava(), BodyDef.BodyType.StaticBody, false);
        world.setContactListener(this);
    }

    /**
     * Returns all the controllers in the game
     * @return A list of all the controllers in the game
     */
    public List<EntityController> getAllControllers() {
        List<EntityController> controllers = new ArrayList<EntityController>();
        controllers.addAll(platformControllers);
        controllers.addAll(playerControllers);
        controllers.addAll(obstacleControllers);
        controllers.addAll(boostControllers);
        return controllers;
    }

    /**
     * Sets the controller of the given model as flagged from removal
     * @param model The model being removed in the game
     */
    public void remove(EntityModel model)
    {
        List<EntityController> entityControllers = getAllControllers();
        for(EntityController entityController: entityControllers)
        {
            EntityModel modelController = ((EntityModel)entityController.getBody().getUserData());
            if(model == modelController)
                entityController.setFlaggedForRemoval(true);
        }
    }

    /**
     * Adds a new controller of specified model to the game
     * @param entityModel The new model in the game
     * @return The controller of the new entity
     */
    public EntityController add(EntityModel entityModel)
    {
        EntityController entityController = null;
        switch (entityModel.getType())
        {
            case PLATFORM:
                entityController = new PlatformController(world, (PlatformModel) entityModel);
                platformControllers.add((PlatformController)entityController);
                break;
            case OBSTACLE:
                entityController = new ObstacleController(world, (ObstacleModel) entityModel);
                obstacleControllers.add((ObstacleController) entityController);
                break;
            case FLY_BOOST:
                entityController = new FlyBoostController(world, (FlyBoostModel) entityModel);
                boostControllers.add((FlyBoostController)entityController);
                break;
            case NO_COLLISIONS_BOOST:
                entityController = new NoCollisionsBoostController(world, (NoCollisionsBoostModel) entityModel);
                boostControllers.add((NoCollisionsBoostController)entityController);
                break;
        }
        return entityController;
    }

    /**
     * Returns a singleton instance of a game controller
     *
     * @return the singleton instance
     */
    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    /**
     * Updates the lava's position
     * @param minCameraY The minimum value of the camera's y position
     */
    private void updateLava(float minCameraY)
    {
        EntityModel em = (EntityModel)lavaController.getBody().getUserData();
        em.setY(minCameraY + (PIXEL_TO_METER * (lavaController.getHeight()/2)));
        this.lavaController.setY(minCameraY + (PIXEL_TO_METER * (lavaController.getHeight()/2)));
    }

    /**
     * Updates the obstacle's movement
     * @param minCameraY  The minimum value of the camera's y position
     * @param maxCameraY  The maximum value of the camera's y position
     */
    public void updateObstacles(float minCameraY, float maxCameraY) {
        List<ObstacleModel> obstacles = GameModel.getInstance().getObstaclesInUse();
        for (ObstacleController oc: obstacleControllers){
            float obstacleY = oc.getY();
            if((obstacleY < maxCameraY) && (obstacleY > minCameraY))
                oc.move();
        }
    }

    /**
     * Removes from the world the boosts which are no longer visible
     * @param minCameraY  The minimum value of the camera's y position
     */
    public void updateBoosts(float minCameraY) {
        for(BoostController boost: boostControllers){
            if(boost.getY() < minCameraY)
                GameModel.getInstance().remove((BoostModel)(boost.getBody().getUserData()));
        }
    }

    /**
     * Calculates the next physics step of duration delta (in seconds) and updates the bodies' states
     * @param delta The size of this physics step in seconds.
     * @param minCameraY  The minimum value of the camera's y position
     * @param maxCameraY  The maximum value of the camera's y position
     */
    public void update(float delta, float minCameraY, float maxCameraY) {

        GameModel.getInstance().update(minCameraY);

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        List<EntityController> entityControllers = getAllControllers();
        for(EntityController e: entityControllers)
            if(e.isFlaggedForRemoval()) {
                world.destroyBody(e.getBody());
                playerControllers.remove(e);
                platformControllers.remove(e);
                obstacleControllers.remove(e);
                boostControllers.remove(e);
        }

        this.updateLava(minCameraY);
        this.updateObstacles(minCameraY,maxCameraY);
        this.updateBoosts(minCameraY);

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        List<EntityController> controllers = getAllControllers();
        for (EntityController controller : controllers) {
            Body body = controller.getBody();
            verifyBounds(controller, minCameraY, maxCameraY);
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
            controller.update();
        }
    }

    /**
     * Sets the flag END to true and the flag LOST to the specified value
     * @param lost True if a player fell into the lava and false otherwise
     */
    public void endGame(boolean lost)
    {
        this.LOST = lost;
        this.END = true;
    }

    /**
     * Returns the value of the END flag
     * @return Returns true if a game ended and false otherwise
     */
    public boolean isEndGame()
    {
        return this.END;
    }

    /**
     * Returns the value of the LOST flag
     * @return Returns true if a game ended and a player lost and false otherwise
     */
    public boolean isLost()
    {
        return this.LOST;
    }

    /**
     * A contact between two objects was detected
     * @param contact the detected contact
     */
    @Override
    public void beginContact(Contact contact) {

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        if(((body1.getUserData() instanceof LavaModel) && (body2.getUserData() instanceof PlayerModel)) ||
                ((body1.getUserData() instanceof PlayerModel) && (body2.getUserData() instanceof LavaModel)))
            this.endGame(true);

        if(body1.getUserData() instanceof PlayerModel && body2.getUserData() instanceof BoostModel)
            playerBoostCollision(body1, body2);
        else if (body2.getUserData() instanceof  PlayerModel && body1.getUserData() instanceof  BoostModel)
            playerBoostCollision(body2, body1);

    }

    /**
     * Handles a collision between a boost and a player
     * @param player Player which suffered the collision
     * @param boost  Boost which suffered the collision
     */
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
        bc.setCAUGHT();
        pc.collisionHandler();
        ((PlayerModel) pc.getUserData()).setRemainingTime(bc.getRemainingTime());
        ((PlayerModel) pc.getUserData()).setBoostPresent(true);
        GameModel.getInstance().remove((BoostModel)(bc.getBody().getUserData()));
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

    /**
     * Handles the input of the user, affecting the player's movement
     * @param dir The direction of the player's movement
     * @param playerNum The player at which the input applies
     */
    public void handleInput(Direction dir, int playerNum)
    {
        PlayerController player = playerControllers.get(playerNum - 1);
        player.handleInput(dir);
    }

    /**
     * Checks if a body is colliding with the world's left wall
     * @param ec The controller whose position is going to be testes
     * @param width The width of the controller's body
     */
    public void checkLeftWallCollision(EntityController ec, float width) {
        Body body = ec.getBody();
        if (body.getPosition().x < (width / 2)) {
            body.setTransform((width / 2), body.getPosition().y, body.getAngle());
            ec.leftWallCollision();
        }
    }

    /**
     * Checks if a body is colliding with the world's right wall
     * @param ec The controller whose position is going to be testes
     * @param width The width of the controller's body
     */
    public void checkRightWallCollision(EntityController ec, float width) {
        Body body = ec.getBody();
        if (body.getPosition().x > (WORLD_WIDTH - (width / 2))) {
            body.setTransform(WORLD_WIDTH - (width / 2), body.getPosition().y, body.getAngle());
            ec.rightWallCollision();
        }
    }

    /**
     * Checks if a body is colliding with the world's upper wall
     * @param ec The controller whose position is going to be testes
     * @param maxCameraY The maximum value of the camera's y position
     * @param height The height of the controller's body
     */
    public void checkUpWallCollision(EntityController ec, float maxCameraY, float height) {
        Body body = ec.getBody();
        if ((body.getPosition().y + (height / 2)) > maxCameraY) {
            body.setLinearVelocity(0, 0);
            body.setAngularVelocity(0);
            body.setTransform(body.getPosition().x, maxCameraY - height, body.getAngle());
        }
    }

    /**
     * Checks if a body is colliding with the world's bottom wall
     * @param ec The controller whose position is going to be testes
     * @param minCameraY The minimum value of the camera's y position
     * @param height The height of the controller's body
     */
    public void checkDownWallCollision(EntityController ec, float minCameraY, float height) {
        Body body = ec.getBody();
        if (body.getPosition().y < (minCameraY + (height / 2))) {
            if (body.getUserData() instanceof PlayerModel)
                System.exit(31);
        }
    }

    /**
     * Checks if the controller is within the camera's section
     * @param ec The controller whose position is going to be testes
     * @param minViewportY The minimum value of the camera's y position
     * @param maxViewportY The maximum value of the camera's y position
     */
    private void verifyBounds(EntityController ec,float minViewportY, float maxViewportY) {
        if((ec instanceof PlatformController) || (ec instanceof ObstacleController)) return;

        EntityView ev = ViewFactory.makeView(AppView.game, (EntityModel) ec.getBody().getUserData());
        float height = ev.getSprite().getHeight() * PIXEL_TO_METER;
        float width = ev.getSprite().getWidth() * PIXEL_TO_METER;

        this.checkLeftWallCollision(ec, width);
        this.checkRightWallCollision(ec, width);
        this.checkUpWallCollision(ec, maxViewportY, height);
        this.checkDownWallCollision(ec, minViewportY, height);
    }

    /**
     * Sets the value of the PAUSED flag and updates the boost's tiem
     * @param paused The value of the PAUSED flag
     */
    public void setPAUSED(boolean paused)
    {
        PAUSED = paused;
        if(!paused)
            GameController.getInstance().restoreBoostsTime();
    }

    /**
     * Returns the value of the PAUSED flag
     * @return The value of the PAUSED flag
     */
    public boolean getPAUSED()
    {
        return PAUSED;
    }

    /**
     * Deletes the current game
     */
    public static void delete()
    {
        instance = null;
        GameModel.delete();
    }

    /**
     * Updates the boost's time in each player
     */
    public void restoreBoostsTime()
    {
        for(PlayerController playerController: playerControllers)
            playerController.updateStrategyTime();
    }

    /**
     * Returns true if the current game has two players
     * @return True if the current game has two players and false otherwise
     */
    public boolean isDoublePlayer(){
        return (this.playerControllers.size() == 2);
    }

    /**
     * Returns the game's world
     * @return The game's world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns the list of the players' controllers
     * @return The list of the players' controllers
     */
    public List<PlayerController> getPlayerControllers() {
        return playerControllers;
    }

    /**
     * Returns the lava's controller
     * @return The lava's controller
     */
    public LavaController getLavaController() {
        return lavaController;
    }

    /**
     * Returns the list of the platform' controllers
     * @return The list of the platform' controllers
     */
    public List<PlatformController> getPlatformControllers() {
        return platformControllers;
    }

    /**
     * Returns the list of the obstacles' controllers
     * @return The list of the obstacles' controllers
     */
    public List<ObstacleController> getObstacleControllers() {
        return obstacleControllers;
    }

    /**
     * Returns the list of the boosts' controllers
     * @return The list of the boosts' controllers
     */
    public List<BoostController> getBoostControllers() {
        return boostControllers;
    }

    /**
     * Returns the first player of the game
     * @return The first player of the game
     */
    public PlayerController getFirstPlayer()
    {
        if(! playerControllers.isEmpty())
            return playerControllers.get(0);
        else
            return null;
    }
}
