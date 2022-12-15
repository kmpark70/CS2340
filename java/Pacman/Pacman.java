package Pacman;

import javax.swing.*;
import java.awt.*;

public class Pacman extends JFrame {

    public Pacman(JPanel cards) {
        this.getContentPane().add(cards);
    }

    public static void main(String[] args) {

        JPanel cards = new JPanel(new CardLayout());
        welcomeScreen launchpage = new welcomeScreen();
        Configure config = new Configure();
        Model game = new Model(1,"YELLOW");
        Pacman pac = new Pacman(cards);
        GameOverScreen gameover = new GameOverScreen(0,0,0);
        pac.setVisible(true);
        pac.setFocusable(false);
        cards.setVisible(true);
        cards.setFocusable(false);
        cards.add(config, "config");
        cards.add(launchpage, "launchpage");
        cards.add(gameover, "gameover");
        cards.add(game, "game");
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards,"launchpage");
        pac.setTitle("Pacman");
        pac.setSize(380,420);
        pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pac.setLocationRelativeTo(null);
    }
}
