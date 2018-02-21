package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.EntityDirection;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager;

/**
 * Class that handles all the given input for the player.
 */
public class PlayerInputListener implements InputProcessor {

  private static final float spawnFrequency = 0.135f;

  private PlayerSpaceship player;
  private ActiveProjectileManager projManager;
  private float time;

  /**
   * Input listener for the player.
   * @param player current player being used in the game.
   */
  public PlayerInputListener(PlayerSpaceship player) {
    this.player = player;
    this.projManager = ActiveProjectileManager.getInstance();
    this.time = 0.0f;
  }

  @Override
  public boolean keyDown(int keycode) {
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
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

    if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS ) {

      if (screenX < Gdx.graphics.getWidth() / 2.0f) {
        player.move(EntityDirection.LEFT);
      } else {
        player.move(EntityDirection.RIGHT);
      }
    }

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

  /**
   * Polls to update the game based upon input events.
   * @param delta delta time used throughout the game.
   */
  public void poll(float delta) {
    
    if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
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

          y = y - (32 / Constants.PX_PER_M);

          this.projManager.spawnLaser(x + (16 / Constants.PX_PER_M), y);
          this.projManager.spawnLaser(x - (16 / Constants.PX_PER_M), y);

          this.time = 0.0f;
        }
      }

      if (!moved) {
        player.stopMoving();
      }
    } else {
      time += delta;
      if (time >= spawnFrequency) {
        float x = (this.player.getX() + (this.player.getWidth() / 2)) / Constants.PX_PER_M;
        float y = (this.player.getY() + (this.player.getHeight())) / Constants.PX_PER_M;

        y = y - (32 / Constants.PX_PER_M);

        this.projManager.spawnLaser(x + (16 / Constants.PX_PER_M), y);
        this.projManager.spawnLaser(x - (16 / Constants.PX_PER_M), y);
        this.time = 0.0f;
      }
    }
  }
}
