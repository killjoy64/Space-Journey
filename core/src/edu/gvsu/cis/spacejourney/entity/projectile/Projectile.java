package edu.gvsu.cis.spacejourney.entity.projectile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool.Poolable;
import edu.gvsu.cis.spacejourney.entity.Entity;
import edu.gvsu.cis.spacejourney.entity.Graveyard;

/**
 * Created by Kyle Flynn on 2/3/2018.
 */
public abstract class Projectile extends Entity implements Poolable {

  private boolean isAlive;

  public Projectile(Stage stage, TextureRegion textureRegion) {
    super(stage, textureRegion);
    this.isAlive = false;
  }

  public void spawn(World world, float x, float y) {
    setPosition(x, y);
    createBody(world);
    this.isAlive = true;
  }

  public boolean isAlive() {
    return isAlive;
  }

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
    this.setPosition(0.0f, 0.0f);
    this.isAlive = false;
  }

}
