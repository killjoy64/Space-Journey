package edu.gvsu.cis.spacejourney;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

public class IOSLauncher extends IOSApplication.Delegate {
  @Override
  protected IOSApplication createApplication() {
    IOSApplicationConfiguration config = new IOSApplicationConfiguration();
    return new IOSApplication(new SpaceJourney(), config);
  }

  /**
   * Main method that creates an instance of the Space Journey application for iOS.
   * @param argv args from a java program.
   */
  public static void main(String[] argv) {
    NSAutoreleasePool pool = new NSAutoreleasePool();
    UIApplication.main(argv, null, IOSLauncher.class);
    pool.close();
  }
}