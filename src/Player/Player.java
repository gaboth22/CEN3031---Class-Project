package Player;

public class Player {
    private static final int MAX_MEEPLE_COUNT = 20;
    private static final int MAX_TOTORO_COUNT = 3;
    private PlayerID id;
    private int meeples;
    private int totoros;

    public Player(PlayerID id) {
        this.id = id;
        this.meeples = new Integer(MAX_MEEPLE_COUNT);
        this.totoros= new Integer(MAX_TOTORO_COUNT);
    }

    public PlayerID getID() {
        return this.id;
    }

    public int getMeeple() {
        if(this.meeples > 0) {
            this.meeples--;
            return 1;
        }

        return -1;
    }

    public int getTotoro() {
        if(this.totoros > 0) {
            this.totoros--;
            return 1;
        }

        return -1;
    }
}
