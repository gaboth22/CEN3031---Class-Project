package Tile.Tile;

import Location.Location;
import Terrain.Terrain.Terrain;

public interface Tile {
    Location[] getArrayOfTerrainLocations();

    Terrain[] getArrayOfTerrains();
}
