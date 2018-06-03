package com.mygdx.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.DownFall;

/**
 * Screen related to the beginning of menu view
 */
public class NetworkMenuView extends MenuView {

    private static int BUTTON_WIDTH_LONG = 350;
    private static final float BTN_DISTANCE_LONG = 150;  //distance between buttons
    public NetworkMenuView(DownFall game) {
        super(game);
        //this.loadAssets();
        this.createCamera();
        setStage(new Stage(this.viewport));
        Gdx.input.setInputProcessor(getStage());
        setBtnSkin(new Skin(Gdx.files.internal("skin/comic-ui.json")));
        createBtns();
    }
    /**
     * Creates the Buttons needed for the screen
     */

    private void createBtns()
    {
        TextButton joinAnotherPlayerBtn = createBtn("Join Another Player",camera.viewportHeight / 2, BUTTON_WIDTH_LONG,stage);
        joinAnotherPlayerBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.switchToClientView();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton startNewGamePlayBtn = createBtn("Start new Gameplay",camera.viewportHeight / 2 + BTN_DISTANCE_LONG, BUTTON_WIDTH_LONG,stage);
        startNewGamePlayBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.switchToServerView();

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

    }


}
