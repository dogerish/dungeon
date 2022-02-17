public class Tier {
    int tier;
    Mob mob1, mob2;

    public Tier(int tier, Mob mob1, Mob mob2)
    {
        this.tier = tier;
        this.mob1 = mob1;
        this.mob2 = mob2;
    }

    public Mob spawnMob()
    {
        // only spawn a mob tier * 33% of the time
        if (Math.random() >= tier / 2.f) return null;
        // 50-50 between mobs
        Mob mob = (Math.random() < 0.5) ? mob1 : mob2;
        return new Mob(mob.name, mob.health, mob.action1, mob.action2, mob.drop);
    }
}
