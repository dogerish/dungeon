public class Main {
    public static void main(String[] args)
    {
        // tier 1
        Mob imp = new Mob(
            "Imp", 40,
            new Action(20, "Poke"), 0
        );
        Mob elf = new Mob(
            "Elf", 60,
            new Action(10, "Candy Stab"), new Action("Freeze", new Debuff(3,1,0,0)), 0
        );

        // tier 2
        Mob alligator = new Mob(
            "Alligator", 80,
            new Action(30, "Snap"), new Action("Death Roll", new Debuff(0,1,0,0)), 1
        );
        Mob crocodile = new Mob(
            "Crocodile", 120,
            new Action(20, "Snap"), new Action("Death Roll", new Debuff(0,2,0,0)), 2
        );

        // tier 3
        Mob troll = new Mob(
            "Troll", 150,
            new Action(30, "Bludgeon", new Debuff(0,1,0,0)), new Action("Roar", new Debuff(2,0,1,0)), 3
        );
        Mob wizard = new Mob(
            "Wizard", 100,
            new Action(10, "Fireball", new Debuff(0,0,0,1)), new Action(20, "Whack"), 4
        );

        Player player = new Player(100, new Tier(1, imp, elf), new Tier(2, alligator, crocodile), new Tier(3, troll, wizard));
        //Introduction

        System.out.println("\nYou wake up in a dimly lit room with nothing but a map. You see light coming from 4 doorways. To save your life, you must find a way to escape the...");
        System.out.println(
                " ______            _        _______  _______  _______  _       \n" +
                "(  __  \\ |\\     /|( (    /|(  ____ \\(  ____ \\(  ___  )( (    /|\n" +
                "| (  \\  )| )   ( ||  \\  ( || (    \\/| (    \\/| (   ) ||  \\  ( |\n" +
                "| |   ) || |   | ||   \\ | || |      | (__    | |   | ||   \\ | |\n" +
                "| |   | || |   | || (\\ \\) || | ____ |  __)   | |   | || (\\ \\) |\n" +
                "| |   ) || |   | || | \\   || | \\_  )| (      | |   | || | \\   |\n" +
                "| (__/  )| (___) || )  \\  || (___) || (____/\\| (___) || )  \\  |\n" +
                "(______/ (_______)|/    )_)(_______)(_______/(_______)|/    )_)\n" +
                "                                                               ");

        while (!player.gameOver)
        {
            player.getDirection();
            player.fightEnemy();
        }
    }
}
