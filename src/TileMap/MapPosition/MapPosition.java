package TileMap.MapPosition;

import Terrain.TerrainPosition.TerrainPosition;
import TileMap.TileMap;

public class MapPosition {
    private int row;
    private int col;
    private int height;

    public MapPosition(TerrainPosition position) {
        row = position.getRow();
        col = position.getCol();
        height = position.getHeight();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getHeight() {
        return height;
    }

    public void resetPosition(TerrainPosition newPosition) {
        row = newPosition.getRow();
        col = newPosition.getCol();
        height = newPosition.getHeight();
    }
}
