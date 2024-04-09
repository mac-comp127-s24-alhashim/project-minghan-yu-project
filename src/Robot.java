import java.awt.Graphics;
import java.awt.Color;

public class Robot extends Enemy {
    private int speed;
    private final int SIZE = 25; 
    private static final Color COLOR = Color.RED; 

    public Robot(int x, int y) {
        super(x, y);
        this.health = 80; 
        this.speed = 3; 
    }

    @Override
    public void update(Player player) {
        int dx = player.getX() - this.x;
        int dy = player.getY() - this.y;

        double angle = Math.atan2(dy, dx);

        x += (int) (speed * Math.cos(angle));
        y += (int) (speed * Math.sin(angle));

        if (Math.abs(x - player.getX()) < 10 && Math.abs(y - player.getY()) < 10) {
            player.setHealth(player.getHealth() - 20);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(COLOR);
        g.fillRect(x, y, SIZE, SIZE); 
    }

    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

