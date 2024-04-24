import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

public class Enemy extends GameObject {
    private int speedX = 1;
    private int speedY = 1;
    private Random rand = new Random();
    private int flag = rand.nextInt(99);
    private boolean teamChoice;

    public Enemy(int x, int y, int size, int speedX,int speedy, boolean teamChoice) {
        // super(x, y, size, new Color(199,0,0));
        super(x, y, size, Color.BLACK);
        this.speedX = speedX==0?1:speedX;
        this.speedY = speedy==0?1:speedy;
        this.teamChoice = teamChoice;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, size, size);
        // if (teamChoice) g.drawImage(new ImageIcon(Constant.NAVI_IMAGE).getImage(), x, y, size, size, null);
        // else g.drawImage(new ImageIcon(Constant.G2_IMAGE).getImage(), x, y, size, size, null);
        if (teamChoice){
            switch (flag/10) {
                case 1:  g.drawImage(new ImageIcon(Constant.FAZE_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 2:  g.drawImage(new ImageIcon(Constant.NAVI_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 3:  g.drawImage(new ImageIcon(Constant.MOUZ_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 4:  g.drawImage(new ImageIcon(Constant.TS_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 5:  g.drawImage(new ImageIcon(Constant.VITALITY_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 6:  g.drawImage(new ImageIcon(Constant.EF_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 7:  g.drawImage(new ImageIcon(Constant.C9_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 8:  g.drawImage(new ImageIcon(Constant.HEROIC_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 9:  g.drawImage(new ImageIcon(Constant.VP_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 0: g.drawImage(new ImageIcon(Constant.ASTRALIS_IMAGE).getImage(), x, y, size, size, null);
                         break;
                default: g.drawImage(new ImageIcon(Constant.NAVI_IMAGE).getImage(), x, y, size, size, null);
                         break;
            }
        }
        else {
            switch (flag/10) {
                case 1:  g.drawImage(new ImageIcon(Constant.FAZE_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 2:  g.drawImage(new ImageIcon(Constant.MOUZ_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 3:  g.drawImage(new ImageIcon(Constant.TS_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 4:  g.drawImage(new ImageIcon(Constant.G2_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 5:  g.drawImage(new ImageIcon(Constant.VITALITY_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 6:  g.drawImage(new ImageIcon(Constant.EF_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 7:  g.drawImage(new ImageIcon(Constant.C9_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 8:  g.drawImage(new ImageIcon(Constant.HEROIC_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 9:  g.drawImage(new ImageIcon(Constant.VP_IMAGE).getImage(), x, y, size, size, null);
                         break;
                case 0: g.drawImage(new ImageIcon(Constant.ASTRALIS_IMAGE).getImage(), x, y, size, size, null);
                         break;
                default: g.drawImage(new ImageIcon(Constant.TS_IMAGE).getImage(), x, y, size, size, null);
                         break;
            }
        }
        
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
