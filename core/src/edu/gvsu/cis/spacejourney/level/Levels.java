package edu.gvsu.cis.spacejourney.level;

import edu.gvsu.cis.spacejourney.level.one.LevelOne;

public enum Levels {
    SPACE (1, new LevelOne()),
    EARTH (2, new LevelOne()),
    BOSS_SPACE (3, new LevelOne());

    private int id;
    private Level level;

    Levels(int id, Level level) {
        this.id = id;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public Level getLevel() {
        return level;
    }

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
