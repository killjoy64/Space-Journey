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
 * Created by Kyle Flynn on 2/3/2018.
 */
public class GameContactListener implements ContactListener {

  private GameDataManager gameData;

  public GameContactListener() {
    this.gameData = GameDataManager.getInstance();
  }

  @Override
  public void beginContact(Contact contact) {
    Fixture a = contact.getFixtureA();
    Fixture b = contact.getFixtureB();
    Object entityA = a.getBody().getUserData();
    Object entityB = b.getBody().getUserData();
    int currentLives = gameData.getLives();

    if (entityB instanceof EvilSpaceship && entityA instanceof PlayerSpaceship) {
      PlayerSpaceship player = (PlayerSpaceship) entityA;
      player.takeDamage();
      gameData.setLives(currentLives - 1);
    } else if (entityA instanceof EvilSpaceship && entityB instanceof PlayerSpaceship) {
      PlayerSpaceship player = (PlayerSpaceship) entityB;
      player.takeDamage();
      gameData.setLives(currentLives - 1);
    }

    if (entityA instanceof Collectible && entityB instanceof PlayerSpaceship) {
      Collectible c = (Collectible) entityA;
      c.collect();
    } else if (entityA instanceof PlayerSpaceship && entityB instanceof Collectible) {
      Collectible c = (Collectible) entityB;
      c.collect();
    }

    if (entityA instanceof Laser && entityB instanceof Enemy) {
      Enemy e = (Enemy) entityB;
      Laser l = (Laser) entityA;
      e.takeDamage();
      l.reset();
      Graveyard.bodies.add(a.getBody());
    } else if (entityB instanceof Laser && entityA instanceof Enemy) {
      Enemy e = (Enemy) entityA;
      Laser l = (Laser) entityB;
      e.takeDamage();
      l.reset();
      Graveyard.bodies.add(b.getBody());
    }

  }

  @Override
  public void endContact(Contact contact) {

  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }
}
