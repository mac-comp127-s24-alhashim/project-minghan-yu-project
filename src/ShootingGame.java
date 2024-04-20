import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ShootingGame extends JPanel implements KeyListener {
    public final static int WIDTH = 800;
    public final static int HEIGHT = 600;
    public final static int BULLET_SIZE = 5;

    private final int PLAYER_SIZE = 40;
    private final int ENEMY_SIZE = 30;

    private static int ENEMY_NUM = 5;
    public static ArrayList<Bullet> bullets;

    private Player player;
    private ArrayList<Enemy> enemies;
    private int score;
    private int health;
    private boolean gameRunning;
    private int currentLevel = 1;
    private WeaponPanel weaponPanel;
    private Clip clip;
    private boolean teamChoice = true;
    private Random rand = new Random();

    
    public ShootingGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        displayChoicePanel();
    }

    private void initialize(){
        player = new Player(WIDTH / 2, HEIGHT - 100, PLAYER_SIZE, new Pistol(), teamChoice);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        score = 0;
        health = 100;
        gameRunning = true;
        // create 3 weapon categories
        Weapon[] weapons = new Weapon[] {new Pistol(), new Rifle(), new AWP()};
        // create weapon panel
        weaponPanel = new WeaponPanel(weapons);
        // set weapon panel size
        weaponPanel.setPreferredSize(new Dimension(150, 50));
        // add weapon panel to canvas
        add(weaponPanel, BorderLayout.NORTH);
        playMusic("background_music.wav"); 
        spawnEnemies(Constant.INIT_ENMEMY_NUM);
        startGameLoop();
    }

    private void displayChoicePanel() {
        JDialog choiceDialog = new JDialog();
        choiceDialog.setTitle("Choose Your Team");
        choiceDialog.setSize(300, 100);
        choiceDialog.setLayout(new FlowLayout());
        choiceDialog.setModal(true);
        choiceDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        choiceDialog.setLocationRelativeTo(null);

        JButton G2Button = new JButton("G2");
        JButton NAVIButton = new JButton("NAVI");

        G2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choiceDialog.dispose();
                initialize();
            }
        });
        NAVIButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choiceDialog.dispose();
                teamChoice = false;
                initialize();
            }
        });

        choiceDialog.add(G2Button);
        choiceDialog.add(NAVIButton);
        choiceDialog.setVisible(true);
    }

    private void playMusic(String path) {
        try {
            URL url = this.getClass().getResource(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void spawnEnemies(int num) {
        Random rand = new Random();
        for (int i = 0; i < num; i++) {
            int x = rand.nextInt(WIDTH - ENEMY_SIZE);
            int y = rand.nextInt(HEIGHT - ENEMY_SIZE);
            enemies.add(new Enemy(x, y, ENEMY_SIZE, currentLevel-1 ,currentLevel-1, teamChoice));
        }
    }

    private void startGameLoop() {
        Thread gameLoop = new Thread(() -> {
            while (gameRunning) {
                update();
                repaint();
                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameLoop.start();
    }

    private void update() {
        player.move();
        player.switchWeapon(weaponPanel.getSelectedWeapon());
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        checkCollisions();
        removeOutOfBoundsBullets();
        spawnEnemiesIfNeeded();
        moveEnemiesTowardsPlayer();
        checkPlayerHealth();
        checkNextLevel(); 
    }

    /**
     * condition to enter next level
     */
    private void checkNextLevel() {
        int nextLevelThreshold = 200 * currentLevel;
        // check whether player's score surpass threshold for next level
        if (score >= nextLevelThreshold) {
            currentLevel++; // enter next level
            if (currentLevel <= 3) {
                JOptionPane.showMessageDialog(this, "Congratulations, Enter Next Level", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                // update enemy number and moving speed
                enemies.clear();
                ENEMY_NUM = 10;
                // add new enemies
                spawnEnemies(ENEMY_NUM);
            } 
            else gameOver(true);
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), enemy.getSize(), enemy.getSize());
            boolean iFlag = true;
            for (int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), BULLET_SIZE, BULLET_SIZE);
                if (enemyRect.intersects(bulletRect)) {
                    // get score based on current weapon type
                    score += player.getCurrentWeapon().getScore();
                    bullets.remove(j);
                    enemies.remove(i);
                    j--; 
                    //enemy i need to be removed
                    iFlag = false;
                    break;
                }
            }
            Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
            if (enemyRect.intersects(playerRect)) {
                health -= 10;
                if (iFlag) {
                    enemies.remove(i);
                    iFlag = false;
                }
            }
            if (!iFlag) {
                i--;
            }
        }
    }

    private void removeOutOfBoundsBullets() {
        bullets.removeIf(bullet -> bullet.getX() < 0 || bullet.getX() > WIDTH || bullet.getY() < 0 || bullet.getY() > HEIGHT);
    }

    private void spawnEnemiesIfNeeded() {
        if (enemies.size() < 5) {
            spawnEnemies(ENEMY_NUM);
        }
    }

    private void moveEnemiesTowardsPlayer() {
        for (Enemy enemy : enemies) {
            enemy.moveTowards(player.getX(), player.getY());
        }
    }

    private void checkPlayerHealth() {
        if (health <= 0) {
            gameRunning = false;
            System.out.println("Game Over! Your score: " + score);
            // game end
            gameOver(false);
        }
    }

    /**
     * draw game window
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 20, 20);
        g.drawString("Health: " + health, 20, 40);
    }

    private void gameOver(boolean win) {
        if (teamChoice) gameOverG2(win);
        else gameOverNAVI(win);
    }

    private void gameOverG2 (boolean win){
        String message;
        ImageIcon icon;
        if (win) {
            message = "You win! Your score: " + score;
            icon = null;
        } 
        else {
            ImageIcon icon1 = new ImageIcon(Constant.G2LOSE1_IMAGE);
            ImageIcon icon2 = new ImageIcon(Constant.G2LOSE2_IMAGE);
            message = "Game Over! Your score: " + score;
            int flag = rand.nextInt(10);
            if (flag <= 5) icon = new ImageIcon(icon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            else icon = new ImageIcon(icon2.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    private void gameOverNAVI (boolean win){
        String message;
        ImageIcon icon;
        if (win) {
            message = "You win! Your score: " + score;
            icon = null;
        } 
        else {
            ImageIcon icon1 = new ImageIcon(Constant.G2LOSE1_IMAGE);
            ImageIcon icon2 = new ImageIcon(Constant.G2LOSE2_IMAGE);
            message = "Game Over! Your score: " + score;
            int flag = rand.nextInt(10);
            if (flag <= 5) icon = new ImageIcon(icon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            else icon = new ImageIcon(icon2.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shooting Game by FMH&YY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new ShootingGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



