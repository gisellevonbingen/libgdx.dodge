package giselle.dodge.desktop;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import giselle.dodge.Dodge;

public class DesktopLauncher
{
	public static void main(String[] arg) throws IOException
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 540;
		// config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());

		config.foregroundFPS = config.backgroundFPS = 240;
		config.vSyncEnabled = false;

		new LwjglApplication(new Dodge(new DesktopControllerProvider()), config);
	}

}
