import java.awt.Graphics;
import java.awt.Color;

public class Zombie extends Enemy {
    private int speed;
    private final int SIZE = 20;

    public Zombie(int x, int y) {
        super(x, y);
        this.health = 50; 
        this.speed = 1; 
    }

    @Override
    public void update(Player player) {
        // Simple AI to move towards the player
        int dx = player.getX() - this.x;
        int dy = player.getY() - this.y;

        // Calculate the angle to the player
        double angle = Math.atan2(dy, dx);

        // Move the zombie towards the player
        x += (int) (speed * Math.cos(angle));
        y += (int) (speed * Math.sin(angle));

        if (Math.abs(x - player.getX()) < 10 && Math.abs(y - player.getY()) < 10) {
            player.setHealth(player.getHealth() - 10);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN); 
        g.fillRect(x, y, SIZE, SIZE); 
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
