package GameBoard;

import Piece.*;
import Terrain.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Settlement implements Iterable<Terrain> {
    private List<Terrain> settlement;
    private boolean hasTotoro;

    public Settlement() {
        this.settlement = new LinkedList<Terrain>();
        this.hasTotoro = false;
    }

    public void append(Terrain terrain) {
        Piece piece = null;

        try {
            piece = terrain.getPiece();
        }

        catch (EmptyTerrainException e) {
        }

        if(piece instanceof Totoro)
            this.hasTotoro = true;

        this.settlement.add(terrain);
    }

    public int size() {
        return this.settlement.size();
    }

    public boolean hasTotoro() {
        return this.hasTotoro;
    }

    public void clearTotoroAdded() {
        this.hasTotoro = false;
    }

    public Iterator<Terrain> iterator() {
        return new SettlementIterator(this.settlement);
    }
}