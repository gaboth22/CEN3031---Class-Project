package TerrainTest;

import Terrain.TerrainLocation;

public class TerrainLocationTestDouble extends TerrainLocation{
    private int row;
    private int column;
    private int height;

    public TerrainLocationTestDouble(int row, int column, int height) {
        this.row = row;
        this.column = column;
        this.height = height;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TerrainLocationTestDouble up() {
        return new TerrainLocationTestDouble(row + 1, column, height);
    }

    public TerrainLocationTestDouble upRight() {
        return new TerrainLocationTestDouble(row, column + 1, height);
    }

    public TerrainLocationTestDouble downRight() {
        return new TerrainLocationTestDouble(row - 1, column + 1, height);
    }

    public TerrainLocationTestDouble down() {
        return new TerrainLocationTestDouble(row - 1, column, height);
    }

    public TerrainLocationTestDouble downLeft() {
        return new TerrainLocationTestDouble(row, column - 1, height);
    }

    public TerrainLocationTestDouble upLeft() {
        return new TerrainLocationTestDouble(row + 1, column - 1, height);
    }

    public TerrainLocationTestDouble above() {
        return new TerrainLocationTestDouble(row, column, height + 1);
    }

    public TerrainLocationTestDouble below() {
        return new TerrainLocationTestDouble(row, column, height - 1);
    }
}
