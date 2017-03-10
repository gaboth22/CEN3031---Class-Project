/*
 *  The location is the location of the tile in regard to its volcano.
 *  In other words, the location of the volcano
 *  the tile can be reconstructed from there using the leftTerrain
 *  rightTerrain convention.
 */

public class TileLocation {
    public int column;
    public int row;

    public TileLocation(int column, int row) {
        this.column = column;
        this.row = row;
    }
}
