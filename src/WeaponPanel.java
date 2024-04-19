import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * show wwapon image & handle mouse/key movement
 */
public class WeaponPanel extends JPanel {
    /**
     * store weapon
     */
    private Weapon[] weapons;
    /**
     * current selected weapon index
     */
    private int selectedIndex;

    public WeaponPanel(Weapon[] weapons) {
        this.weapons = weapons;
        // default weapon pistol
        selectedIndex = 0;
        // switch weapon based on mouse movement
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int width = getWidth() / weapons.length;
                int index = e.getX() / width; // weapon index based on mouse click 
                if (index >= 0 && index < weapons.length) {
                    selectedIndex = index; // update current weapon index
                    ShootingGame.bullets.clear(); // remove current bullets on canvas
                    repaint(); 
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth() / weapons.length;
        for (int i = 0; i < weapons.length; i++) {
            if (i == selectedIndex) {
                // mark selected weapon
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect(i * width, 0, width, getHeight());
            // draw weapon image on the panel
            g.drawImage(weapons[i].getImage(), i * width, 0, width, getHeight(), null);
        }
    }

    /**
     * get current selected weapon
     * @return
     */
    public Weapon getSelectedWeapon() {
        return weapons[selectedIndex];
    }
}

