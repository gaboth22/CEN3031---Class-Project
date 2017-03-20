package Terrain.TerrainMovement;

import Terrain.TerrainLocation.TerrainLocation;

public class AxialMovement implements TerrainMovement {

    @Override
    public TerrainLocation up(TerrainLocation loc) {
        return new TerrainLocation(loc.getX(), loc.getY() + 1);
    }

    @Override
    public TerrainLocation upRight(TerrainLocation loc) {
        return new TerrainLocation(loc.getX() + 1, loc.getY());
    }

    @Override
    public TerrainLocation downRight(TerrainLocation loc) {
        return new TerrainLocation(loc.getX() + 1, loc.getY() - 1);
    }

    @Override
    public TerrainLocation down(TerrainLocation loc) {
        return new TerrainLocation(loc.getX(), loc.getY() - 1);
    }

    @Override
    public TerrainLocation downLeft(TerrainLocation loc) {
        return new TerrainLocation(loc.getX() - 1, loc.getY());
    }

    @Override
    public TerrainLocation upLeft(TerrainLocation loc) {
        return new TerrainLocation(loc.getX() - 1, loc.getY() + 1);
    }
}
