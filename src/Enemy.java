import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.Rectangle;

public class Enemy {
    private int x, y, size;

    public Enemy(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
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

    public void moveTowards(int targetX, int targetY) {
        if (x < targetX) {
            x++;
        } else if (x > targetX) {
            x--;
        }
        if (y < targetY) {
            y++;
        } else if (y > targetY) {
            y--;
        }
    }
}
