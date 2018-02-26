package edu.gvsu.cis.spacejourney.managers;

/**
 * Singleton class that stores and manages
 * universal game data.
 */
public final class GameDataManager {

  /**
   * Maximum number of lives possible throughout the game.
   */
  public static final int MAX_LIVES = 4;

  private static GameDataManager instance;

  private int lives;
  private int levelNumber;
  private int score;

  /**
   * Default constructor that initializes all game defaults
   * ONCE every instance of the game.
   */
  private GameDataManager() {
    this.lives = 3;
    this.levelNumber = 1;
    this.score = 0;
  }

  /**
   * Method that resets all of the game data to it's original state.
   */
  public void reset() {
    this.lives = 3;
    this.levelNumber = 1;
    this.score = 0;
  }

  /**
   * Gets the current game data singleton being used.
   * @return {@link edu.gvsu.cis.spacejourney.managers.GameDataManager} singleton.
   */
  public static GameDataManager getInstance() {
    if (instance == null) {
      instance = new GameDataManager();
    }
    return instance;
  }

  /**
   * Get the number of lives the player has left.
   * @return int as the number of lives the player has left.
   */
  public int getLives() {
    return lives;
  }

  /**
   * Get the current level number that the game
   * is on.
   * @return int as the number associated to the current level.
   */
  public int getLevelNumber() {
    return levelNumber;
  }

  /**
   * Get the current score of the player.
   * @return int as the number associated with the player's score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Set the number of lives the player currently has.
   * @param lives to give to the player.
   */
  public void setLives(int lives) {
    this.lives = lives;
  }

  /**
   * Set the level that the game is currently on.
   * @param levelNumber to set the game to.
   */
  public void setLevelNumber(int levelNumber) {
    this.levelNumber = levelNumber;
  }

  /**
   * Set the current score of the player.
   * @param score to set the score to.
   */
  public void setScore(int score) {
    this.score = score;
  }
}
