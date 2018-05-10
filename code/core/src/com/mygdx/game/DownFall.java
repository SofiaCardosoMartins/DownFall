package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.GameView;
import com.badlogic.gdx.Game;

import java.util.Stack;

public class DownFall extends Game {
	SpriteBatch batch;

    AssetManager assetManager;
	Stack<AppView> views;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		startGame();
	}

	private void startGame(){
	    setScreen(new GameView(this));
	}

/*
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 20);
		batch.end();
	}
*/
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
