package TileBuilder;

import Location.Location;
import Movement.*;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;

import java.util.Arrays;

public class TileBuilder {
    private Movement movementGenerator;

    public TileBuilder() {
        movementGenerator = new AxialMovement();
    }

    public Tile getTileAtOrigin() {
        Terrain[] terrains = {Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.JUNGLE};
        Location volcanoLocation = new Location(0, 0);
        Location[] locations = {volcanoLocation,
                                movementGenerator.up(volcanoLocation),
                                movementGenerator.upRight(volcanoLocation)};

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    public Tile getAdjacentTile(Tile tile) {
        Location[] locations = tile.getArrayOfTerrainLocations();
        Location volcanoLocation = movementGenerator.up(locations[2]);
        Location[] newLocations = {volcanoLocation,
                                   movementGenerator.up(volcanoLocation),
                                   movementGenerator.upRight(volcanoLocation)};
        Terrain[] newTerrains = {Terrain.VOLCANO, Terrain.LAKE, Terrain.ROCKY};

        return new TileImpl(Arrays.asList(newTerrains), Arrays.asList(newLocations));
    }

    public Tile getTileWithLocations(Location l1, Location l2, Location l3) {
        Location[] locations = {l1, l2, l3};
        Terrain[] terrains = {Terrain.VOLCANO, Terrain.ROCKY, Terrain.GRASSLANDS};

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

}
