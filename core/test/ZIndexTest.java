import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.util.ZIndex;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ZIndexTest {

    @Test
    public void testPlayerInFrontOfObjects() {
        assertTrue(ZIndex.PLAYER > ZIndex.BACKGROUND);
        assertTrue(ZIndex.PLAYER > ZIndex.COLLECTIBLE);
        assertTrue(ZIndex.PLAYER > ZIndex.PARALLAX_BACKGROUND_LAYER1);
        assertTrue(ZIndex.PLAYER > ZIndex.PARALLAX_BACKGROUND_LAYER2);
        assertTrue(ZIndex.PLAYER > ZIndex.PARALLAX_BACKGROUND_LAYER3);
        assertTrue(ZIndex.PLAYER > ZIndex.PARALLAX_BACKGROUND_LAYER1);
        assertTrue(ZIndex.PLAYER > ZIndex.PARALLAX_BACKGROUND_LAYER2);
        assertTrue(ZIndex.PLAYER > ZIndex.PARALLAX_BACKGROUND_LAYER3);
    }

}

