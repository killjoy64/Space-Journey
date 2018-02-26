import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.entity.Graveyard;
import edu.gvsu.cis.spacejourney.entity.enemy.Enemy;
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GameTest.class)
public class EnemyTest {

  private static Camera camera;
  private static FitViewport viewport;
  private static Stage stage;
  private static World world;
  private static Enemy enemy;

  @BeforeClass
  public static void setup() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
    stage = mock(Stage.class);
    world = new World(new Vector2(0.0f, 0.0f), false);

    when(stage.getViewport()).thenReturn(viewport);
    SpaceJourney.Companion.getAssetManager().load("spaceship3.png", Texture.class);
    SpaceJourney.Companion.getAssetManager().finishLoading();
  }

  @Before
  public void setupEnemy() {
    enemy = new EvilSpaceship(stage);
    enemy.createBody(world);
  }

  @Test
  public void testEnemyPhysicsBody() {
    Body body = enemy.getBody();

    // Player's body should not be null once created.
    assertNotNull(enemy.getBody());

    world.destroyBody(enemy.getBody());
    enemy.createBody(world);

    // Player's body should not change even if it is redefined.
    assertEquals(body, enemy.getBody());
  }

  @Test
  public void testEnemyLogic() {
    enemy.setMaxHitPoints(2);
    enemy.setScore(40);
    assertEquals(enemy.getMaxHitPoints(), enemy.getHitPoints());
    assertEquals(enemy.getScore(), 40);

    enemy.takeDamage();
    assertEquals(enemy.getHitPoints(), enemy.getMaxHitPoints() - 1);
    enemy.takeDamage();
    assertEquals(enemy.getHitPoints(), enemy.getMaxHitPoints() - 2);
    assertEquals(enemy.getHitPoints(), 0);

    assertTrue(Graveyard.BODIES.contains(enemy.getBody()));
  }

  @Test
  public void testEnemyMovement() {
    // TODO - John help please.
  }

  @After
  public void resetEnemy() {
    world.destroyBody(enemy.getBody());
    enemy.dispose();
  }

  @AfterClass
  public static void dispose() {
    Graveyard.BODIES.clear();
    Graveyard.ACTORS.clear();
    stage.dispose();
  }

}
