package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashSet;
import java.util.Set;

/**
 * Where the bodies are piled up and queued for removal
 * from this cruel, cruel world.
 */
public class Graveyard {

  /**
   * The generic bodies that need to be removed. These are sets
   * because no body should be removed twice.
   */
  public static final Set<Body> bodies = new HashSet<>();

  /**
   * The specific actors of the bodies that need to be removed. These
   * are sets, because no actor should be removed twice.
   */

  public static final Set<Actor> actors = new HashSet<>();

}
