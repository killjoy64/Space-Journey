package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entities.Direction;
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity;
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager;

/**
 * Created by Kyle Flynn on 1/24/2018.
 */
public class PlayerInputListener implements InputProcessor {

    private SpaceshipEntity player;
    private ActiveProjectileManager projManager;


    private boolean[] keys;

    public PlayerInputListener(SpaceshipEntity player) {
        this.player = player;
        this.projManager = ActiveProjectileManager.getInstance();
        this.keys = new boolean[255];
    }

    @Override
    public boolean keyDown(int keycode) {

        float moveSpeed = 300.0f / Constants.PX_PER_M;

        if (keycode == Input.Keys.W) {
            this.player.setVelocityY(moveSpeed);
        }
        if (keycode == Input.Keys.A) {
            this.player.setVelocityX(-moveSpeed);
        }
        if (keycode == Input.Keys.S) {
            this.player.setVelocityY(-moveSpeed);
        }
        if (keycode == Input.Keys.D) {
            this.player.setVelocityX(moveSpeed);
        }

        if (keycode == Input.Keys.SPACE) {
            float x = this.player.getX() + (this.player.getWidth() / 2);
            float y = this.player.getY() + (this.player.getHeight());
            this.projManager.spawnLaser(x, y);
        }

        this.keys[keycode] = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        float moveSpeed = 0.0f;

        if (keycode == Input.Keys.W) {
            this.player.setVelocityY(moveSpeed);
        }
        if (keycode == Input.Keys.A) {
            this.player.setVelocityX(-moveSpeed);
        }
        if (keycode == Input.Keys.S) {
            this.player.setVelocityY(-moveSpeed);
        }
        if (keycode == Input.Keys.D) {
            this.player.setVelocityX(moveSpeed);
        }

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

    }

}
