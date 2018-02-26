package edu.gvsu.cis.spacejourney.entity.projectile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool.Poolable;
import edu.gvsu.cis.spacejourney.entity.Entity;

/**
 * Created by Kyle Flynn on 2/3/2018.
 */
public abstract class Projectile extends Entity implements Poolable {

  private boolean isAlive;

  /**
   * Default constructor that creates a projectile instance.
   * @param stage current stage that the projectile will live in.
   * @param textureRegion texture to give the projectile.
   */
  public Projectile(Stage stage, TextureRegion textureRegion) {
    super(stage, textureRegion);
    this.isAlive = false;
    this.setVisible(true);
  }

  /**
   * Method that spawns a projectile.
   * @param world current world that the projectile will live in.
   * @param x normalized x coordinate.
   * @param y normalized y coordinate.
   */
  public void spawn(World world, float x, float y) {
    setPosition(x, y);
    createBody(world);
    this.isAlive = true;
    this.setVisible(true);
  }

  /**
   * Method that checks if the laser is still alive.
   * @return true if alive, false otherwise.
   */
  public boolean isAlive() {
    return isAlive;
  }

  /**
   * Method that sets whether or not a projectile is alive.
   * @param alive true or false.
   */
  public void setAlive(boolean alive) {
    this.isAlive = alive;
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    if (this.outOfBounds()) {
      this.isAlive = false;
    }

  }

  @Override
  public void reset() {
    this.setVisible(false);
    this.setPosition(0.0f, 0.0f);
    this.isAlive = false;
  }

}
