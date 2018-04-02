package edu.gvsu.cis.spacejourney.level;

import edu.gvsu.cis.spacejourney.level.one.LevelOne;
import edu.gvsu.cis.spacejourney.level.two.LevelTwo;

/**
 * Enum class that holds all level data, including
 * their class and ID.
 */
public enum Levels {
  SPACE(1, new LevelOne()),
  EARTH(2, new LevelTwo()),
  BOSS_SPACE(3, new LevelOne());

  private int id;
  private Level level;

  /**
   * Default constructor for a Level enum.
   * @param id number to assign as the level.
   * @param level class that the level comes from.
   */
  Levels(int id, Level level) {
    this.id = id;
    this.level = level;
  }

  /**
   * Gets the id of the current level enum.
   * @return id as an integer.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the level class of the current level enum.
   * @return {@link edu.gvsu.cis.spacejourney.level.Level} object.
   */
  public Level getLevel() {
    return level;
  }

  /**
   * Translation method that returns a {@link edu.gvsu.cis.spacejourney.level.Level} object
   * from a given ID.
   * @param id number as the level ID.
   * @return {@link edu.gvsu.cis.spacejourney.level.Level} object.
   */
  public static Levels getFromId(int id) {
    switch (id) {
      case 1:
        return SPACE;
      case 2:
        return EARTH;
      case 3:
        return BOSS_SPACE;
      default:
        return SPACE;
    }
  }
}
