package GamePieceMap;

public class LocationNotEmptyException extends Exception {

    public LocationNotEmptyException(){
    }

    public LocationNotEmptyException(String message) {
        super(message);
    }
}