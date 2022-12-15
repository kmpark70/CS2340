package Pacman;
import javax.swing.JFrame;

import Pacman.framework.ui.ButtonPanel;
import org.junit.Test;

public class buttonTest {
    /**
     * welcomeScreen test that just creates the button panel.
     *
     * @author Kyungmin Park
     */
    public class ButtonPanelTest {

        /**
         * welcomeScreen test that merely creates the panel.
         */
        @Test
        public void buttonPanelTest() {
            JFrame jf = new JFrame();
            ButtonPanel bp = new ButtonPanel().withParent(jf);
            jf.add(bp);
            jf.setVisible(true);
            // next create suitable mocks for the listeners.
            // and then all we need is a gui testing
        }

    }
}
