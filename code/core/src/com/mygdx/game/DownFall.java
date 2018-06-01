package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.entities.*;
import com.badlogic.gdx.Game;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.GameView;
import com.badlogic.gdx.Game;
import com.mygdx.game.view.entities.MenuView;
import com.mygdx.game.view.entities.NetworkMenuView;
import com.mygdx.game.view.entities.PausedView;

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

	public void switchToGameView(int numPlayers)
	{
		deleteGame();
		views.pop();
		views.push(new GameView(this,numPlayers));
		setScreen(views.peek());
	}

	public void switchToNetworkView()
	{
		views.pop();
		views.push(new NetworkMenuView(this));
		setScreen(views.peek());
	}

	public void switchToServerView()
	{
		views.pop();
		views.push(new ServerMenuView(this));
		setScreen(views.peek());
	}

	public void switchToMenuView()
	{
		views.pop();
		startGame();
	}

	public void switchToPausedView()
	{
		views.push(new PausedView(this));
		setScreen(views.peek());
	}

	public void resumeGame()
	{
		GameController.setPAUSED(false);
		views.pop();
		setScreen(views.peek());
	}

	public void deleteGame()
	{
		GameController.delete();
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
