package GamePieceMap;

import java.util.HashMap;
import Location.Location;
import Player.PlayerID;

public class GamePieceMap {

    private HashMap<Location, GamePiece> mapContainingPiecesAtLocations;

    public GamePieceMap(){
        mapContainingPiecesAtLocations = new HashMap<Location, GamePiece>();
    }

    public boolean isThereAPieceAt(Location location){
        return mapContainingPiecesAtLocations.containsKey(location);
    }

    public void insertAPieceAt(Location location, GamePiece gamePiece) throws LocationNotEmptyException {
        if(!isThereAPieceAt(location))
            mapContainingPiecesAtLocations.put(location, gamePiece);
        else
            throw new LocationNotEmptyException();
    }

    public void removeAPieceAt(Location location){
        if(isThereAPieceAt(location))
            mapContainingPiecesAtLocations.remove(location);
    }

    public TypeOfPiece getPieceTypeAtLocation(Location location){
        GamePiece gamePiece = mapContainingPiecesAtLocations.get(location);
        return gamePiece.getPieceType();
    }

    public PlayerID getPieceOwnerIdAtLocation(Location location){
        GamePiece gamePiece = mapContainingPiecesAtLocations.get(location);
        return gamePiece.getPlayer();
    }

    public GamePiece getPieceAtLocation(Location location){
        GamePiece gamePiece = mapContainingPiecesAtLocations.get(location);
        return gamePiece;
    }

}