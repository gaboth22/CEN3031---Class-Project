package TileMap;

import Terrain.TerrainLocation.TerrainLocation;
import Tile.Tile.Tile;

import java.util.List;

public interface TileMap {

    void insertTile(Tile tile) throws LocationOccupiedException;
}
