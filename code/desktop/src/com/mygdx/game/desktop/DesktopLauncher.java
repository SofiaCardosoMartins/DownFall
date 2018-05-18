package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;

import static com.mygdx.game.view.entities.AppView.PIXEL_TO_METER;
import static com.mygdx.game.controller.GameController.WORLD_WIDTH;
import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = GameController.TITLE;
		config.width = (int) (WORLD_WIDTH / PIXEL_TO_METER);
		config.height = (int) (WORLD_HEIGHT / PIXEL_TO_METER);

		new LwjglApplication(new DownFall(), config);
	}
}
