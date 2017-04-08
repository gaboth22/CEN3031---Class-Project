package Tile.TileInformationGenerator;

import Location.Location;
import Terrain.Terrain.Terrain;

/*
 * The tile orientation is based on the orientation of the volcano, seen from a point
 * at the very center of the tile. So If the volcano has row 0, column 0, and orientation
 * DOWN then the tile's left (up right) terrain has row 0, column 1 and
 * the right (up) terrain has row 1, column 0
 */
public interface TileInformationGenerator {

    Location[] getLocations();
    Terrain[] getTerrains();
}
