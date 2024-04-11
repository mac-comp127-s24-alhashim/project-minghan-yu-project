import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.Rectangle;

public class Player {
    private int x, y, size, dx, dy;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        dx = 0;
        dy = 0;
    }

    public void move() {
        x = Math.max(0, Math.min(x + dx, ShootingGame.WIDTH - size));
        y = Math.max(0, Math.min(y + dy, ShootingGame.HEIGHT - size));
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            dy = -5;
        } else if (keyCode == KeyEvent.VK_S) {
            dy = 5;
        } else if (keyCode == KeyEvent.VK_A) {
            dx = -5;
        } else if (keyCode == KeyEvent.VK_D) {
            dx = 5;
        } else if (keyCode == KeyEvent.VK_SPACE) {
            fireBullet();
        }
    }

    public void keyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
            dy = 0;
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
            dx = 0;
        }
    }

    private void fireBullet() {
        int bulletX = x + size / 2 - ShootingGame.BULLET_SIZE / 2;
        int bulletY = y + size / 2 - ShootingGame.BULLET_SIZE / 2;
        int bulletDx = dx != 0 ? dx : 5; // 默认发射方向
        int bulletDy = dy != 0 ? dy : 5;
        ShootingGame.bullets.add(new Bullet(bulletX, bulletY, bulletDx, bulletDy));
    }
}