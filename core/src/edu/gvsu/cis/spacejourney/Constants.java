package edu.gvsu.cis.spacejourney;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Static class that holds vital information to be
 * used across the project. This is specifically used
 * for screen dimensions and unit conversions.
 */
public final class Constants {
	
  /**
   * This class should not be instantiated.
   */
  private Constants() { }
	
  /**
   * Virtual width that the screen represents.
   * The amount of pixels per meter on
   * the screen for Box2D.
   */
  public static final float PX_PER_M = 100;

  /**
   * Get the virtual width that the screen represents. This is dependent on the platform.
   * @return Virtual width that the screen represents.
   */
  public static float getVirtualWidth(){
    if (Gdx.app.getType() != Application.ApplicationType.Android) {
      return 1920;
    } else {
      return 1080;
    }
  }

  /**
   * Get the virtual height that the screen represents. This is dependent on the platform.
   * @return Virtual height that the screen represents.
   */
  public static float getVirtualHeight(){
    if (Gdx.app.getType() != Application.ApplicationType.Android) {
      return 1080;
    } else {
      return 1920;
    }
  }

}
