package edu.gvsu.cis.spacejourney.managers;

public class GameDataManager {

    private GameDataManager instance;

    private int lives;

    public GameDataManager() {
        this.lives = 3;
    }

    public GameDataManager getInstance() {
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
