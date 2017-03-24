package Player;

public class Player {
    private static final int MAX_MEEPLE_COUNT = 20;
    private static final int MAX_TOTORO_COUNT = 3;
    private static final int MAX_TIGER_COUNT = 2;
    private PlayerID id;
    private int meeples;
    private int totoros;
    private int tigers;

    public Player(PlayerID id) {
        this.id = id;
        this.meeples = new Integer(MAX_MEEPLE_COUNT);
        this.totoros= new Integer(MAX_TOTORO_COUNT);
        this.tigers = new Integer(MAX_TIGER_COUNT);
    }

    public PlayerID getID() {
        return this.id;
    }

    public void decrimentMeeples() {
        this.meeples--;
    }

    public void decrimentTotoros() {
            this.totoros--;
    }

    public void decrimentTigers() {
            this.tigers--;
    }

    public int getMeeples(){
        return meeples;
    }

    public int getTotoros(){
        return totoros;
    }

    public int getTigers(){
        return tigers;
    }
}
