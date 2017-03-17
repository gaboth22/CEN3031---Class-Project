package TileMap;

import Terrain.TerrainLocation;
import Tile.*;
import Terrain.*;

import java.util.HashMap;
import java.util.List;

public class TileMap {
    List<Tile> tilesOnBoard;
    int numberOfTilesPlaced = 0;
    HashMap<TerrainLocation, Tile> board;

    public TileMap(){
        board = new HashMap<TerrainLocation, Tile>();
    }

    public void placeTile(Tile tileToPlace) throws LocationOccupiedException {
        throwExceptionIfLocationIsOccupied(tileToPlace);
        placeTileOnBoard(tileToPlace);
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
        numberOfTilesPlaced++;
    }

    public Tile getTileAtLocation(TerrainLocation location){
        return board.get(location);
    }


}
