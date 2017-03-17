package TerrainTest;

import Terrain.MapLocation;
import Terrain.TerrainLocation;

public class TerrainLocationTestDouble extends TerrainLocation{
    private int row;
    private int column;
    private int height;

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MapLocation up() {
        return new MapLocation(row + 1, column, height);
    }

    public MapLocation upRight() {
        return new MapLocation(row, column + 1, height);
    }

    public MapLocation downRight() {
        return new MapLocation(row - 1, column + 1, height);
    }

    public MapLocation down() {
        return new MapLocation(row - 1, column, height);
    }

    public MapLocation downLeft() {
        return new MapLocation(row, column - 1, height);
    }

    public MapLocation upLeft() {
        return new MapLocation(row + 1, column - 1, height);
    }

    public MapLocation above() {
        return new MapLocation(row, column, height + 1);
    }

    public MapLocation below() {
        return new MapLocation(row, column, height - 1);
    }
}
