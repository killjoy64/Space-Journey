package edu.gvsu.cis.spacejourney.entities;

public enum Direction {
    LEFT    (1),
    RIGHT   (2),
    UP      (3),
    DOWN    (4);

    private int code;

    Direction(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
