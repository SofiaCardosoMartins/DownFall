package com.mygdx.game.view.entities;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.DownFall;

public class GameView extends AppView {

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugCamera;

    GameView(DownFall game)
    {
        super(game);
        createCamera();
    }

    @Override
    protected void createCamera() {

    }

    @Override
    protected void loadAssets() {

    }

    @Override
    protected void render(float delta) {

    }

    @Override
    protected void handleInputs(float delta) {

    }

    @Override
    protected void drawEntities() {

    }
}
