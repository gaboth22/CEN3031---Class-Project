package Tile.TileFactory;

public class ImpossibleTileException extends Exception {
    public ImpossibleTileException() {
    }

    public ImpossibleTileException(String message) {
        super(message);
    }
}
