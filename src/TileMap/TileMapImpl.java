package TileMap;

import Terrain.TerrainPosition.TerrainPosition;
import Tile.Tile.Tile;

import java.util.Map;

public class TileMapImpl implements TileMap {
    private Map<TerrainPosition, Tile> mapOfTiles;

    @Override
    public void insertTile(Tile tile) throws LocationOccupiedException {
    }
}
