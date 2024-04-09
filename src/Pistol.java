public class Pistol extends Weapon {
    public Pistol() {
        super(10, 15); 
    }

    @Override
    public void fire() {
        if (ammoCount > 0) {
            System.out.println("Pistol fired. Damage: " + damage);
            ammoCount--;
        } else {
            System.out.println("Out of ammo. Reload required.");
        }
    }

    public void reload(int ammo) {
        ammoCount += ammo;
        System.out.println("Pistol reloaded. Current ammo: " + ammoCount);
    }
}

