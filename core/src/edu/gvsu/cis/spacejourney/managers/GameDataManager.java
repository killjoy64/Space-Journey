package edu.gvsu.cis.spacejourney.managers;

public class GameDataManager {

  public static int MAX_LIVES = 4;

  private static GameDataManager instance;

  private int lives;

  public GameDataManager() {
    this.lives = 3;
  }

  public static GameDataManager getInstance() {
    if (instance == null) {
      instance = new GameDataManager();
    }
    return instance;
  }

  public int getLives() {
    return lives;
  }

  public void setLives(int lives) {
    this.lives = lives;
  }
}
