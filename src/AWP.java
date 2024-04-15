
import javax.swing.*;

import java.awt.*;

public class AWP extends Weapon {
    private Image image;

    public AWP() {
        super();
        weaponColor = Color.YELLOW;
        image = new ImageIcon(Constant.AWP_IMAGE).getImage();
    }
    @Override
    public void fire(int bulletX, int bulletY, int bulletDx, int bulletDy) {
        ShootingGame.bullets.add(new Bullet(bulletX, bulletY, bulletDx, bulletDy));
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public int getScore() {
        return 45;
    }
}