package Tile.Tile;

import Terrain.TerrainLocation.TerrainLocation;
import Terrain.Terrain.Terrain;

public interface Tile {
    TerrainLocation[] getArrayOfTerrainLocations();

    Terrain[] getArrayOfTerrains();
}
