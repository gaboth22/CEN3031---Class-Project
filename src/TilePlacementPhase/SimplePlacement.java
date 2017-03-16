package TilePlacementPhase;

import GameBoard.GameBoard;
import Tile.Tile;
import Tile.TileLocation;

public class SimplePlacement extends TilePlacementPhase {
    private Tile tile;
    private TileLocation location;
    private GameBoard gameBoard;

    public SimplePlacement(Tile tile, TileLocation location) {
        this.location = location;
        this.tile = tile;
    }

    public void place(){
        this.gameBoard.insertTile(this.tile, this.location);
    }

}
