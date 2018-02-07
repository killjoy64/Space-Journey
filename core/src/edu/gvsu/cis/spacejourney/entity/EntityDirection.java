package edu.gvsu.cis.spacejourney.entity;

public enum EntityDirection {

  UP(0, 0, 1),
  DOWN(1, 0, -1),
  LEFT(2, -1, 0),
  RIGHT(3, 1, 0),
  IDLE(4, 0, 0);

  private int id;
  private int valueX;
  private int valueY;

  EntityDirection(int id, int x, int y) {
    this.id = id;
    this.valueX = x;
    this.valueY = y;
  }

  public int getId() {
    return this.id;
  }

  public int getX() {
    return valueX;
  }

  public int getY() {
    return valueY;
  }
}
