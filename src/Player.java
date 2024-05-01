import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player extends GameObject {
    private int dx, dy;
    private Weapon currentWeapon;
    private Color weaponColor;
    private boolean teamChoice;

    public Player(int x, int y, int size, Weapon weapon, boolean teamChoice) {
        super(x, y, size, Color.BLUE); // default color blue
        dx = 0;
        dy = 0;
        this.currentWeapon = weapon;
        weaponColor = Color.BLUE; // default weapon pistol blue
        this.teamChoice = teamChoice;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(weaponColor);
        g.fillRect(x, y, size, size);
        if (teamChoice) g.drawImage(new ImageIcon(Constant.G2_IMAGE).getImage(), x, y, size, size, null);
        else g.drawImage(new ImageIcon(Constant.NAVI_IMAGE).getImage(), x, y, size, size, null);
    }

    /**
     * move player
     */
    public void move() {
        //privent out of bound of canvas
        x = Math.max(0, Math.min(x + dx, ShootingGame.WIDTH - size)); 
        y = Math.max(0, Math.min(y + dy, ShootingGame.HEIGHT - size));
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                dy = -5;
                break;
            case KeyEvent.VK_S:
                dy = 5;
                break;
            case KeyEvent.VK_A:
                dx = -5;
                break;
            case KeyEvent.VK_D:
                dx = 5;
                break;
            case KeyEvent.VK_UP:
                fireWeapon(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                fireWeapon(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                fireWeapon(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                fireWeapon(1, 0);
                break;
            default:
        }
    }

    public void keyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
            dy = 0;
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
            dx = 0;
        }
    }

    public void switchWeapon(Weapon weapon) {
        this.currentWeapon = weapon;
        this.weaponColor = weapon.weaponColor;
    }

    public void fireWeapon( int bulletDx, int bulletDy) {
        int bulletX = x + size / 2 - ShootingGame.BULLET_SIZE / 2;
        int bulletY = y + size / 2 - ShootingGame.BULLET_SIZE / 2;
        currentWeapon.fire(bulletX, bulletY, bulletDx, bulletDy);
    }
}

