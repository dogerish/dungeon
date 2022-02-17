public class Map {
    int[] data;
    int index;
    int w, h;
    static final int BLANKTIER = -1;
    int maxTier = 3;
    Tier tiers[] = new Tier[maxTier];

    public Map(int w, int h, String layout)
    {
        this.w = w;
        this.h = h;
        data = new int[w*h];
        int lastIndex = -1;
        for (int y = 0; y < h; y++)
        {
            String line = layout.substring(lastIndex + 1, lastIndex = layout.indexOf(';', lastIndex + 1));
            System.out.println(line);
            for (int x = 0; x < w; x++)
            {
                if (line.charAt(x) == ' ') data[y*w + x] = BLANKTIER;
                else data[y*w + x] = Integer.parseInt(line.substring(x, x + 1));
                if (line.charAt(x) == '0') index = y*w + x;
            }
        }
    }

    public String toString()
    {
        String result = "-".repeat(9) + "\n";
        for (int i = 0; i < data.length; i++)
        {
            result += "|";
            if      (i == index)           result += "*";
            else if (data[i] == BLANKTIER) result += " ";
            else                           result += data[i];
            if (i % w == w - 1)            result += "|\n";
        }
        result += "-".repeat(9);
        return result;
    }

    public int getRoom() { return data[index]; }
    public Mob spawnMob() { return tiers[getRoom() - 1].spawnMob(); }

    // returns true if the room is extant
    public boolean hasUp()    { return (index > w - 1)       && data[index - w] != BLANKTIER; }
    public boolean hasDown()  { return (index < w * (h - 1)) && data[index + w] != BLANKTIER; }
    public boolean hasLeft()  { return (index % w != 0)      && data[index - 1] != BLANKTIER; }
    public boolean hasRight() { return (index % w != w - 1)  && data[index + 1] != BLANKTIER; }

    // goes the direction if possible
    public boolean goUp()    { return hasUp()    && (index -= w) != -1; }
    public boolean goDown()  { return hasDown()  && (index += w) != -1; }
    public boolean goLeft()  { return hasLeft()  && (index -= 1) != -1; }
    public boolean goRight() { return hasRight() && (index += 1) != -1; }
}
