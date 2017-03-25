package Play.BuildPhase;

import GamePieceMap.TypeOfPiece;
import Player.PlayerID;
import Location.Location;

public class BuildPhase {
    final private PlayerID playerID;
    final private TypeOfPiece pieceToPlace;
    final private Location locationToPlacePieceAt;

    public BuildPhase(PlayerID playerID, TypeOfPiece pieceToPlace, Location locationToPlacePieceAt) {
        this.playerID = playerID;
        this.pieceToPlace = pieceToPlace;
        this.locationToPlacePieceAt = locationToPlacePieceAt;
    }

    public PlayerID getPlayerID() {
        return playerID;
    }

    public TypeOfPiece getPieceToPlace() {
        return pieceToPlace;
    }

    public Location getLocationToPlacePieceAt() {
        return locationToPlacePieceAt;
    }

}