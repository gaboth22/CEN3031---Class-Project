package Terrain;

public class MapLocation extends TerrainLocation {

    public MapLocation(int row, int column, int height) {
        super(row, column, height);
    }

    public MapLocation up() {
        return new MapLocation(getRow() + 1, getColumn(), getHeight());
    }

    public MapLocation upRight() {
        return new MapLocation(getRow(), getColumn() + 1, getHeight());
    }

    public MapLocation downRight() {
        return new MapLocation(getRow() - 1, getColumn() + 1, getHeight());
    }

    public MapLocation down() {
        return new MapLocation(getRow() - 1, getColumn(), getHeight());
    }

    public MapLocation downLeft() {
        return new MapLocation(getRow(), getColumn() - 1, getHeight());
    }

    public MapLocation upLeft() {
        return new MapLocation(getRow() + 1, getColumn() - 1, getHeight());
    }

    public MapLocation above() {
        return new MapLocation(getRow(), getColumn(), getHeight() + 1);
    }

    public MapLocation below() {
        return new MapLocation(getRow(), getColumn(), getHeight() - 1);
    }

}
