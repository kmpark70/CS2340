package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Configure extends JPanel implements ActionListener {

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton button = new JButton("Start the Game");

    JRadioButton easy = new JRadioButton("Easy");
    JRadioButton medium = new JRadioButton("Medium");
    JRadioButton hard = new JRadioButton("Hard");
    ButtonGroup difficulty = new ButtonGroup();

    JRadioButton left = new JRadioButton();
    JRadioButton middle = new JRadioButton();
    JRadioButton right = new JRadioButton();
    ButtonGroup spriteSelection = new ButtonGroup();

    JTextField enterName = new JTextField("Enter name: ", 20);
    JButton nameButton = new JButton("Submit Name!");
    JTextField playerName = new JTextField("No name yet");


    JMenuBar menuBar = new JMenuBar();
    JMenuItem item1, item2, item3;

    JTextField nameArea = new JTextField();
    
    ImageIcon redPacmanIcon;
    ImageIcon yellowPacmanIcon;
    ImageIcon bluePacmanIcon;
    JLabel redPacman = new JLabel(); //JLabel Creation
    JLabel yellowPacman = new JLabel();
    JLabel bluePacman = new JLabel();


    boolean isNameSet = false;
    boolean isDifficultySet = false;

    double speed = 1;
    String color = "YELLOW";

    Configure() {
        
        button.setBounds(100,160,200,40);

        JMenu menu = new JMenu("Click Here to Select Difficulty");
        item1 = new JMenuItem("Easy");
        item2 = new JMenuItem("Medium");
        item3 = new JMenuItem("Hard");

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.addActionListener(this);

        easy.setBounds(50, 150, 100, 50);
        medium.setBounds(150, 150, 100, 50);
        hard.setBounds(250, 150, 100, 50);

        left.setBounds(50, 325, 20, 20);
        middle.setBounds(200, 325, 20, 20);
        right.setBounds(350, 325, 20, 20);

        nameButton.addActionListener(this);
        nameButton.setBounds(200, 100, 150, 50);

        button.setBounds(50,350,300,50);
        button.setFocusable(false);
        button.addActionListener(this);

        this.add(button);
        this.setVisible(true);

        enterName.setBounds(50, 50, 100, 25);
        enterName.setFocusable(false);

        nameArea.setBounds(150, 50, 100, 25);
        nameArea.setFocusable(true);

        playerName.setBounds(50, 100, 150, 50);
        playerName.setFocusable(false);


        redPacmanIcon = new ImageIcon(this.getClass().getResource("/RedPacman.jpg"));
        Image redResized = redPacmanIcon.getImage();
        redResized = redResized.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        redPacmanIcon = new ImageIcon(redResized);
        redPacman.setIcon(redPacmanIcon);
        //label.setSize(100, 200);

         //Sets the image to be displayed as an icon
        Dimension size = redPacman.getPreferredSize(); //Gets the size of the image                       add(easy);
        redPacman.setBounds(50, 200,size.width,size.height);

        yellowPacmanIcon = new ImageIcon(this.getClass().getResource("/YellowPacman.jpg"));
        Image yellowResized = yellowPacmanIcon.getImage();
        yellowResized = yellowResized.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        yellowPacmanIcon = new ImageIcon(yellowResized);
        yellowPacman.setIcon(yellowPacmanIcon);
        //label.setSize(100, 200);

        //Sets the image to be displayed as an icon
        size = yellowPacman.getPreferredSize(); //Gets the size of the image                       add(easy);
        yellowPacman.setBounds(175, 200,size.width,size.height);

        bluePacmanIcon = new ImageIcon(this.getClass().getResource("/BluePacman.jpg"));
        Image blueResized = bluePacmanIcon.getImage();
        blueResized = blueResized.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        bluePacmanIcon = new ImageIcon(blueResized);
        bluePacman.setIcon(bluePacmanIcon);
        //label.setSize(100, 200);

        //Sets the image to be displayed as an icon
        size = bluePacman.getPreferredSize(); //Gets the size of the image                       add(easy);
        bluePacman.setBounds(300, 200,size.width,size.height);

        add(easy);
        add(medium);
        add(hard);
        add(left);
        add(middle);
        add(right);
        add(enterName);
        add(button);
        add(playerName);
        add(nameButton);
        add(nameArea);
        add(redPacman);
        add(yellowPacman);
        add(bluePacman);



        setLayout(null);
        setVisible(true);

        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);

        spriteSelection.add(left);
        spriteSelection.add(right);
        spriteSelection.add(middle);

   
      

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            if (isNameSet && (easy.isSelected() || medium.isSelected() || hard.isSelected()) && (left.isSelected()
                        || right.isSelected()) || middle.isSelected()) {
                if (medium.isSelected()) {
                    speed = 1.5;
                } else if (hard.isSelected()) {
                    speed = 2;
                }
                if (left.isSelected()) {
                    color = "RED";
                } else if(right.isSelected()) {
                    color = "BLUE";
                }
                Model myModel = new Model(speed, color);
                JPanel cards = (JPanel) this.getParent();
                cards.remove(3);
                cards.add(myModel, "game");
                ((CardLayout) this.getParent().getLayout()).show(cards, "game");
                cards.getComponent(3).requestFocusInWindow();
            }
            }
            if (e.getSource() == nameButton) {
                String text = nameArea.getText();
                if (text == null || text.isBlank()) {
                    playerName.setText("Please enter a real name");
                    isNameSet = false;
                } else {
                    playerName.setText("Name: " + text);
                    isNameSet = true;
                }
            }
        }
}
        







