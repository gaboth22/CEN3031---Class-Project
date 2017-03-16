package TerrainTest;

import Terrain.TerrainLocation;

public class TerrainLocationTestDouble implements TerrainLocation{
    private int row;
    private int col;
    private int height;

    public void setRow(int row) {
        this.row = row;
    }

    public void setColum(int col) {
        this.col = col;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColumn() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public int getHeight() {
        return this.height;
    }
}
