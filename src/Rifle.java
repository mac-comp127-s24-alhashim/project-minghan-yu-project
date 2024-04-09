public class Rifle extends Weapon {
    public Rifle() {
        super(20, 30); 
    }

    @Override
    public void fire() {
        if (ammoCount > 0) {
            System.out.println("Rifle fired. Damage: " + damage);
            ammoCount--;
        } else {
            System.out.println("Out of ammo. Reload required.");
        }
    }

    public void reload(int ammo) {
        ammoCount += ammo;
        System.out.println("Rifle reloaded. Current ammo: " + ammoCount);
    }
}
