package Terrain.TerrainMovement;

import Terrain.TerrainLocation.TerrainLocation;

public interface TerrainMovement {

    TerrainLocation up(TerrainLocation loc);
    TerrainLocation upRight(TerrainLocation loc);
    TerrainLocation downRight(TerrainLocation loc);
    TerrainLocation down(TerrainLocation loc);
    TerrainLocation downLeft(TerrainLocation loc);
    TerrainLocation upLeft(TerrainLocation loc);
}
