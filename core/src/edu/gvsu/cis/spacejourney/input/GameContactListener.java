package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.physics.box2d.*;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship;
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
            gameData.setLives(currentLives-1);
        } else if (entityA instanceof EvilSpaceship && entityB instanceof PlayerSpaceship) {
            PlayerSpaceship player = (PlayerSpaceship) entityB;
            player.takeDamage();
            gameData.setLives(currentLives-1);
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
