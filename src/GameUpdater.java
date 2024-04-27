import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;


public class GameUpdater extends JPanel {
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
    private ShootingGame game;

    public GameUpdater(ShootingGame game, Player player, ArrayList<Enemy> enemies, ArrayList<Bullet> bullets, WeaponPanel weaponPanel
    , int score, int health, int currentLevel, boolean gameRunning, Music music, boolean teamChoice) {
        this.game = game;
        this.player = player;
        this.enemies = enemies;
        this.bullets = bullets;
        this.weaponPanel = weaponPanel;
        this.score = score;
        this.health = health;
        this.currentLevel = currentLevel;
        this.gameRunning = gameRunning;
        this.music = music;
        this.teamChoice = teamChoice;
    }

    public void update() {
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
            boolean i_exit = true;
            for (int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), BULLET_SIZE, BULLET_SIZE);
                if (enemyRect.intersects(bulletRect)) {
                    score += player.getCurrentWeapon().getScore();
                    bullets.remove(j);
                    enemies.remove(i);
                    j--; 
                    i_exit = false;
                    break;
                }
            }
            Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getSize(), player.getSize());
            if (enemyRect.intersects(playerRect)) {
                health -= 10;
                if (i_exit) {
                    enemies.remove(i);
                    i_exit = false;
                }
            }
            if (!i_exit) {
                i--;
            }
        }
    }

    private void removeOutOfBoundsBullets() {
        if (bullets != null) {
            ListIterator<Bullet> iterator = bullets.listIterator();
            while(iterator.hasNext()) {
                Bullet bullet = iterator.next();
                if (bullet.getX() < 0 || bullet.getX() > WIDTH || bullet.getY() < 0 || bullet.getY() > HEIGHT) {
                    iterator.remove();
                }
            }
        }
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

    public void spawnEnemies(int num) {
        Random rand = new Random();
        for (int i = 0; i < num; i++) {
            int x = rand.nextInt(WIDTH - ENEMY_SIZE);
            int y = rand.nextInt(HEIGHT - ENEMY_SIZE);
            enemies.add(new Enemy(x, y, ENEMY_SIZE, currentLevel-1 ,currentLevel-1, teamChoice));
        }
    }

    private void gameOver(boolean win) {
        music.stopMusic();
        GameOverHandler gameOverHandler = new GameOverHandler(music, score);
        gameOverHandler.gameOver(win, teamChoice);
    }

    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
    }
}
