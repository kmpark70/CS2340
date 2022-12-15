package Pacman;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.Model.framework.model.Board;
import org.framework.model.Direction;
import org.Model.framework.model.Tile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
public class pacmanMazeTest {
    /**
     * Test the Board.tileAt method by means of a series of test cases with
     * Parameterized data.
     *
     * @author Kyungmin Park
     */
    @RunWith(Parameterized.class)
    public class pacmanMazeTest {

        private final int startx, starty;
        private final Direction dir;
        private final int dx, dy;

        private final Maze maze;

        private static final int WIDTH = 10;
        private static final int HEIGHT = 20;

        /**
         * Create a new test case obtaining the tile
         * from starting point (x, y) into the given direction.
         * The result should be the new point (nx, ny).
         *
         * @param x  Start x
         * @param y  Start y
         * @param d  way to go
         * @param dx Next x
         * @param dy Next y
         */
        public mazePacmanTest(int x, int y, Direction d, int dx, int dy) {
            startx = x;
            starty = y;
            dir = d;
            nextx = dx;
            nexty = dy;
            maze = new Maze(WIDTH, HEIGHT);
        }

        /**
         * The actual test case.
         */
        @Test
        public void testPacmanDirection() {
            Maze source = board.tileAt(startx, starty);
            Maze actual = board.tileAtDirection(source, dir);
            Maze desired = board.tileAt(nextx, nexty);
            assertEquals(desired, actual);
        }

        /**
         * List of (x,y)/Direction/(newx,newy) data points.
         *
         * @return Test data to be fed to constructor.
         */
        @Parameters
        public static Collection<Object[]> data() {
            Object[][] values = new Object[][]{
                    // Pick all directions from one point.
                    {2, 2, Direction.UP, 2, 1},
                    {2, 2, Direction.DOWN, 2, 3},
                    {2, 2, Direction.LEFT, 1, 2},
                    {2, 2, Direction.RIGHT, 3, 2},
                    // Verify the worm holes in all directions.
                    {0, 2, Direction.LEFT, WIDTH - 1, 2},
                    {WIDTH - 1, 2, Direction.RIGHT, 0, 2},
                    {2, 0, Direction.UP, 2, HEIGHT - 1},
                    {2, HEIGHT - 1, Direction.DOWN, 2, 0}};
            return Arrays.asList(values);
        }

    }
}
