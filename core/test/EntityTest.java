import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.Entity;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityTest extends GameTest {

    @Test
    public void testEntityCreate() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Camera camera = new OrthographicCamera();
                FitViewport viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
                Stage stage = new Stage(viewport);
                Entity e = new PlayerSpaceship(stage);
                stage.addActor(e);
                int actors = stage.getActors().size;
                assertEquals(actors, 1);
            }
        });
    }

}
