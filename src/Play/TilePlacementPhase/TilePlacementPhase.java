package Play.TilePlacementPhase;

import Player.PlayerID;
import Tile.Tile.Tile;

public class TilePlacementPhase {
    final private PlayerID playerID;
    final private Tile tileToPlace;

    public TilePlacementPhase(PlayerID playerID, Tile tileToPlace) {
        this.playerID = playerID;
        this.tileToPlace = tileToPlace;
    }

    public PlayerID getPlayerID() {
        return playerID;
    }

    public Tile getTileToPlace() {
        return tileToPlace;
    }
}