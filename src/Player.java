import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Player {
    private int x, y;
    public static final int SIZE = 30; 
    private int health;
    private boolean isScoped = false;

    private Weapon equippedWeapon;
    private List<Weapon> weapons = new ArrayList<>();
    private int equippedWeaponIndex = -1; 
    private double aimingDirection = 0;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 100; 
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
        // If no weapon is currently equipped, equip the added one
        if (equippedWeapon == null) {
            equippedWeapon = weapon;
            equippedWeaponIndex = 0;
        }
    }

    public void switchWeapon() {
        if (weapons.size() > 1) {
            equippedWeaponIndex = (equippedWeaponIndex + 1) % weapons.size();
            equippedWeapon = weapons.get(equippedWeaponIndex);
            System.out.println("Switched to " + equippedWeapon.getClass().getSimpleName());
        }
    }

    // Method to render the player on the screen
    public void render(Graphics g) {
        g.setColor(Color.BLUE); // Color for the player
        g.fillRect(x, y, SIZE, SIZE); // Draw the player as a square
    }

   
    public void update() {
        
    }

   
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
        System.out.println("Weapon equipped.");
    }

    public void fireWeapon() {
        if (equippedWeapon != null) {
            equippedWeapon.fire();
        } else {
            System.out.println("No weapon equipped.");
        }
    }

    public void useWeapon() {
        if (equippedWeapon instanceof AWP) {
            if (isScoped) {
                ((AWP) equippedWeapon).scopedFire();
            } else {
                System.out.println("Need to scope in before firing AWP.");
            }
        } else {
            equippedWeapon.fire();
        }
    }

    public void toggleScope() {
        isScoped = !isScoped;
        System.out.println("Scope " + (isScoped ? "enabled" : "disabled"));
    }

    public boolean isScoped() {
        return isScoped;
    }

    public void move(int deltaX, int deltaY) {
        int newX = this.x + deltaX;
        int newY = this.y + deltaY;
    
        if (newX >= 0 && newX <= Game.WIDTH - Player.SIZE) {
            this.x = newX;
        }
        if (newY >= 0 && newY <= Game.HEIGHT - Player.SIZE) {
            this.y = newY;
        }
    }

    public void setAimingDirection(double angle) {
        this.aimingDirection = angle;
    }

    public void shoot() {
        System.out.println("Shooting at angle (radians): " + aimingDirection);
    }
}
