package Pacman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.awt.Image;

import Pacman.framework.factory.FactoryException;
import Pacman.framework.model.Direction;
import Pacman.framework.view.ImageLoader;

import org.junit.Before;
import org.junit.Test;

/**
 * Fairly basic test cases for the image factory.
 * @author Arie van Deursen, TU Delft, created 2007.
 */
public class ImageLoaderTest {
    /**
     * The factory under test.
     */
    private ImageLoader imf;

    /**
     * Actually create the image factory.
     * @throws FactoryException if images can't be found.
     */
    @Before public void setUp() throws FactoryException {
        imf = new ImageLoader();
        imf.loadImages();
    }

    /**
     * Are images for player properly loaded?
     */
    @Test public void testPacman() {
        Image up = imf.pacman(YELLOWup, 1);
        Image down = imf.pacman(YELLOWdown, 1);
        Image right = imf.pacman(YELLOWright, 1);
        Image left = imf.pacman(YELLOWleft, 1);
        assertNotSame(up, down);
    }

    /**
     * Are monster images properly loaded?
     */
    @Test public void testGhost() {
        Image m1 = imf.ghost(0);
        Image m2 = imf.deadGhost(0);
        assertEquals(m1, m2);
    }
}
