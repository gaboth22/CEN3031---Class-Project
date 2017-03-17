package Terrain;

public class MapLocation extends TerrainLocation {
    private int column;
    private int row;
    private int height;

    public MapLocation(int row, int column, int height){
        this.row = row;
        this.column = column;
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
