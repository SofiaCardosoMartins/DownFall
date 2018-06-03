package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.DownFall;
import com.mygdx.game.model.GameModel;
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
            server = new Server(game);
            IP = server.getServerIP();
            if (server != null)
                server.start();
        } catch (Exception e){
            System.out.println("Couldn't create Server");
        } finally {
        }
    }

    @Override
    public void render(float delta) {
        if (game.startGame) {
            game.switchToGameView(2);
            GameModel.getInstance().updatePlayers();
            game.createdGame = true;
            return;
        }

        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        drawBackground();
        drawIP();
        game.getBatch().end();

        stage.act();

    }


    void drawIP(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Rubik-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels

        generator.dispose();
        font.setColor(Color.FOREST);
        font.getData().setScale(FONT_SCALE);
        IP = server.getServerIP();

        font.draw(game.getBatch(), IP , 200, 500);

    }

}
