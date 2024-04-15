import javax.swing.*;

import java.awt.*;

public class Pistol extends Weapon {
    private Image image;

    public Pistol() {
        super();
        weaponColor = Color.BLUE;
        image = new ImageIcon(Constant.PISTOL_IMAGE).getImage();
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void fire(int bulletX, int bulletY, int bulletDx, int bulletDy) {
        ShootingGame.bullets.add(new Bullet(bulletX, bulletY, bulletDx*2, bulletDy*2));
    }

    @Override
    public int getScore() {
        return 10;
    }
}