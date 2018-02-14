package edu.gvsu.cis.spacejourney.level.one;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.entity.collectible.Collectible;
import edu.gvsu.cis.spacejourney.entity.collectible.TestCollectible;
import edu.gvsu.cis.spacejourney.input.PlayerInputListener;
import edu.gvsu.cis.spacejourney.level.Level;
import edu.gvsu.cis.spacejourney.level.choreography.EnemySpawnEvent;
import edu.gvsu.cis.spacejourney.level.choreography.LevelChoreographer;
import edu.gvsu.cis.spacejourney.screens.backgrounds.ParallaxBackground;
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay;
import edu.gvsu.cis.spacejourney.util.ZIndex;

public class LevelOne extends Level {

  private ParallaxBackground background;
  private PlayerSpaceship player;
  private PlayerInputListener inputListener;
  private Collectible testCollectible;

  private LevelChoreographer choreographer;
  private DefaultOverlay defaultHud;

  public LevelOne() {
    setMusic(SpaceJourney.Companion.getAssetManager().get("Space Background Music.mp3", Music.class));
  }

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

    for (int i = 0; i < 200; i++) {
      choreographer.schedule(1.0f + i, new EnemySpawnEvent());
    }
  }

  @Override
  public void update(float delta) {
    inputListener.poll(delta);
    choreographer.update(delta);
    defaultHud.poll();
  }

  @Override
  public void dispose() {
    player.dispose();
    testCollectible.dispose();
  }
}
