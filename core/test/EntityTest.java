import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.entity.EntityDirection;
import edu.gvsu.cis.spacejourney.entity.Graveyard;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import edu.gvsu.cis.spacejourney.entity.enemy.Enemy;
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship;
import edu.gvsu.cis.spacejourney.managers.GameDataManager;
import org.junit.*;
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

  /**
   * Setup method that is required to run before any of our
   * code can be tested. We also need to mock LIBGdx.Scene2d's
   * Stage class. Assets are also loaded here.
   */
  @BeforeClass
  public static void setup() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
    stage = mock(Stage.class);
    world = new World(new Vector2(0.0f, 0.0f), false);

    when(stage.getViewport()).thenReturn(viewport);

    SpaceJourney.Companion.getAssetManager().load("spaceship2.png", Texture.class);
    SpaceJourney.Companion.getAssetManager().load("spaceship3.png", Texture.class);
    SpaceJourney.Companion.getAssetManager().load("take_damage.wav", Sound.class);
    SpaceJourney.Companion.getAssetManager().finishLoading();
  }

  @Test
  public void testPlayerPhysicsBody() {
    PlayerSpaceship player = new PlayerSpaceship(stage);
    player.createBody(world);
    Body body = player.getBody();

    // Player's body should not be null once created.
    assertNotNull(player.getBody());

    world.destroyBody(player.getBody());
    player.createBody(world);

    // Player's body should not change even if it is redefined.
    assertEquals(body, player.getBody());

    world.destroyBody(player.getBody());
    player.dispose();
  }

  @Test
  public void testPlayerLogic() {
    PlayerSpaceship player = new PlayerSpaceship(stage);
    player.createBody(world);

    // By default, the player is not dead, and spawns at 0,0.
    assertFalse(player.isDead());
    assertTrue(player.canMove(EntityDirection.UP));
    assertTrue(player.canMove(EntityDirection.RIGHT));
    assertFalse(player.canMove(EntityDirection.DOWN));
    assertFalse(player.canMove(EntityDirection.LEFT));
    assertFalse(player.outOfBounds());

    GameDataManager.getInstance().setLives(0);
    player.takeDamage();

    assertTrue(player.isDead());
    assertTrue(Graveyard.bodies.size() == 1);

    player.dispose();
  }

  public void testPlayerMovement() {
    PlayerSpaceship player = new PlayerSpaceship(stage);
    player.createBody(world);

    float delta = 0.1f;
  }

  /**
   * Cleans up after all our tests are done for this suite.
   */
  @AfterClass
  public static void dispose() {
    Graveyard.bodies.clear();
    Graveyard.actors.clear();
    stage.dispose();
  }

}
