package Terrain;

public abstract class TerrainLocation {
    private int row;
    private int column;
    private int height;

    public TerrainLocation(int row, int column, int height) {
        this.row = row;
        this.column = column;
        this.height = height;
    }

    int getRow(){
        return this.row;
    }

    int getColumn(){
        return this.column;
    }

    int getHeight(){
        return this.height;
    }

    public abstract TerrainLocation up();
    public abstract TerrainLocation upRight();
    public abstract TerrainLocation downRight();
    public abstract TerrainLocation down();
    public abstract TerrainLocation downLeft();
    public abstract TerrainLocation upLeft();
    public abstract TerrainLocation above();
    public abstract TerrainLocation below();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TerrainLocation that = (TerrainLocation) o;

        if (row != that.row) return false;
        if (column != that.column) return false;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        result = 31 * result + height;
        return result;
    }


}
