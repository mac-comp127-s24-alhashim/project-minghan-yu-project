import java.awt.Graphics;

public abstract class Enemy {
    protected int x, y;
    protected int health;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update(Player player);

    public abstract void render(Graphics g);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
