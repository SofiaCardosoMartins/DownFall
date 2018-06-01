package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.DownFall;
import com.mygdx.game.network.Server;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


public class ServerMenuView extends MenuView {
    private static final float FONT_SCALE  = 4f;
    private Server server;
    private String IP;

    public ServerMenuView(DownFall game) {
        super(game);
        this.createCamera();
        setStage(new Stage(this.viewport));
        Gdx.input.setInputProcessor(getStage());

        try {
            server = new Server();
        } catch (Exception e){
            System.out.println("exception");
        }

        IP = server.getServerIP();

    }

    @Override
    public void render(float delta) {

        game.getBatch().setProjectionMatrix(camera.combined);


        //Gdx.input.getTextInput(listener, "Dialog Title", "Initial Textfield Value", "Hint Value");

        game.getBatch().begin();
        drawBackground();
        drawIP();
        game.getBatch().end();

        stage.act();

    }


    void drawIP(){
        BitmapFont bm = new BitmapFont();
        bm.setColor(Color.FOREST);
        bm.getData().setScale(FONT_SCALE);
        bm.draw(game.getBatch(), IP , 200, 500);
        System.out.println(IP);
    }

}
