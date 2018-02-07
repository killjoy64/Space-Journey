package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

/**
 * Where the bodies are piled up and queued for removal.
 */
public class Graveyard {

  /**
   * The generic bodies that need to be removed.
   */
  public static ArrayList<Body> bodies = new ArrayList<Body>();

  /**
   * The specific actors of the bodies that need to be removed.
   */
  public static ArrayList<Actor> actors = new ArrayList<Actor>();

}
