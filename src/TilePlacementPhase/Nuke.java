package TilePlacementPhase;

import GameBoard.GameBoard;
import Tile.TileLocation;
import Tile.Tile;

public class Nuke extends TilePlacementPhase {
    private TileLocation location;
    private Tile tile;
    private GameBoard gameBoard;

    public Nuke(Tile tile, TileLocation location) {
        this.location = location;
        this.tile = tile;
    }

    public void place(){
        this.gameBoard.nukeTiles(this.tile, this.location);
    }
}