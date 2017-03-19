package TileMap;

import Terrain.TerrainLocation.TerrainLocation;
import Terrain.Terrain.Terrain;

public class Hexagon {
    private int parentTileID;
    private TerrainLocation location;
    private int height;
    private Terrain terrain;

    public int getParentTileID() {
        return parentTileID;
    }

    public TerrainLocation getTerrainLocation() {
        return location;
    }

    public int getHeight() {
        return height;
    }

    public Terrain getTerrain() {
        return terrain;
    }

}
