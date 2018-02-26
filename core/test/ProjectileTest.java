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
import edu.gvsu.cis.spacejourney.entity.projectile.Laser;
import edu.gvsu.cis.spacejourney.entity.projectile.Projectile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GameTest.class)
public class ProjectileTest {

  private static Camera camera;
  private static FitViewport viewport;
  private static Stage stage;
  private static World world;
  private static Projectile proj;

  @BeforeClass
  public static void setup() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
    stage = mock(Stage.class);
    world = new World(new Vector2(0.0f, 0.0f), false);

    when(stage.getViewport()).thenReturn(viewport);
    SpaceJourney.Companion.getAssetManager().load("laser.png", Texture.class);
    SpaceJourney.Companion.getAssetManager().finishLoading();
  }

  @Before
  public void setupLaser() {
    proj = new Laser(stage);
    proj.spawn(world, 0.0f, 0.0f);
  }

  @Test
  public void testLaserPhysicsBody() {
    Body body = proj.getBody();

    // Player's body should not be null once created.
    assertNotNull(proj.getBody());

    world.destroyBody(proj.getBody());
    proj.createBody(world);

    // Player's body should not change even if it is redefined.
    assertEquals(body, proj.getBody());
  }

  @Test
  public void testLaserLogic() {
    assertTrue(proj.isAlive());
    assertTrue(proj.getBody().getLinearVelocity().y > 0);
    proj.reset();
    assertFalse(proj.isAlive());
  }

  @Test
  public void testLaserStorm() {
    for (int i = 0; i < 20; i++) {
      Laser laser = new Laser(stage);
      laser.spawn(world, i * 1.0f, 0.0f);
      assertTrue(laser.isAlive());
    }
  }

  @After
  public void resetLaser() {
    world.destroyBody(proj.getBody());
    proj.dispose();
  }

  @AfterClass
  public static void dispose() {
    Graveyard.BODIES.clear();
    Graveyard.ACTORS.clear();
    stage.dispose();
  }

}
