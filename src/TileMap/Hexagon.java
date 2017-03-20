package TileMap;

import Location.Location;
import Terrain.Terrain.Terrain;

public class Hexagon {
    private int parentTileID;
    private Location location;
    private int height;
    private Terrain terrain;

    public int getParentTileID() {
        return parentTileID;
    }

    public Location getTerrainLocation() {
        return location;
    }

    public int getHeight() {
        return height;
    }

    public Terrain getTerrain() {
        return terrain;
    }

}
