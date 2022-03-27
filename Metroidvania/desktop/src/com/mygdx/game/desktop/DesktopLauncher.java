package com.mygdx.game.desktop;

// import com.badlogic.gdx.Files.FileType;
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
		config.foregroundFPS = 60;
		config.pauseWhenBackground = true; // could be changed, maybe if you use 2 screens, you don't want the game to
											// be paused when you just change your music, or answer a discord message
		config.pauseWhenMinimized = true;
		config.vSyncEnabled = true;
		// config.addIcon("Metroidvania/core/assets/player/standing-left",
		// FileType("png")); // j'y arrive pas
		new LwjglApplication(new Boot(), config);
	}
}
