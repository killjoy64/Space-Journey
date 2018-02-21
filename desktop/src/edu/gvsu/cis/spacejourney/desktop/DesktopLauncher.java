package edu.gvsu.cis.spacejourney.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class DesktopLauncher {

  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    config.width = 1920;
    config.height = 1080;

    config.fullscreen = true;
    config.vSyncEnabled = true;
    
    new LwjglApplication(new SpaceJourney(), config);
  }

}
