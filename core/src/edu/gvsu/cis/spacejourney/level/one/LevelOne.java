package edu.gvsu.cis.spacejourney.level.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.entity.collectible.Collectible;
import edu.gvsu.cis.spacejourney.entity.collectible.TestCollectible;
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship;
import edu.gvsu.cis.spacejourney.entity.movement.LinearMovement;
import edu.gvsu.cis.spacejourney.input.PlayerInputListener;
import edu.gvsu.cis.spacejourney.level.Level;
import edu.gvsu.cis.spacejourney.level.choreography.LevelChoreographer;
import edu.gvsu.cis.spacejourney.level.choreography.events.EntitySpawnEvent;
import edu.gvsu.cis.spacejourney.screens.backgrounds.ParallaxBackground;
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay;
import edu.gvsu.cis.spacejourney.util.ZIndex;

public class LevelOne extends Level {

  private static final int LEVEL_TIME = 200;

  private ParallaxBackground background;
  private PlayerSpaceship player;
  private PlayerInputListener inputListener;
  private Collectible testCollectible;

  private LevelChoreographer choreographer;
  private DefaultOverlay defaultHud;

  /**
   * Default constructor for the first level.
   */
  public LevelOne() {
    setMusic(SpaceJourney.Companion.getAssetManager().get(
        "Space Background Music.mp3", Music.class));
  }

  /**
   * Method that initializes logic specific to the first level.
   * @param stage for the current stage of the game.
   * @param world for the current world of the game.
   */
  @Override
  public void init(Stage stage, World world) {
    super.init(stage, world);

    background = new ParallaxBackground();
    background.setZIndex(ZIndex.BACKGROUND);
    stage.addActor(background);

    player = new PlayerSpaceship(stage);
    player.setPosition(1.5f, 0.0f);
    player.setWidth(50.0f);
    player.setHeight(50.0f);
    player.createBody(world);
    stage.addActor(player);
    setPlayer(player);

    defaultHud = new DefaultOverlay();
    setHud(defaultHud);

    inputListener = new PlayerInputListener(player);
    Gdx.input.setInputProcessor(inputListener);

    testCollectible = new TestCollectible(stage);
    testCollectible.createBody(world);
    stage.addActor(testCollectible);

    choreographer = new LevelChoreographer(stage, world);

    for (int i = 0; i < LEVEL_TIME; i++) {
      EvilSpaceship basicEnemy = new EvilSpaceship(stage);
      basicEnemy.setWidth(35.0f);
      basicEnemy.setHeight(35.0f);
      basicEnemy.setMovementPattern(new LinearMovement(new Vector2(0f, -25f)));
      choreographer.schedule(1.0f + i, new EntitySpawnEvent(basicEnemy));
    }

  }

  /**
   * Method that updates the logic each time the render() method is called.
   * @param delta float given during a screen's render() method.
   */
  @Override
  public void update(float delta) {
    inputListener.poll(delta);
    choreographer.update(delta);
    defaultHud.poll();

    if (choreographer.isEmpty()) {
      EntitySpawnEvent event = (EntitySpawnEvent) choreographer.getLastEvent().getEvent();
      if (event.getEntity().belowScreen()) {
        // TODO - Implement level completed... or something...
    	  System.out.println("FINISHED");
      }
    }

  }

  /**
   * Disposes of any disposable objects, such as music, textures, or entities.
   */
  @Override
  public void dispose() {
    player.dispose();
    testCollectible.dispose();
    getMusic().stop();
    getMusic().dispose();
  }
}
