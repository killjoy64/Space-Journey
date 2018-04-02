package edu.gvsu.cis.spacejourney;

/**
 * Static class that holds vital information to be
 * used across the project. This is specifically
 * used for String resources.
 */
public final class Strings {

  /**
   * This class should not be instantiated.
   */
  private Strings() { }
	
  /**
   * Title of the game.
   */
  public static final String GAME_TITLE = "Space Journey";

  /**
   * String text to start the game.
   */
  public static final String MENU_OPTION_1 = "%sStart  ";

  /**
   * String text to exit the game.
   */
  public static final String MENU_OPTION_2 = "%sExit  ";

  /**
   * String text to select a level.
   */
  public static final String LEVEL_SELECT_TITLE = "Select A Level";

  /**
   * String text of the first level.
   */
  public static final String LEVEL_ONE = "%sEarth  ";

  /**
   * String text of the second level.
   */
  public static final String LEVEL_TWO = "%sSpace  ";

  /**
   * String text of the third level.
   */
  public static final String LEVEL_THREE = "%sSpace Boss  ";

  /**
   * String text used to represent the current score on the heads up display.
   */
  public static final String HUD_SCORE = "SCORE %06d";

  /**
   * String text used to represent level end text.
   */
  public static final String LEVEL_END = "LEVEL END";

}
