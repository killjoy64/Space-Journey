package edu.gvsu.cis.spacejourney.entity;

/**
 * Enumeration class meant to designate the possible directions for
 * the player to travel.
 */
public enum EntityDirection {

  UP(0, 0, 1),
  DOWN(1, 0, -1),
  LEFT(2, -1, 0),
  RIGHT(3, 1, 0),
  IDLE(4, 0, 0);

  private int id;
  private int valueX;
  private int valueY;

  /**
   * Default constructor.
   * @param id Unique ID of the direction.
   * @param x Value to multiply by to go this direction.
   * @param y Value to multiply by to go this direction.
   */
  EntityDirection(int id, int x, int y) {
    this.id = id;
    this.valueX = x;
    this.valueY = y;
  }

  /**
   * Getter that returns the unique ID of the direction.
   * @return The unique ID as an integer.
   */
  public int getId() {
    return this.id;
  }

  /**
   * Method that returns the x value of the enum.
   * @return Value to multiply by to go this direction.
   */
  public int getX() {
    return valueX;
  }

  /**
   * Method that returns the y value of the enum.
   * @return Value to multiply by to go this direction.
   */
  public int getY() {
    return valueY;
  }
}
