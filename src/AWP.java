public class AWP extends Weapon {
    public AWP() {
        super(100, 5); 
    }

    @Override
    public void fire() {
        if (ammoCount > 0) {
            System.out.println("AWP fired. Damage: " + damage);
            ammoCount--;
        } else {
            System.out.println("Out of ammo. Reload required.");
        }
    }

    public void reload(int ammo) {
        ammoCount += ammo;
        System.out.println("AWP reloaded. Current ammo: " + ammoCount);
    }

    public void scopedFire() {
        System.out.println("AWP fired with scope. Damage: " + damage);
        ammoCount--;
    }

    public void fire(boolean isScoped) {
        if (ammoCount > 0) {
            if (isScoped) {
                System.out.println("AWP scoped fire. Damage: " + damage);
                ammoCount--;
            } else {
                System.out.println("Need to scope in before firing AWP.");
            }
        } else {
            System.out.println("Out of ammo. Reload required.");
        }
    }
}

