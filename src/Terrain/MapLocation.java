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

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public int getHeight() {
        return this.height;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapLocation that = (MapLocation) o;

        if (column != that.column) return false;
        if (row != that.row) return false;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        int result = column;
        result = 31 * result + row;
        result = 31 * result + height;
        return result;
    }
}
