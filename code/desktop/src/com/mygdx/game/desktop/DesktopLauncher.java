package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = GameController.TITLE;
		config.width = GameController.WORLD_WIDTH;
		config.height = GameController.WORLD_HEIGHT;

		new LwjglApplication(new DownFall(), config);
	}
}
