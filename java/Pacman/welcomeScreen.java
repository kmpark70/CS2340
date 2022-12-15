package Pacman;


import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


public class welcomeScreen extends JPanel implements ActionListener {


    private ImageIcon backGroundIcon;
    private JLabel myLabel;
    private JButton button;
    private JButton exitButton;

    public welcomeScreen() {
        backGroundIcon = new ImageIcon(this.getClass().getResource("/background.png"));
        myLabel = new JLabel(backGroundIcon);
        myLabel.setSize(650, 450);
        button = new JButton("Start");
        button.setBounds(540, 300, 105, 80);
        button.setFocusable(false);
        button.addActionListener(this);

        myLabel.add(button);
        add(myLabel);

        exitButton = new JButton("Exit");
        exitButton.setBounds(200, 300, 105, 80);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);

        myLabel.add(exitButton);
        add(myLabel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            CardLayout cl = (CardLayout) this.getParent().getLayout();
            cl.show(this.getParent(), "config");
            this.getParent().setSize(420,420);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
