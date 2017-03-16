package TileMap;

import Terrain.TerrainLocation;
import Tile.*;
import Terrain.*;

import java.util.HashMap;
import java.util.List;

public class TileMap {
    List<Tile> tilesOnBoard;
    HashMap<TerrainLocation, Tile> board;

    public void placeTile(Tile tile) {
        List<Terrain> terrainsInTile = tile.getListOfTerrains();
    }


}
