package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Kyle Flynn on 2/3/2018.
 */
public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        contact.setEnabled(false);
    }

    @Override
    public void endContact(Contact contact) {
        contact.setEnabled(false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
