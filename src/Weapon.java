public abstract class Weapon {
    protected int damage;
    protected int ammoCount;

    public Weapon(int damage, int ammoCount) {
        this.damage = damage;
        this.ammoCount = ammoCount;
    }

    public abstract void fire();

    public int getDamage() {
        return damage;
    }

    public int getAmmoCount() {
        return ammoCount;
    }

}
