package GamePieceMap;

import java.util.HashMap;
import Location.Location;


public class GamePieceMap {

    private HashMap<Location, GamePiece> mapContainingPiecesAtLocations;

    public GamePieceMap(){
        mapContainingPiecesAtLocations = new HashMap<Location, GamePiece>();
    }

    private boolean isThereAPieceAt(Location location){
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

    public TypeOfPiece getPieceAtLocation(Location location){
        GamePiece gamePiece = mapContainingPiecesAtLocations.get(location);
        return gamePiece.getPieceType();
    }

    public PlayerID getPlayerAtLocation(Location location){
        GamePiece gamePiece = mapContainingPiecesAtLocations.get(location);
        return gamePiece.getPlayer();
    }
}