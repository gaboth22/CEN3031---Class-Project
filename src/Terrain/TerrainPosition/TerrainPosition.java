package Terrain.TerrainPosition;

import Terrain.TerrainLocation.TerrainLocation;
import java.security.InvalidParameterException;

public class TerrainPosition {
    private TerrainLocation location;
    private int height;

    public TerrainPosition(TerrainLocation location, int height) {
        if(height < 0)
            throw new InvalidParameterException("TerrainPosition height can never be < 0, and it was set to: " + height);

        this.location = location;
        this.height = height;
    }

    public int getRow() {
        return location.getX();
    }

    public int getCol() {
        return location.getY();
    }

    public int getHeight() {
        return height;
    }
}
