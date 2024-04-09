import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    private Player player;

    public GameKeyListener(Player player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Move player up
                player.move(0, -5);
                break;
            case KeyEvent.VK_S: // Move player down
                player.move(0, 5);
                break;
            case KeyEvent.VK_A: // Move player left
                player.move(-5, 0);
                break;
            case KeyEvent.VK_D: // Move player right
                player.move(5, 0);
                break;
            case KeyEvent.VK_Q: // Switch weapons
                player.switchWeapon();
                break;
            case KeyEvent.VK_E: // Fire weapon
                player.fireWeapon();
                break;
            case KeyEvent.VK_SHIFT: // Toggle scope
                player.toggleScope();
                break;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.setAimingDirection(0); // 0 degrees, aiming up
                break;
            case KeyEvent.VK_RIGHT:
                player.setAimingDirection(Math.PI / 2); // 90 degrees, aiming right
                break;
            case KeyEvent.VK_DOWN:
                player.setAimingDirection(Math.PI); // 180 degrees, aiming down
                break;
            case KeyEvent.VK_LEFT:
                player.setAimingDirection(3 * Math.PI / 2); // 270 degrees, aiming left
                break;
            case KeyEvent.VK_SPACE:
                player.shoot(); // Shoot in the current aiming direction
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
