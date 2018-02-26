package edu.gvsu.cis.spacejourney.managers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.projectile.Laser;

/**
 * Singleton class that stores and manages information of
 * active projectiles in a pool.
 */
public class ActiveProjectileManager implements Disposable {

  private static ActiveProjectileManager instance;

  private Stage stage;
  private World world;

  private Array<Laser> activeProjectiles;
  private Pool<Laser> projectilePool;

  /**
   * Gets the current projectile manager singleton being used.
   * @return {@link edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager} singleton.
   */
  public static ActiveProjectileManager getInstance() {
    if (instance == null) {
      instance = new ActiveProjectileManager();
    }
    return instance;
  }

  /**
   * MUST be called once after the {@link com.badlogic.gdx.scenes.scene2d.Stage}
   * is setup.
   */
  public void init() {
    this.activeProjectiles = new Array<Laser>();
    this.projectilePool = new Pool<Laser>() {
      @Override
      protected Laser newObject() {
        return new Laser(stage);
      }
    };
  }

  /**
   * Spawns a laser at the desired x and y location. This includes
   * a texture and Box2d body object.
   * @param x the x-coordinate of where to spawn the laser.
   * @param y the y-coordinate of where to spawn the laser.
   */
  public void spawnLaser(float x, float y) {
    Laser newLaser = this.projectilePool.obtain();
    newLaser.setWidth(12.5f);
    newLaser.setHeight(12.5f);
    newLaser.spawn(world, x - ((12.5f / 2) / Constants.PX_PER_M), y);
    activeProjectiles.add(newLaser);
    stage.addActor(newLaser);
  }

  /**
   * Polls for updates to any pooled projectile. This controls
   * projectiles that need to be removed from the stage, or
   * added to the {@link edu.gvsu.cis.spacejourney.entity.Graveyard}.
   */
  public void poll() {
    for (int i = 0; i < activeProjectiles.size; i++) {
      Laser p = activeProjectiles.get(i);
      if (!p.isAlive()) {
        p.remove();
        activeProjectiles.removeIndex(i);
        projectilePool.free(p);
      }
    }
  }

  /**
   * The stage MUST be set in order for the projectile manager
   * to work properly.
   * @param stage current stage being used on screen.
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * The world MUST be set in order for the projectile manager to
   * work properly.
   * @param world current world being used on screen.
   */
  public void setWorld(World world) {
    this.world = world;
  }

  /**
   * Method that disposes of the projectile pool, since it does
   * implement the Disposable interface.
   */
  @Override
  public void dispose() {
    this.projectilePool.clear();
  }

}
