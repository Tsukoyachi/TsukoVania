package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Boot;

public class DesktopLauncher {
	/** 
	 * Class used to run the game 
	 */
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;
		config.title = "Metroidvania";
		config.backgroundFPS = 60;
		new LwjglApplication(new Boot(), config);
	}
}
