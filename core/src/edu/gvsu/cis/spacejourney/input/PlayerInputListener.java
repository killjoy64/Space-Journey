package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entities.Direction;
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager;

/**
 * Created by Kyle Flynn on 1/24/2018.
 */
public class PlayerInputListener implements InputProcessor {

    private PlayerSpaceship player;
    private ActiveProjectileManager projManager;


    private boolean[] keys;

    public PlayerInputListener(PlayerSpaceship player) {
        this.player = player;
        this.projManager = ActiveProjectileManager.getInstance();
        this.keys = new boolean[255];
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            float x = (this.player.getX() + (this.player.getWidth() / 2)) / Constants.PX_PER_M;
            float y = (this.player.getY() + (this.player.getHeight())) / Constants.PX_PER_M;
            this.projManager.spawnLaser(x, y); // TODO - Make sure is till working.
        }

        this.keys[keycode] = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.keys[keycode] = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void poll() {
        if (keys[Input.Keys.D]) {
            player.getBody().applyLinearImpulse(new Vector2(0.1f, 0.0f), player.getBody().getWorldCenter(), true);
        } else if (keys[Input.Keys.A]) {
            player.getBody().applyLinearImpulse(new Vector2(-0.1f, 0.0f), player.getBody().getWorldCenter(), true);
        } else if (keys[Input.Keys.W]) {
            player.getBody().applyLinearImpulse(new Vector2(0.0f, 0.1f), player.getBody().getWorldCenter(), true);
        } else if (keys[Input.Keys.S]) {
            player.getBody().applyLinearImpulse(new Vector2(0.0f, -0.1f), player.getBody().getWorldCenter(), true);
        }
    }

}
