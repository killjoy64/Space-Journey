package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import edu.gvsu.cis.spacejourney.entity.Graveyard;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.entity.collectible.Collectible;
import edu.gvsu.cis.spacejourney.entity.enemy.Enemy;
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship;
import edu.gvsu.cis.spacejourney.entity.projectile.Laser;
import edu.gvsu.cis.spacejourney.managers.GameDataManager;

/**
 * Class that handles all collisions in a given world.
 */
public class GameContactListener implements ContactListener {

  private GameDataManager gameData;

  /**
   * Default constructor that initializes the singleton instance of the
   * GameDataManager class.
   */
  public GameContactListener() {
    this.gameData = GameDataManager.getInstance();
  }

  /**
   * Private helper method that checks for collisions between two entities.
   * @param contact Please refer to {@link com.badlogic.gdx.physics.box2d.Contact}.
   * @param entityA The first entity that is a part of the collision.
   * @param entityB The second entity that is a part of the collision.
   */
  private void check(Contact contact, Object entityA, Object entityB) {

    int currentLives = gameData.getLives();

    Fixture a = contact.getFixtureA();

    if (entityB instanceof EvilSpaceship && entityA instanceof PlayerSpaceship) {
      PlayerSpaceship player = (PlayerSpaceship) entityA;
      gameData.setLives(currentLives - 1);
      player.takeDamage();
    }

    if (entityA instanceof Collectible && entityB instanceof PlayerSpaceship) {
      Collectible c = (Collectible) entityA;
      c.collect();
    }

    if (entityA instanceof Laser && entityB instanceof Enemy) {
      Enemy e = (Enemy) entityB;
      Laser l = (Laser) entityA;
      e.takeDamage();
      l.reset();
      Graveyard.BODIES.add(a.getBody());
    }
  }

  /**
   * Method that checks contact between two entities.
   * @param contact The box2D contact instance.
   */
  @Override
  public void beginContact(Contact contact) {
    Fixture a = contact.getFixtureA();
    Fixture b = contact.getFixtureB();

    Object entityA = a.getBody().getUserData();
    Object entityB = b.getBody().getUserData();

    check(contact, entityA, entityB);
    check(contact, entityB, entityA);
  }

  /**
   * Currently not used method in Space Journey.
   * @param contact Please refer to {@link com.badlogic.gdx.physics.box2d.Contact}.
   */
  @Override
  public void endContact(Contact contact) {

  }

  /**
   * Currently not used method in Space Journey.
   * @param contact Please refer to {@link com.badlogic.gdx.physics.box2d.Contact}.
   * @param oldManifold Please refer to {@link com.badlogic.gdx.physics.box2d.Manifold}.
   */
  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  /**
   * Currently not used method in Space Journey.
   * @param contact Please refer to {@link com.badlogic.gdx.physics.box2d.Contact}.
   * @param impulse Please refer to {@link com.badlogic.gdx.physics.box2d.ContactImpulse}.
   */
  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }
}
