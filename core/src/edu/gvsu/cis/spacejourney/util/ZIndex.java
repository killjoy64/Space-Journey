package edu.gvsu.cis.spacejourney.util;

/**
 * Static class that holds vital information to be
 * used across the project. This is specifically used
 * for Z-indexing across entities and backgrounds.
 */
public final class ZIndex {

  /**
   * This class should not be instantiated.
   */
  private ZIndex() { }
	
  /**
   * Z-index value for the 3rd parallax layer.
   */
  public static final int PARALLAX_BACKGROUND_LAYER3 = 0;

  /**
   * Z-index value for the 2nd parallax layer.
   */
  public static final int PARALLAX_BACKGROUND_LAYER2 = 0;

  /**
   * Z-index value for the 1st parallax layer.
   */
  public static final int PARALLAX_BACKGROUND_LAYER1 = 1;

  /**
   * Z-index value for the background layer.
   */
  public static final int BACKGROUND = 2;

  /**
   * Z-index value for the entity layer.
   */
  public static final int ENTITY = 5;

  /**
   * Z-index value for the player.
   */
  public static final int PLAYER = 50;

  /**
   * Z-index value for projectiles.
   */
  public static final int PROJECTILES = 51;

  /**
   * Z-index value for enemies.
   */
  public static final int ENEMY = 5;

  /**
   * Z-index value for GUI.
   */
  public static final int GUI = 100;

  /**
   * Z-index values for collectibles.
   */
  public static final int COLLECTIBLE = 49;

}
