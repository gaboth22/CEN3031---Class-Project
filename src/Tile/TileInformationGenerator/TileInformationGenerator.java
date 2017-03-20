package Tile.TileInformationGenerator;

import Terrain.Terrain.Terrain;
import Tile.TileOrientation.TileOrientation;

/*
 * The convention is that getRow() and getCol() will
 * return the row and column of the volcano
 *
 * The tile orientation is based on the orientation of the volcano, seen from a point
 * at the very center of the tile. So If the volcano has row 0, column 0, and orientation
 * DOWN then the tile's left (up right) terrain has row 0, column 1 and
 * the right (up) terrain has row 1, column 0
 */
public interface TileInformationGenerator {

    int getRow();
    int getCol();
    TileOrientation getOrientation();
    Terrain[] getTerrains();
}
