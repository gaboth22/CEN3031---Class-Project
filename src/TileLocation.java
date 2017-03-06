/*
 *  The location is the location of the tile containing a volcano,
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
