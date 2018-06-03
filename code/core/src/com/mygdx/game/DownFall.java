package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.entities.*;
import com.badlogic.gdx.Game;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.view.entities.AppView;
import com.mygdx.game.view.entities.GameView;
import com.mygdx.game.view.entities.MenuView;
import com.mygdx.game.view.entities.NetworkMenuView;
import com.mygdx.game.view.entities.PausedView;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * The game class
 */
public class DownFall extends Game {
	SpriteBatch batch;
    AssetManager assetManager;
	Stack<AppView> views;

	/**
	 * Initializes the bath, assetManager and stack of views
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		views = new Stack<AppView>();
		startGame();
	}

	/**
	 * Starts a new game
	 */
	private void startGame(){
	    views.push(new MenuView(this));
		setScreen(views.peek());
	}

	/**
	 * Sets the current screen to the game screen
	 * @param numPlayers The number of players in a new game
	 */
	public void switchToGameView(int numPlayers)
	{
		deleteGame();
		/*
		try {
			views.pop();
		} catch (EmptyStackException e){

		}
		*/
		views.pop().dispose();
		views.push(new GameView(this,numPlayers));
		setScreen(views.peek());
	}

	/**
	 * Switches the current view to the network screen
	 */
	public void switchToNetworkView()
	{
		views.pop();
		views.push(new NetworkMenuView(this));
		setScreen(views.peek());
	}

	/**
	 * Switches the current view to the server screen
	 */
	public void switchToServerView()
	{
		views.pop();
		views.push(new ServerMenuView(this));
		setScreen(views.peek());
	}

	/**
	 * Switches the current view to the client screen
	 */
	public void switchToClientView()
	{
		views.pop();
		views.push(new ClientMenuView(this));
		setScreen(views.peek());
	}

	/**
	 * Switches the current view to the lost screen
	 */
	public void switchToLostView()
	{
		views.pop();
		views.push(new LostView(this));
		setScreen(views.peek());
	}
	/**
	 * Switches the current view to the won screen
	 */
	public void switchToWonView()
	{
		views.pop();
		views.push(new WonView(this));
		setScreen(views.peek());
	}

	/**
	 * Switches the current view to the menu screen
	 */
	public void switchToMenuView()
	{
		views.pop();
		startGame();
	}

	/**
	 * Switches the current view to the paused screen
	 */
	public void switchToPausedView()
	{
		views.push(new PausedView(this));
		setScreen(views.peek());
	}

	/**
	 * Switches the current view to the resume game screen
	 */
	public void resumeGame()
	{
		GameController.getInstance().setPAUSED(false);
		views.pop();
		setScreen(views.peek());
		GameController.getInstance().restoreBoostsTime();
	}

	/**
	 * Deletes the previous game
	 */
	public void deleteGame()
	{
		GameController.delete();
	}

	/**
	 * Deletes useless items
	 */
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	/**
	 * @return The game's batch
	 */
    public SpriteBatch getBatch() {
        return batch;
    }

	/**
	 * @return The game's asset manager
	 */
	public AssetManager getAssetManager() {
        return assetManager;
    }

}
