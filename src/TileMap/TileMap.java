package TileMap;

import Tile.Tile.Tile;

public interface TileMap {

    void insertTile(Tile tile) throws LocationOccupiedException;
}
