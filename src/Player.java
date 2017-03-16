import java.util.Stack;

public class Player {
    static final int MAX_MEEPLE_COUNT = 20;
    static final int MAX_TOTORO_COUNT = 3;
    public PlayerID id;
    private Stack<Meeple> meeples;
    private Stack<Totoro> totoros;

    public Player(PlayerID id) {
        this.id = id;
        this.meeples = new Stack<Meeple>();
        this.totoros= new Stack<Totoro>();
        for(int i=0; i < this.MAX_MEEPLE_COUNT; i++) {
            this.meeples.push(new Meeple(this));
        }

        for(int i=0; i < this.MAX_TOTORO_COUNT; i++) {
            this.totoros.push(new Totoro(this));
        }
    }

    public void getTotoro() {
        this.totoros.pop();
    }

    public void getMeeple() {
        this.meeples.pop();
    }
}
