package edu.gvsu.cis.spacejourney.entity;

public enum EntityDirection {

    UP    (0, 0, 1),
    DOWN (1, 0, -1),
    LEFT (2, -1, 0),
    RIGHT (3, 1, 0),
    IDLE  (4, 0, 0);

    private int id;
    private int x;
    private int y;

    EntityDirection(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getID() {
        return this.id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
