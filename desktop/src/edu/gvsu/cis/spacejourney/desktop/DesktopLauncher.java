package edu.gvsu.cis.spacejourney.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class DesktopLauncher {

  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.useHDPI = false;
    config.width = 896;
    config.height = 504;
    config.vSyncEnabled = true;
    new LwjglApplication(new SpaceJourney(), config);
  }

}
