package com.mygdx.game.view.entities;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.DownFall;

import java.awt.*;

public class ClientMenuView extends MenuView {

    public ClientMenuView(DownFall game) {
        super(game);
        createTextField();
    }

    @Override
    public void render(float delta) {

        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        drawBackground();
        //drawIP();
        game.getBatch().end();

        stage.act();
        stage.draw();
    }
    void createTextField(){
        com.badlogic.gdx.scenes.scene2d.ui.TextField field = new TextField("he", btnSkin);
        field.setPosition(300, 300);
        field.setSize(300,100);

        stage.addActor(field);
    }
}
