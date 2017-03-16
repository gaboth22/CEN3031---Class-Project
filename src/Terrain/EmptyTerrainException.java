package Terrain;

public class EmptyTerrainException extends Exception {
    public EmptyTerrainException() {
    }

    public EmptyTerrainException(String message) {
        super(message);
    }
}
