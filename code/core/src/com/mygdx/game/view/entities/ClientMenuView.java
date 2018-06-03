package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.DownFall;
import com.mygdx.game.network.Client;


/**
 * The Client Menu Screen - to be displayed in multiplayer android
 */
public class ClientMenuView extends MenuView {
    protected Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    protected float FONT_SCALE = 1.6f;
    private Stage stageC;
    TextField field;

    /**
     * Creates this screen.
     * @param game The game this screen belongs to
     */

    public ClientMenuView(DownFall game) {
        super(game);
        final DownFall g = game;
        stageC = new Stage(this.viewport);
        stageC.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    try{
                        new Client(g, field.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
                return false;
            }
        });
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(stageC);
        createTextField();

    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {

        game.getBatch().begin();
        drawBackground();
        drawLabel();
        game.getBatch().end();

        stageC.act();
        stageC.draw();
    }

    /**
     * Creates the text field where the user inputs the IP Address to join
     */
    void createTextField(){
        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(2*FONT_SCALE);

        field = new TextField("", btnSkin);
        field.setPosition(100, 400);
        field.setSize(500,100);
        field.setStyle(textFieldStyle);

        stageC.addActor(field);
    }

    /**
     * Draws an explanatory label for the user to input the IP Addresss
     */

    void drawLabel(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Rubik-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.color = Color.BLACK;
        BitmapFont font2 = generator.generateFont(parameter);

        font2.getData().setScale(FONT_SCALE);

        font2.draw(game.getBatch(), "Please insert the IP Address\n you see on you partner's screen", 50, 600);
    }
}
