package GameBoard;

import Terrain.Terrain;
import java.util.Iterator;
import java.util.List;

public class SettlementIterator implements Iterator<Terrain> {
    private List<Terrain> list;
    private int index;

    public SettlementIterator(List<Terrain> settlement) {
        this.index = 0;
        this.list = settlement;
    }

    public boolean hasNext() {
        return (this.index < list.size());
    }

    public Terrain next() {
        Terrain terrain = this.list.get(index);
        this.index++;
        return terrain;
    }
}
