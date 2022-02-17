public class Debuff {
    int vulnerable, stunned, weak, ablaze;
    
    public Debuff(int vulnerable, int stunned, int weak, int ablaze)
    {
        this.vulnerable = vulnerable;
        this.stunned = stunned;
        this.weak = weak;
        this.ablaze = ablaze;
    }
    public Debuff()
    {
        this.vulnerable = 0;
        this.stunned = 0;
        this.weak = 0;
        this.ablaze = 0;
    }
}
