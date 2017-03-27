package Play.TilePlacementPhase;

import Player.PlayerID;
import Tile.Tile.Tile;

public class TilePlacementPhase {
    final private PlayerID playerID;
    final private Tile tileToPlace;
    private TilePlacementType type;

    public TilePlacementPhase(PlayerID playerID, Tile tileToPlace) {
        this.playerID = playerID;
        this.tileToPlace = tileToPlace;
        this.type = null;
    }

    public void setTilePlacementType(TilePlacementType type){
        this.type = type;
    }

    public TilePlacementType getTilePlacementType(){
        if(type == null){
            throw new NullPointerException("Trying to access TilePlacementType without setting value");
        }
        return type;
    }

    public PlayerID getPlayerID() {
        return playerID;
    }

    public Tile getTileToPlace() {
        return tileToPlace;
    }
}