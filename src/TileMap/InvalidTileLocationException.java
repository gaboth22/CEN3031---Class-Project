package TileMap;

public class InvalidTileLocationException extends Exception {
    public InvalidTileLocationException() {
    }

    public InvalidTileLocationException(String message) {
        super(message);
    }
}
