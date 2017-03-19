package Terrain.TerrainMovement;

import Terrain.TerrainLocation.TerrainLocation;

public class AxialMovement implements TerrainMovement {

    @Override
    public TerrainLocation up(TerrainLocation loc) {
        return new TerrainLocation(loc.getRow() + 1, loc.getCol());
    }

    @Override
    public TerrainLocation upRight(TerrainLocation loc) {
        return null;
    }

    @Override
    public TerrainLocation downRight(TerrainLocation loc) {
        return null;
    }

    @Override
    public TerrainLocation down(TerrainLocation loc) {
        return null;
    }

    @Override
    public TerrainLocation downLeft(TerrainLocation loc) {
        return null;
    }

    @Override
    public TerrainLocation upLeft(TerrainLocation loc) {
        return null;
    }
}
