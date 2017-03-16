package TerrainTest;

import Terrain.TerrainLocation;

public class TerrainLocationTestDouble extends TerrainLocation{
    private int row;
    private int col;
    private int height;

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int col) {
        this.col = col;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
