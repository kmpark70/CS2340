package Pacman;
import org.Model.framework.model.Player;
import org.Model.framework.model.PointManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the basic behavior of a point manager.
 * @author Kyungmin Park
 */
public class PointClassTest {
    private PointManager pm;
    private final int totalPoints = 10;
    @Mock private Player player;

    /**
     * Create a point manager, and give it some initial points.
     */
    @Before public void setUp() {
        pm = new PointManager();
        pm.addPointsToBoard(totalPoints);
    }

    /**
     * After food has been added, there's more to eat.
     */
    @Test public void testAdd() {
        assertFalse(pm.allEaten());
    }

    /**
     * After half has been eaten, there's still more to eat.
     */
    @Test public void testEatSomePellet() {
        final int pointsToEat = totalPoints / 2;
        pm.consumePointsOnBoard(player, pointsToEat);
        assertFalse(pm.allEaten());
        assertEquals(pointsToEat, pm.getPelletEaten());
    }

    /**
     * Detect that all has been eaten.
     */
    @Test public void testEatAll() {
        pm.consumePointsOnBoard(player, totalPoints);
        assertTrue(pm.allEaten());
    }
}
