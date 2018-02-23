package edu.gvsu.cis.spacejourney.level.choreography.events;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.Entity;
import edu.gvsu.cis.spacejourney.level.choreography.ChoreographEvent;

import java.util.Random;

/**
 * Basic class that handles spawning an entity at a random location on the screen.
 * This is meant to be a ChoreographEvent used with the LevelChoreographer.
 */
public class EntitySpawnEvent extends ChoreographEvent {

  private Entity entity;

  /**
   * Default constructor that takes an entity
   * that will be spawned randomly.
   * @param entity {@link edu.gvsu.cis.spacejourney.entity.Entity} object.
   */
  public EntitySpawnEvent(Entity entity) {
    this.entity = entity;
  }

  /**
   * Spawns an entity randomly when the event is called.
   * @param stage for the current stage of the game.
   * @param world for the current world of the game.
   */
  @Override
  public void onEvent(Stage stage, World world) {
    Random random = new Random();

    float r = random.nextFloat();
    float x = (r * stage.getViewport().getWorldWidth() - (50f / Constants.PX_PER_M));
    float y = stage.getViewport().getWorldHeight();

    entity.setPosition(x, y);
    entity.createBody(world);
    stage.addActor(entity);
  }

  /**
   * Gets the entity that is being spawned.
   * @return a {@link edu.gvsu.cis.spacejourney.entity.Entity} object.
   */
  public Entity getEntity() {
    return entity;
  }
}
