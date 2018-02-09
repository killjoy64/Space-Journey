package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Interface that signals the class can interact
 * with other entities in a Box2D world.
 */
public interface Collidable {

  /**
   * Creates a body using the given world parameter.
   * @param world current Box2D world that is being
   *        rendered.
   */
  void createBody(World world);

}
