public class Mob
{
    int health;
    String name;
    Action action1, action2;
    int drop;

    public Mob(String name, int health, Action action1, Action action2, int drop)
    {
        this.health  = health;
        this.action1 = action1;
        this.action2 = action2;
        this.name = name;
        this.drop = drop;
    }

    public Mob(String name, int health, Action action1, int drop)
    {
        this.health  = health;
        this.action1 = action1;
        this.name = name;
        this.drop = drop;
    }


    public void attack(Player player)
    {
        // choose one of the actions and apply its effects to the player
        // print the result
        if (action2 == null)
        {
            action1.apply(player);
            return;
        }
        ((Math.random() < 0.5) ? action1 : action2).apply(player);
    }

    public void printStats()
    {
        System.out.println(name + " has " + health + " health.");
    }
}