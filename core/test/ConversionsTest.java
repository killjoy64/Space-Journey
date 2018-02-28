import edu.gvsu.cis.spacejourney.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversionsTest {

    @Test
    public void testConversionsToBox2D() {
        assertEquals(100.0 / Constants.PX_PER_M, 1.0, 0.1);
        assertEquals(200.0 / Constants.PX_PER_M, 2.0, 0.1);
        assertEquals(300.0 / Constants.PX_PER_M, 3.0, 0.1);
    }

    @Test
    public void testConversionsFromBox2D() {
        assertEquals(1.0 * Constants.PX_PER_M, 100.0, 0.1);
        assertEquals(2.0 * Constants.PX_PER_M, 200.0, 0.1);
        assertEquals(3.0 * Constants.PX_PER_M, 300.0, 0.1);
    }

}

