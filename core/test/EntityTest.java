import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.entity.Entity;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.entity.enemy.Enemy;
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityTest extends GameTest {

  private Camera camera;
  private FitViewport viewport;
  private Stage stage;
  private World world;

  /**
   * Test to make sure an entity can be created and added to a stage,
   * as well as a world. The first test can only execute once
   * Gdx is ready.
   */
  @Test
  public void testPlayerCreate() {
    Gdx.app.postRunnable(() -> {
      camera = new OrthographicCamera();
      viewport = new FitViewport(
          Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
      stage = new Stage(viewport);
      world = new World(new Vector2(0.0f, 0.0f), false);
      Entity e = new PlayerSpaceship(stage);
      stage.addActor(e);
      int actors = stage.getActors().size;
      int bodies = world.getBodyCount();
      assertEquals("Stage actor count increases.", actors, 1);

      e.createBody(world);
      assertEquals("World body count increases.", world.getBodyCount(), bodies + 1);

      world.destroyBody(e.getBody());
      assertEquals("World deletes body successfully.", world.getBodyCount(), bodies);
    });
  }

  /**
   * Test that should throw an error. First, the stage
   * is not successfully loaded, and second the
   * asset should not be loaded yet.
   */
  @Test(expected = GdxRuntimeException.class)
  public void testEnemyCreateAssetNotLoaded() {
    Enemy e = new EvilSpaceship(stage);
  }

  @Test
  public void testEnemyCreate() {
    Gdx.app.postRunnable(() -> {
      getAssets().load("spaceship3.png", Texture.class);
      getAssets().finishLoading();
      TextureRegion region = new TextureRegion(getAssets().get("spaceship3.png", Texture.class));
      Enemy e = new EvilSpaceship(stage, region);
      stage.addActor(e);
      int actors = stage.getActors().size;
      int bodies = world.getBodyCount();
      assertEquals("Stage actor count increases.", actors, 1);

      e.createBody(world);
      assertEquals("World body count increases.", world.getBodyCount(), bodies + 1);

      world.destroyBody(e.getBody());
      assertEquals("World deletes body successfully.", world.getBodyCount(), bodies);
    });
  }

}
