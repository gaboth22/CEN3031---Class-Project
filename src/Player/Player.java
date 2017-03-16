package Player;

import Piece.Meeple;
import Piece.Totoro;
import java.util.Stack;

public class Player {
    private static final int MAX_MEEPLE_COUNT = 20;
    private static final int MAX_TOTORO_COUNT = 3;
    private PlayerID id;
    private Stack<Piece.Meeple> meeples;
    private Stack<Piece.Totoro> totoros;

    public Player(PlayerID id) {
        this.id = id;
        this.meeples = new Stack<Meeple>();
        this.totoros= new Stack<Totoro>();

        for(int i = 0; i < this.MAX_MEEPLE_COUNT; i++) {
            this.meeples.push(new Piece.Meeple(this.id));
        }

        for(int i = 0; i < this.MAX_TOTORO_COUNT; i++) {
            this.totoros.push(new Totoro(this.id));
        }
    }

    public PlayerID getID() {
        return this.id;
    }

    public void getTotoro() {
        this.totoros.pop();
    }

    public void getMeeple() {
        this.meeples.pop();
    }
}
