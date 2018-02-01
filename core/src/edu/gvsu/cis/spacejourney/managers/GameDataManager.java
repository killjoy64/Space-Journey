package edu.gvsu.cis.spacejourney.managers;

public class GameDataManager {

   private GameDataManager instance;

    public GameDataManager getInstance() {
        if (instance == null) {
            instance = new GameDataManager();
        }
        return instance;
    }
}
