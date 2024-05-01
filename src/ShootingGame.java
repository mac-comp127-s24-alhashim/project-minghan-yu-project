import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
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
    private boolean teamChoice = true;
    private Random rand = new Random();
    private Music music = new Music();
    private GameUpdater gameUpdater;


    public ShootingGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        displayChoicePanel();
    }

    private void initialize() {
        player = new Player(WIDTH / 2, HEIGHT - 100, PLAYER_SIZE, new Pistol(), teamChoice);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        score = 0;
        health = 100;
        gameRunning = true;
        Weapon[] weapons = new Weapon[] {new Pistol(), new Rifle(), new AWP()}; // create 3 weapon categories
        weaponPanel = new WeaponPanel(weapons); // create weapon panel
        weaponPanel.setPreferredSize(new Dimension(150, 50)); // set weapon panel size
        add(weaponPanel, BorderLayout.NORTH); // add weapon panel to canvas

        // Choose background music based on the team choice
        if (teamChoice) {
            music.playMusic("res/Music/background_music_2.wav");
        } else {
            music.playMusic("res/Music/background_music_1.wav");
        }

        gameUpdater = new GameUpdater(this, player, enemies, bullets, weaponPanel, 
        score, health, currentLevel, gameRunning, music, teamChoice);
        gameUpdater.spawnEnemies(Constant.INIT_ENMEMY_NUM);

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
                teamChoice = true;
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

    private void startGameLoop() {
        Thread gameLoop = new Thread(() -> {
            while (gameRunning) {
                gameUpdater.update();
                score = gameUpdater.getScore();
                health = gameUpdater.getHealth();
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

    /**
     * draw game window
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        ArrayList<Enemy> enemiesCopy = new ArrayList<>(enemies);
        for (Enemy enemy : enemiesCopy) {
            enemy.draw(g);
        }
        // enemiesCopy.forEach(enemy -> enemy.draw(g));
        ArrayList<Bullet> bulletsCopy = new ArrayList<>(bullets);
        for (Bullet bullet : bulletsCopy) {
            bullet.draw(g);
        }
        // bulletsCopy.forEach(bullet -> bullet.draw(g));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 20, 20);
        g.drawString("Health: " + health, 20, 40);
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



