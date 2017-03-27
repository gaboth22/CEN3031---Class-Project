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
    private BuildType type;

    public BuildPhase(GamePiece gamePiece, Location locationToPlacePieceAt) {
        this.playerID = gamePiece.getPlayer();
        this.pieceToPlace = gamePiece.getPieceType();
        this.locationToPlacePieceAt = locationToPlacePieceAt;
        this.piece = gamePiece;
        this.type = null;
    }

    public void setBuildType(BuildType type){
        this.type = type;
    }

    public BuildType getBuildType(){
        if(type == null){
            throw new NullPointerException("Trying to access BuildType without setting first");
        }
        return type;
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