package Tile;

import Terrain.Terrain;

public interface TileInformationGenerator {

    Terrain getVolcano();

    Terrain getLeftTerrain();

    Terrain getRightTerrain();

    TileLocation getTileLocation();

    TileOrientation getOrientation();
}
