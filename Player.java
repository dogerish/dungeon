import java.util.Scanner;

public class Player {
    Scanner sc;
    boolean gameOver = false;
    int health;
    Map map = new Map(4, 4, "32  ;21  ;1012; 2 3;");
    int maxHealth = 100;
    Debuff debuff = new Debuff();
    int numScales = 0, numTeeth = 0, numClubs = 0, numWands = 0;
    int gold = 0;
    int attack = 20;
    boolean cleansed = false;
    int heal = 40;

    public Player(int health, Tier tier1, Tier tier2, Tier tier3)
    {
        sc = new Scanner(System.in);
        map.tiers[0] = tier1;
        map.tiers[1] = tier2;
        map.tiers[2] = tier3;
        this.health = health;
    }

    public int getMaxHealth() { return maxHealth + numScales*20; }

    public int getAttack() { return attack + numTeeth*10 + numClubs*20; }

    public int getHeal() { return heal + numWands*20; }

    public void getDirection()
    {
        // print map and directions
        System.out.println(map.toString());
        System.out.println("You can go any of the following directions: ");
        if (map.hasUp())    System.out.println("up");
        if (map.hasDown())  System.out.println("down");
        if (map.hasLeft())  System.out.println("left");
        if (map.hasRight()) System.out.println("right");

        boolean valid = false;
        while (!valid)
        {
            System.out.print("Where will you go? ");
            String direction = sc.nextLine();
            switch (direction)
            {
                case "up":    valid = map.goUp();    break;
                case "down":  valid = map.goDown();  break;
                case "left":  valid = map.goLeft();  break;
                case "right": valid = map.goRight(); break;
                default: break;
            }
        }
    }

    public void fightEnemy()
    {
        Mob enemy = null;
        if (map.getRoom() != 0) enemy = map.spawnMob();
        if (enemy == null) return;
        // fight the enemy
        while (this.health > 0)
        {
            System.out.println("-".repeat(20));
            printStats(enemy);
            playerTurn(enemy);
            //player turn - 3 action options
            if (enemy.health <= 0) break;
            //mob turn
            enemy.attack(this);
        } 
        fightResult(enemy);
        System.out.println("-".repeat(40));
    }

    public void playerTurn(Mob enemy)
    {
        boolean valid = false;
        while (!valid)
        {
            if (debuff.stunned > 0) System.out.print("Do you want to heal or cleanse? ");
            else                    System.out.print("Do you want to heal, cleanse, or attack? ");
            String input = sc.nextLine();
            cleansed = false;
            if (input.equals("heal"))
            {
                valid = true;
                health += getHeal();
                health = Math.min(health, getMaxHealth());
                System.out.println("You healed to " + health + " health!");
            }
            else if (input.equals("cleanse"))
            {
                valid = true; 
                debuff = new Debuff();
                cleansed = true;
                System.out.println("You are cleansed from all debuffs for 1 turn!");
            }
            else if (input.equals("attack"))
            {
                if (debuff.stunned > 0)
                {
                    System.out.println("You can't attack now. You are stunned.");
                    continue;
                }
                valid = true;
                int dmg = getAttack() - ((debuff.weak > 0) ? debuff.weak*10 : 0);
                enemy.health -= dmg;
                System.out.println("You attacked and did " + dmg + " damage!");
            }
            else if (input.equals("42069noscopeultrakill"))
            {
                enemy.health = 0;
                valid = true;
            }
        }
        if (debuff.stunned > 0) debuff.stunned--;
    }

    public void printStats(Mob enemy)
    {
        // print health, enemy health, and debuffs
        System.out.println(
            "You have " + health + " health, and "
            + enemy.name + " has " + enemy.health + " health."
        );
        if (debuff.vulnerable > 0) System.out.println("You are vulnerable for " + debuff.vulnerable + " turns.");
        if (debuff.stunned > 0) System.out.println("You are stunned for " + debuff.stunned + " turns.");
        if (debuff.weak > 0) System.out.println("You have " + debuff.weak + " stack(s) of weakness.");
        if (debuff.ablaze > 0) System.out.println("You have " + debuff.ablaze + " stack(s) of fire.");
    }

    public void fightResult(Mob enemy)
    {
        // player died
        if (health <= 0)
        {
            System.out.println("You died to " + enemy.name + " with " + gold + " gold. Game over.");
            gameOver = true;
        }
        //prints inventory on winning a fight
        else 
        {
            System.out.println("You beat " + enemy.name + "! You now have " + (gold += map.getRoom()) + " gold.");
            if (enemy.drop == 1 && (Math.random() > 0.2))
            {
                System.out.println("It dropped a tooth!");
                numTeeth++;
                System.out.println("You now have " + numTeeth + " teeth, and your attack damage is " + getAttack() + ".");
            }
            if (enemy.drop == 2 && (Math.random() > 0.2))
            {
                System.out.println("It dropped a scale!");
                numScales++;
                health += 20;
                System.out.println("You now have " + numScales + " scale(s), and your max health is " + getMaxHealth() + ".");
            }
                        /*if (numTeeth != 0 || numScales != 0)
                        {
                            System.out.println("You have " + numScales + " scale(s) and " + numTeeth + " teeth.");
                            System.out.println("Your max health is " + getMaxHealth() + " and your attack damage is " + getAttack() + ".");
                        }*/
            if (enemy.drop == 3 && (numClubs == 0))
            {
                System.out.println("It dropped a club!");
                numClubs++;
                System.out.println("You now have a club, and your attack damage is " + getAttack() + ".");
            }
            if (enemy.drop == 4 && (numWands == 0))
            {
                System.out.println("It dropped a wand!");
                numWands++;
                System.out.println("You now have a wand, and you heal for 60 health.");
            }
        }
        //Checks if you have enough gold upon completion of an exit room (tier 3)
        if (map.getRoom() == map.maxTier)
        {
            if (gold >= map.goldNecessary) {
                boolean valid = false;
                while (!valid) {
                    System.out.print("Would you like to exit the dungeon? (yes/no) ");
                    String input = sc.nextLine();
                    if (input.equals("yes")) {
                        System.out.println("Congratulations! You won with " + gold + " gold!\nThanks for playing!");
                        valid = true;
                        gameOver = true;
                    } else if (input.equals("no")) {
                        System.out.println("Have fun! ");
                        valid = true;
                    }
                }
            }
            else System.out.println("You need " + map.goldNecessary + " gold to exit the Dungeon.");
        }
    }
}