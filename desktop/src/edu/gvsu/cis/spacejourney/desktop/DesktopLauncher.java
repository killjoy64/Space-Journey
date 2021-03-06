package edu.gvsu.cis.spacejourney.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class DesktopLauncher {

  /**
   * Main method that launches the Space Journey application as a desktop application.
   * @param arg the arg that comes from running a java program.
   */
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    /*config.width = 800;
    config.height = 600;
    config.fullscreen = false;*/

    config.width = 1920;
    config.height = 1080;
    config.fullscreen = true;

    config.vSyncEnabled = true;

    new LwjglApplication(new SpaceJourney(), config);
  }

}
