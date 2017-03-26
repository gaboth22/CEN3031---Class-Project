package Play.BuildPhase;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Player.PlayerID;
import Location.Location;

public class BuildPhase {
    final private PlayerID playerID;
    final private TypeOfPiece pieceToPlace;
    final private Location locationToPlacePieceAt;
    final private GamePiece piece;

    public BuildPhase(GamePiece gamePiece, Location locationToPlacePieceAt) {
        this.playerID = gamePiece.getPlayer();
        this.pieceToPlace = gamePiece.getPieceType();
        this.locationToPlacePieceAt = locationToPlacePieceAt;
        this.piece = gamePiece;
    }

    public PlayerID getPlayerID() {
        return playerID;
    }

    public TypeOfPiece getTypeOfPieceToPlace() {
        return pieceToPlace;
    }

    public Location getLocationToPlacePieceOn() {
        return locationToPlacePieceAt;
    }

    public GamePiece getGamePiece() {
        return piece;
    }

}