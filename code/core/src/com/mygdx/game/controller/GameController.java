package com.mygdx.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class GameController implements ContactListener{

    private static GameController instance;
    public static final String TITLE = "DownFall";
    public static final int WORLD_WIDTH = 1080;
    public static final int WORLD_HEIGHT = 720;
    public static final Vector2 GRAVITY = new Vector2(0,-9.8f);
    private final World world;

    private GameController()
    {
        world = new World(GRAVITY,true);
        world.setContactListener(this);
    }

    public static GameController getInstance()
    {
        if(instance == null)
            instance = new GameController();
        return instance;
    }

    @Override
    public void beginContact(Contact contact) {

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
}
