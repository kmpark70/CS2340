package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel implements ActionListener {
    JButton continueGame = new JButton("Continue");
    JButton quit = new JButton("Quit");
    JButton options = new JButton("Options");
    JLabel finishState = new JLabel();
    JLabel scoreLabel = new JLabel();
    JLabel deathLabel = new JLabel();
    JLabel killsLabel = new JLabel();
    GameOverScreen(int deaths, int  score, int ghostsKilled) {
        quit.addActionListener(this);
        continueGame.addActionListener(this);
        options.addActionListener(this);
        scoreLabel.setText("Score: " + score);
        deathLabel.setText("Deaths: " + deaths);
        killsLabel.setText("Ghosts Killed: " + ghostsKilled);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(finishState);
        this.add(scoreLabel);
        this.add(deathLabel);
        this.add(killsLabel);
        JPanel buttonPane = new JPanel();
        buttonPane.add(continueGame);
        buttonPane.add(options);
        buttonPane.add(quit);
        this.add(buttonPane);
        this.setVisible(true);
    }
    public void setValues(int deaths, int score, int ghostsKilled, String condition) {
        finishState.setText(condition);
        scoreLabel.setText("Score: " + score);
        deathLabel.setText("Deaths: " + deaths);
        killsLabel.setText("Ghosts Killed: " + ghostsKilled);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            System.exit(0);
        } else {
            CardLayout cl = (CardLayout) this.getParent().getLayout();
            if (e.getSource() == options) {
                cl.show(this.getParent(), "config");
            } else  if (e.getSource() == continueGame) {
                if (finishState.getText().equals("Victory")) {

                }
                cl.next(this.getParent());
                this.getParent().getComponent(3).setVisible(true);
                this.getParent().getComponent(3).requestFocusInWindow();
                ((Model)this.getParent().getComponent(3)).restartTimer();
            }
        }
    }
}