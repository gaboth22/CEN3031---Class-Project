package TileMap;

import Terrain.TerrainLocation;
import Tile.*;
import Terrain.*;

import java.util.HashMap;
import java.util.List;

public class TileMap {
    List<Tile> tilesOnBoard;
    HashMap<TerrainLocation, Tile> board;

    public TileMap(){
        board = new HashMap<TerrainLocation, Tile>();
    }

    public void placeTile(Tile tile) throws LocationOccupiedException {
        throwExceptionIfLocationIsOccupied(tile);
        placeTileOnBoard(tile);
    }

    private void throwExceptionIfLocationIsOccupied(Tile tile) throws LocationOccupiedException {
        List<Terrain> terrainsInTile = tile.getListOfTerrains();
        for(int i = 0; i < terrainsInTile.size(); i++) {
            Terrain currentTerrain = terrainsInTile.get(i);
            if(board.containsKey(currentTerrain.getLocation())){
                throw new LocationOccupiedException();
            }
        }
    }

    private void placeTileOnBoard(Tile tile) {
        List<Terrain> terrainsInTile = tile.getListOfTerrains();
        for(int i = 0; i < terrainsInTile.size(); i++) {
            Terrain currentTerrain = terrainsInTile.get(i);
            board.put(currentTerrain.getLocation(), tile);
        }
    }

    public Tile getTileAtLocation(TerrainLocation location){
        return board.get(location);
    }


}
