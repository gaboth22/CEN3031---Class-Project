package Tile.Tile;

import Terrain.Terrain.Terrain;
import Terrain.TerrainLocation.TerrainLocation;

public class TimeImpl implements Tile {
    @Override
    public Terrain[] getArrayOfTerrains() {
        return new Terrain[0];
    }

    @Override
    public TerrainLocation[] getArrayOfTerrainLocations() {
        return new TerrainLocation[0];
    }
}
