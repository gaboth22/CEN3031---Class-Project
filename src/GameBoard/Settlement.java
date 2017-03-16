package GameBoard;

import Terrain.Terrain;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Settlement implements Iterable<Terrain> {
    private List<Terrain> settlement;
    private boolean hasTotoro;

    public Settlement() {
        this.settlement = new LinkedList<Terrain>();
    }

    public void append(Terrain terrain) {
        this.settlement.add(terrain);
    }

    public void size() {
        this.settlement.size();
    }

    public boolean hasTotoro() {
        return this.hasTotoro;
    }

    public void setTotoroAdded() {
        this.hasTotoro = true;
    }

    public void clearTotoroAdded() {
        this.hasTotoro = false;
    }

    public Iterator<Terrain> iterator() {
        return new SettlementIterator(this.settlement);
    }
}
