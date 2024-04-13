
import java.awt.*;

public abstract class Weapon {
    public Color weaponColor;

    public abstract Image getImage();
    public abstract void fire(int bulletX, int bulletY, int bulletDx, int bulletDy);
    public abstract int getScore();
}
