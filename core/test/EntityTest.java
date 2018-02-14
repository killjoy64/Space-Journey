import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

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
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.entity.enemy.Enemy;
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(GameTest.class)
public class EntityTest {

  @Mock
  private static Camera camera;

  @Mock
  private static FitViewport viewport;

  @Mock
  private static Stage stage;

  @Mock
  private static World world;

  @BeforeClass
  public static void setup() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
    stage = mock(Stage.class);
    world = new World(new Vector2(0.0f, 0.0f), false);
  }

  /**
   * Test that should fail because the player's asset image
   * was never properly loaded.
   */
  @Test(expected = GdxRuntimeException.class)
  public void testPlayerCreateAssetNotLoaded() {
    PlayerSpaceship player = new PlayerSpaceship(stage);
  }

  /**
   * Test that should fail because the enemy's asset image
   * was never properly loaded.
   */
  @Test(expected = GdxRuntimeException.class)
  public void testEnemyCreateAssetNotLoaded() {
    Enemy enemy = new EvilSpaceship(stage);
  }

}
