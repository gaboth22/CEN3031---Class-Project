package TileMap.MapPosition;

import Position.Position;

public class MapPosition {
    private int row;
    private int col;
    private int height;

    public MapPosition(Position position) {
        row = position.getX();
        col = position.getY();
        height = position.getHeight();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getHeight() {
        return height;
    }

    public void resetPosition(Position newPosition) {
        row = newPosition.getX();
        col = newPosition.getY();
        height = newPosition.getHeight();
    }
}
