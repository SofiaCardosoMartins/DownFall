package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.GameView;
import com.badlogic.gdx.Game;
import com.mygdx.game.view.entities.MenuView;

import java.util.Stack;

public class DownFall extends Game {
	SpriteBatch batch;
    AssetManager assetManager;
	Stack<AppView> views;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		views = new Stack<AppView>();
		startGame();
	}

	private void startGame(){
	    views.push(new MenuView(this));
		setScreen(views.peek());
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}


    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

}
