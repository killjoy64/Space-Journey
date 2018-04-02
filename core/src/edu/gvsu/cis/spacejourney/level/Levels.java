package edu.gvsu.cis.spacejourney.level;

import edu.gvsu.cis.spacejourney.level.space.SpaceLevel;
import edu.gvsu.cis.spacejourney.level.earth.EarthLevel;

/**
 * Enum class that holds all level data, including
 * their class and ID.
 */
public enum Levels {
  SPACE(1),
  EARTH(2),
  BOSS_SPACE(3);

  private int id;

  /**
   * Default constructor for a Level enum.
   * @param id number to assign as the level.
   */
  Levels(int id) {
    this.id = id;
  }

  /**
   * Gets the id of the current level enum.
   * @return id as an integer.
   */
  public int getId() {
    return id;
  }

  /**
   * Translation method that returns a {@link edu.gvsu.cis.spacejourney.level.Level} object
   * from a given ID.
   * @param id number as the level ID.
   * @return {@link edu.gvsu.cis.spacejourney.level.Level} object.
   */
  public static Level getFromId(int id) {
    switch (id) {
      case 1:
        return new EarthLevel();
      case 2:
        return new SpaceLevel();
      case 3:
        return new SpaceLevel();
      default:
        return new SpaceLevel();
    }
  }
}
