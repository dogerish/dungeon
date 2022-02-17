public class Action {
    int damage;
    String name;
    // MSB-LSB: ablaze, weak, stunned, vulnerable
    Debuff debuff;

    public Action(String name, Debuff debuff)
    {
        this.damage = 0;
        this.name   = name;
        this.debuff = debuff;
    }
    public Action(int damage, String name, Debuff debuff)
    {
        this.damage = damage;
        this.name   = name;
        this.debuff = debuff;
    }

    public Action(int damage, String name)
    {
        this.damage = damage;
        this.name   = name;
        this.debuff = new Debuff();
    }

    public void apply(Player player)
    {
        int dmg = damage;
        if (!player.cleansed) {
            // attack and vulnerable
            if (player.debuff.vulnerable > 0) {
                dmg += damage;
                player.debuff.vulnerable--;
            }
            // fire
            if (player.debuff.ablaze > 0) {
                player.health -= player.debuff.ablaze*20;
                System.out.println("You took " + player.debuff.ablaze*20 + " damage from fire.");
            }
            // update debuffs
            player.debuff.vulnerable += debuff.vulnerable;
            player.debuff.stunned += debuff.stunned;
            player.debuff.weak += debuff.weak;
            player.debuff.ablaze += debuff.ablaze;
        }

        //damage from attack + vuln
        player.health -= dmg;
        System.out.println("You took " + dmg + " damage from " + name + ".");
    }
}
