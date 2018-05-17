package com.mygdx.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.entities.PlatformController;
import com.mygdx.game.controller.entities.PlayerController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.PlatformModel;
import com.mygdx.game.model.entities.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class GameController implements ContactListener{

    private static GameController instance;
    public static final String TITLE = "DownFall";
    public static final int WORLD_WIDTH = 1080;
    public static final int WORLD_HEIGHT = 720;
    public static final Vector2 GRAVITY = new Vector2(0,-9.8f);
    private final World world;
    private List<PlayerController> playerControllers;
    private List<PlatformController> platformControllers;
    private float accumulator;

    private GameController()
    {
        world = new World(GRAVITY,true);

        //Create players bodies
        playerControllers = new ArrayList<PlayerController>();
        List<PlayerModel> playerModels = GameModel.getInstance().getPlayers();
        for(PlayerModel player: playerModels)
            playerControllers.add(new PlayerController(world,player));

        //Create platform bodies
        platformControllers = new ArrayList<PlatformController>();
        List<PlatformModel> platforms = GameModel.getInstance().getPlatformsInUse();
        for(PlatformModel platform: platforms)
            platformControllers.add(new PlatformController(world,platform));

        world.setContactListener(this);
    }

    public static GameController getInstance()
    {
        if(instance == null)
            instance = new GameController();
        return instance;
    }

    public void update(float delta)
    {
        GameModel.getInstance().update(delta);

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body body: bodies)
        {
            ((EntityModel)body.getUserData()).setPosition(body.getPosition().x,body.getPosition().y);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("contact!");
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

    public void moveLeft()
    {
        PlayerController player = playerControllers.get(0);
        float playerX = player.getX() - 0.1f;
        float playerY = player.getY();
        player.setTransform(playerX,playerY,0);
    }

    public World getWorld() {
        return world;
    }
}
