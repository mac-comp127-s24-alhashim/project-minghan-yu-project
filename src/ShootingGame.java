import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.Rectangle;

public class ShootingGame extends JPanel implements KeyListener {
    public final static int WIDTH = 800;
    public final static int HEIGHT = 600;
    private final int PLAYER_SIZE = 40;
    private final int ENEMY_SIZE = 30;
    public final static int BULLET_SIZE = 5;

    private Player player;
    private ArrayList<Enemy> enemies;
    public static ArrayList<Bullet> bullets;
    private int score;
    private int health;
    private boolean gameRunning;

    public ShootingGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(WIDTH / 2, HEIGHT - 100, PLAYER_SIZE);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        score = 0;
        health = 100;
        gameRunning = true;

        spawnEnemies();
        startGameLoop();
    }

    private void spawnEnemies() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int x = rand.nextInt(WIDTH - ENEMY_SIZE);
            int y = rand.nextInt(HEIGHT - ENEMY_SIZE);
            enemies.add(new Enemy(x, y, ENEMY_SIZE));
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
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        checkCollisions();
        removeOutOfBoundsBullets();
        spawnEnemiesIfNeeded();
        moveEnemiesTowardsPlayer();
        checkPlayerHealth();
        checkVictoryCondition();
    }

    private void checkCollisions() {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), enemy.getSize(), enemy.getSize());
            Iterator<Bullet> bulletIterator = bullets.iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), BULLET_SIZE, BULLET_SIZE);
                if (enemyRect.intersects(bulletRect)) {
                    score += 10;
                    bulletIterator.remove();
                    enemyIterator.remove();
                    break;
                }
            }
            Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
            if (enemyRect.intersects(playerRect)) {
                health -= 10;
                enemyIterator.remove();
                break;
            }
        }
    }

    private void removeOutOfBoundsBullets() {
        bullets.removeIf(bullet -> bullet.getX() < 0 || bullet.getX() > WIDTH || bullet.getY() < 0 || bullet.getY() > HEIGHT);
    }

    private void spawnEnemiesIfNeeded() {
        if (enemies.size() < 5) {
            spawnEnemies();
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
        }
    }

    private void checkVictoryCondition() {
        if (score >= 100) {
            gameRunning = false;
            System.out.println("You win! Your score: " + score);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
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
        JFrame frame = new JFrame("Shooting Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new ShootingGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}






