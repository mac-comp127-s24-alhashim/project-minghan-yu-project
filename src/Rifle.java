import javax.swing.*;

import java.awt.*;

public class Rifle extends Weapon {
    private Image image;

    public Rifle() {
        super();
        weaponColor = Color.GREEN;
        image = new ImageIcon(Constant.RIFLE_IMAGE).getImage();
    }
    @Override
    public void fire(int bulletX, int bulletY, int bulletDx, int bulletDy) {
        ShootingGame.bullets.add(new Bullet(bulletX, bulletY, bulletDx*4, bulletDy*4));
        ShootingGame.bullets.add(new Bullet(bulletX, bulletY, bulletDx*4, bulletDy*4));
    }

    @Override
    public Image getImage() {
        return image;
    }
    @Override
    public int getScore() {
        return 20;
    }
}