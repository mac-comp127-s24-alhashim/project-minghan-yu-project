import java.awt.*;

import javax.swing.ImageIcon;

public class Enemy extends GameObject {
    private int speedX = 1;
    private int speedY = 1;

    public Enemy(int x, int y, int size, int speedX,int speedy) {
        super(x, y, size, Color.RED);
        this.speedX = speedX==0?1:speedX;
        this.speedY = speedy==0?1:speedy;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
        g.drawImage(new ImageIcon(Constant.ENEMY_IMAGE).getImage(), x, y, size, size, null);
    }

    /**
     * move enemies toward player
     */
    public void moveTowards(int targetX, int targetY) {
        if (x < targetX) {
            x += speedX;
        } else if (x > targetX) {
            x -= speedX;
        }
        if (y < targetY) {
            y += speedY;
        } else if (y > targetY) {
            y -= speedY;
        }
    }
}
