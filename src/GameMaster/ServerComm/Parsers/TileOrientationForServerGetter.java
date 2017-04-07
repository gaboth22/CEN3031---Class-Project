package GameMaster.ServerComm.Parsers;

import Location.Location;
import Movement.*;
import Tile.Tile.Tile;

import java.security.InvalidParameterException;

public class TileOrientationForServerGetter {

    public static int getOrientation(Tile tile) {
        Movement mov = new AxialMovement();
        Location[] locs = tile.getArrayOfTerrainLocations();
        Location left = locs[1];
        Location right = locs[2];
        Location volc = locs[0];

        if(mov.upRight(volc).equals(left) && mov.downRight(volc).equals(right))
            return 1;

        else if(mov.downRight(volc).equals(left) && mov.down(volc).equals(right))
            return 2;

        else if(mov.down(volc).equals(left) && mov.downLeft(volc).equals(right))
            return 3;

        else if(mov.downLeft(volc).equals(left) && mov.upLeft(volc).equals(right))
            return 4;

        else if(mov.upLeft(volc).equals(left) && mov.up(volc).equals(right))
            return 5;

        else if(mov.up(volc).equals(left) && mov.upRight(volc).equals(right))
            return 6;

        else
            throw new InvalidParameterException("Cannot get orientation of tile");
    }
}
