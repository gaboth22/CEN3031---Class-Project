package Play.BuildPhase;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Player.PlayerID;
import Location.Location;
import Settlements.Creation.Settlement;

public class BuildPhase {
    final private PlayerID playerID;
    final private TypeOfPiece pieceToPlace;
    final private Location locationToPlacePieceAt;
    final private GamePiece piece;
    private BuildType type;
    private Settlement settlement;

    public BuildPhase(GamePiece gamePiece, Location locationToPlacePieceAt) {
        this.playerID = gamePiece.getPlayer();
        this.pieceToPlace = gamePiece.getPieceType();
        this.locationToPlacePieceAt = locationToPlacePieceAt;
        this.piece = gamePiece;
        this.type = null;
        this.settlement = null;
    }

    public BuildPhase(GamePiece gamePiece, Location locationOfTerrainToExpand, Settlement settlement) {
        this.playerID = gamePiece.getPlayer();
        this.pieceToPlace = gamePiece.getPieceType();
        this.locationToPlacePieceAt = locationOfTerrainToExpand;
        this.piece = gamePiece;
        this.type = null;
        this.settlement = settlement;
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

    public Settlement getSettlement(){
        if(settlement == null){
            throw new NullPointerException("Trying to access settlement without setting first (Wrong constructor called)");
        }
        return settlement;
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