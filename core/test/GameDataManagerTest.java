import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.managers.GameDataManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GameTest.class)
public class GameDataManagerTest {

  private static Camera camera;
  private static FitViewport viewport;
  private static Stage stage;
  private static World world;
  private static GameDataManager gameData;

  @BeforeClass
  public static void setup() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
    stage = mock(Stage.class);
    world = new World(new Vector2(0.0f,0.0f), false);

    when(stage.getViewport()).thenReturn(viewport);

    gameData = GameDataManager.getInstance();
    gameData.reset();
  }

  @Test
  public void testLivesData() {
    assertTrue(gameData.getLives() <= GameDataManager.MAX_LIVES);
    gameData.setLives(2);
    assertTrue(gameData.getLives() == 2);
  }

  @Test
  public void testScoreData() {
    assertTrue(gameData.getScore() == 0);
    gameData.setScore(1000);
    assertTrue(gameData.getScore() == 1000);
  }

  @Test
  public void testReset() {
    gameData.reset();
    assertEquals(0, gameData.getScore());
    assertEquals(3, gameData.getLives());
  }

  @Test
  public void testSingleton() {
    GameDataManager instance1 = GameDataManager.getInstance();
    assertEquals(instance1, gameData);
    assertTrue(instance1.getLives() == gameData.getLives());
  }

  @AfterClass
  public static void dispose() {
    gameData.reset();
  }

}
