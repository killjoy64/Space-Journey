package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.EntityDirection;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager;

/**
 * Created by Kyle Flynn on 1/24/2018.
 */
public class PlayerInputListener implements InputProcessor {

    private final float spawnFrequency = 0.225f;

    private PlayerSpaceship player;
    private ActiveProjectileManager projManager;
    private float time;

    private boolean[] keys;

    public PlayerInputListener(PlayerSpaceship player) {
        this.player = player;
        this.projManager = ActiveProjectileManager.getInstance();
        this.time = 0.0f;
        this.keys = new boolean[255];
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            float x = (this.player.getX() + (this.player.getWidth() / 2)) / Constants.PX_PER_M;
            float y = (this.player.getY() + (this.player.getHeight())) / Constants.PX_PER_M;
            this.projManager.spawnLaser(x, y);
            this.time = 0.0f;
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

    public void poll(float delta) {

        boolean moved = false;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(EntityDirection.RIGHT);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(EntityDirection.LEFT);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.move(EntityDirection.UP);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.move(EntityDirection.DOWN);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            time += delta;
            if (time >= spawnFrequency) {
                float x = (this.player.getX() + (this.player.getWidth() / 2)) / Constants.PX_PER_M;
                float y = (this.player.getY() + (this.player.getHeight())) / Constants.PX_PER_M;
                this.projManager.spawnLaser(x, y);
                this.time = 0.0f;
            }
        }

        if (!moved) {
            player.stopMoving();
        }
    }
}
