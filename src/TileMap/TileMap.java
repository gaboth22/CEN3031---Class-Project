package TileMap;

import Terrain.TerrainLocation.TerrainLocation;
import Tile.Tile.Tile;

import java.util.List;

public interface TileMap {

    void insertTile(Tile tile) throws LocationOccupiedException;
    Hexagon getHexagonAt(TerrainLocation location);

    /*
     * Returns a projection of all the hexagons at the top of each location in the map.
     * This could be used for meepleMap validation, for gui printing purposes, etc.
     */
    List<Hexagon> getAllTopHexagons();

}
