import java.awt.Graphics;

public class Projectile {
    private int x, y;
    private final double angle;
    private final int speed = 10; 

    public Projectile(int startX, int startY, double angle) {
        this.x = startX;
        this.y = startY;
        this.angle = angle;
    }

    // Update the projectile's position based on its angle and speed
    public void update() {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
    }

    public void render(Graphics g) {
        g.fillOval(x, y, 5, 5); // Draw the projectile as a small circle
    }
}

