package Pacman;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class Model extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    private boolean inGame = false;
    private boolean dying = false;

    //Block_size = how big block is
    private final int BLOCK_SIZE = 24;
    //Indicates number of blocks -> 15*15
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int MAX_GHOSTS = 12;
    private int PACMAN_SPEED = 6;

    private int N_GHOSTS = 6;
    private int lives, score;
    private int[] dx;
    private int[] dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private boolean[] ghostLiving;

    //image of life and enemy
    private Image heart, ghost, deadGhost;
    //image of packman including up, down, left right of shape
    private Image up, down, left, right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    //class TAdapter extends KeyAdapter{} -> keyboard
    private int req_dx, req_dy;

    private final int validSpeeds[] = {1,2,3,4,6,8};
    private final int maxSpeed = 6;
    private int currentSpeed = 3;
    private short [] screenData;
    private Timer timer;
    private double speed;

    private int spawnedGhosts;

    private int ping = 0;

    private int attackPing;
    private boolean attack;
    private int ghostsKilled;
    private boolean levelCleared;
    private int roundNumber;


    private final short levelData[] = {
            // 0 = blue, 1 = left border, 2 = top border, 4 = right border, 8 = bottom border, 16 = white dots
            //32 = power pellet //64 = red pellet(+100 points) // purple pellet = 128, live + 1 //
            //256 = orange pellet, can kill the ghost
            0, 19, 18, 18, 66, 18, 18, 18, 18, 18, 18, 18, 18, 18, 38,
            19, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
            0,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
            131, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
            17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
            21, 0,  0,  0,  0,  0,  0,   0, 33, 16, 16, 16, 16, 16, 20,
            257, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 20, 0, 17, 128, 128, 16, 16, 16, 16, 16, 16, 20,
            41, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 44
    };

    public Model(double difficulty, String color) {
        loadImages(color);
        initVariables(difficulty);
        setFocusable(true);
        addKeyListener(new TAdapter());
        initGame();
        setVisible(true);
    }
    private void loadImages(String color) {
        down = new ImageIcon("src/main/java/" + color + "down.gif").getImage();
        up = new ImageIcon("src/main/java/" + color + "up.gif").getImage();
        left = new ImageIcon("src/main/java/" + color + "left.gif").getImage();
        right = new ImageIcon("src/main/java/" + color + "right.gif").getImage();
        ghost = new ImageIcon("src/main/java/ghost.gif").getImage();
        heart = new ImageIcon("src/main/java/heart.png").getImage();
        deadGhost = new ImageIcon("src/main/java/deadGhost.gif").getImage();
    }

    private void showIntroScreen(Graphics2D g2d) {

        String start = "Press SPACE to start";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (SCREEN_SIZE)/4, 150);

        String round = "Round 1";
        g2d.setColor(Color.pink);
        g2d.drawString(round,(SCREEN_SIZE)/3, 380);
    }
    private void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(5, 181, 79));
        String s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < lives; i++) {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
        String round = "Round " + roundNumber;
        g.setColor(Color.pink);
        g.drawString(round,(SCREEN_SIZE)/3, 380);
    }

    private void initVariables(double difficulty) {
        speed = difficulty;
        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        ghostLiving = new boolean[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        roundNumber = 1;

         //if timer = 1000, the speed is slower than timer = 5, but I set 40.
        timer = new Timer(40, this);
        timer.restart();
    }

    private void initGame() {
        lives = (int) ((speed - 3) * -2);
        score = 0;
        ghostsKilled = 0;
        initLevel();
        N_GHOSTS = (int) (speed * 4);
        currentSpeed = 3;
    }
    private void initLevel() {
        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }
    }
    private void playGame(Graphics2D g2d) {

        if (dying) {
            death();

        } else {
            //it shows when we press the space bar and it's gone when the players lose all their lives
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }
    private void movePacman() {

        int pos;
        short ch;


        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if (attackPing > 0) {
                attackPing--;
            } else {
                attack = false;
            }

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            if ((ch & 32) != 0) {
                screenData[pos] = (short) (ch & 31);
                score+= 30;
            }

            if ((ch & 64) != 0) {
                screenData[pos] = (short) (ch & 63);
                score += 100;
            }
            if ((ch & 128) != 0) {
                screenData[pos] = (short) (ch & 127);
                lives++;
            }
            if ((ch & 256) != 0) {
                screenData[pos] = (short) (ch & 255);
                attack = true;
                attackPing = 200;

            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
    }

    private void drawPacman(Graphics2D g2d) {

        if (req_dx == -1) {
            g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dx == 1) {
            g2d.drawImage(right, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dy == -1) {
            g2d.drawImage(up, pacman_x + 1, pacman_y + 1, this);
        } else {
            g2d.drawImage(down, pacman_x + 1, pacman_y + 1, this);
        }
    }
    private void moveGhosts(Graphics2D g2d) {

        int pos;
        int count;
        if (spawnedGhosts < N_GHOSTS && ping == 35) {
            spawnedGhosts++;
            ping = 0;
        } else if (spawnedGhosts < N_GHOSTS) {
            ping++;
        }

        for (int i = 0; i < spawnedGhosts; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                count = 0;
                // left = 1, right = 4 up = 2, down = 8
                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {
                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            if (ghostLiving[i] && !attack) {
                drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
            }
            if (ghostLiving[i] && attack) {
                drawdeadGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);
            }
            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame && ghostLiving[i]) {
                if (!attack) {
                    dying = true;
                } else {
                    ghostsKilled++;
                    score += 20;
                    ghostLiving[i] = false;
                }
            }
        }
    }
    private void drawGhost(Graphics2D g2d, int x, int y) {

        g2d.drawImage(ghost, x, y, this);
    }
    private void drawdeadGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(deadGhost,x,y,this);
    }

    private void checkMaze() {
        int i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i]) != 0) {
                finished = false;
            }
            i++;
        }

        if (finished) {
            score += 50;
            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }
            initLevel();

        }
    }
    private void death() {

        lives--;
        spawnedGhosts = 0;

        if (lives == 0) {
            inGame = false;
        }
        continueLevel();
    }

    //Define the location of ghost
    private void continueLevel() {
        int dx = 1;

        for (int i = 0; i < N_GHOSTS; i++) {
            ghostLiving[i] = true;
            ghost_y[i] = 4 * BLOCK_SIZE; //start position
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            ghostSpeed[i] = validSpeeds[(int) (speed * 2 - 1)];
        }

        pacman_x = 7 * BLOCK_SIZE;  //start position
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;    //reset direction move
        pacmand_y = 0;
        req_dx = 0;        // reset direction controls
        req_dy = 0;
        dying = false;
    }
    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;
        levelCleared = true;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(new Color(0,72,251));
                g2d.setStroke(new BasicStroke(5));

                if ((levelData[i] == 0)) {
                    g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
                    levelCleared = false;
                }
                if ((screenData[i] & 32) != 0) {
                    g2d.setColor(new Color(255,255,0));
                    g2d.fillOval(x+7,y+6,12,12);
                    levelCleared = false;
                }
                if ((screenData[i] & 64) != 0) {
                    g2d.setColor(new Color(220, 20, 60));
                    g2d.fillOval(x + 9, y + 9, 9, 9);
                    levelCleared = false;
                }
                if ((screenData[i] & 128) != 0) {
                    g2d.setColor(new Color(218,112,214));
                    g2d.fillOval(x + 9, y + 9, 9, 9);
                    levelCleared = false;
                }
                if ((screenData[i] & 256) != 0) {
                    g2d.setColor(new Color(255, 140, 0));
                    g2d.fillOval(x + 9, y + 9, 9, 9);
                    levelCleared = false;
                }

                i++;
            }
        }
    }
    public void restartTimer(){
        timer.restart();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        if (levelCleared) {
            inGame = false;
        }

        if (inGame) {
            playGame(g2d);
        } else {
            if (lives == 0) {
                timer.stop();
                JPanel cards = (JPanel) this.getParent();
                //replace spawned ghosts with some way to track the killed ghosts.
                ((GameOverScreen)cards.getComponent(2)).setValues((int) ((speed - 3)*-2) - lives,score,ghostsKilled, "Game Over");
                initGame();
                continueLevel();
                ((CardLayout) this.getParent().getLayout()).show(cards, "gameover");
                cards.getComponent(2).requestFocusInWindow();
            }
            if (levelCleared) {
                roundNumber++;
                timer.stop();
                JPanel cards = (JPanel) this.getParent();
                ((GameOverScreen) cards.getComponent(2)).setValues((int) ((speed - 3) * -2) - lives, score, ghostsKilled, "Victory!"); //replace spawned ghosts with some way to track the killed ghosts.
                initGame();
                continueLevel();
                ((CardLayout) this.getParent().getLayout()).show(cards, "gameover");
                cards.getComponent(2).requestFocusInWindow();
            }
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(inGame) {
                //keyboard dx - 1 = <- , dy -1 = ^, dy 1 = downkey, dx 1 = ->
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                }
                else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                }
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                    spawnedGhosts = 0;
                    ping = 0;
                }
                // click the spacebar, the game starts
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                    continueLevel();
                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            this.repaint();
        }
    }

}
