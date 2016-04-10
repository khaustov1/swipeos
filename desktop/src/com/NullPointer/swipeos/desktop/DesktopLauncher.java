package com.NullPointer.swipeos.desktop;

import com.NullPointer.swipeos.OnLoadListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.NullPointer.swipeos.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 600;
		config.width = 350;
		new LwjglApplication(new Game(new OnLoadListener()
		{
			@Override
			public void onLoad()
			{
				//Very important to run this on the UI thread or it will crash
			}

		}), config);
	}
}
