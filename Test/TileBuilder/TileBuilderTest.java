package TileBuilder;

import Location.Location;
import Movement.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Tile.Tile.Tile;

public class TileBuilderTest {
    private TileBuilder builder;
    private Movement locationGen;

    @Before
    public void initializeInstance() {
        builder = new TileBuilder();
        locationGen = new AxialMovement();
    }

    @Test
    public void shouldGetATerrainWithVolcanoAtOrigin() {
        Tile tile = builder.getTileAtOrigin();
        Location origin = new Location(0, 0);
        Location volcanoLocation = tile.getArrayOfTerrainLocations()[0];
        Assert.assertTrue(origin.equals(volcanoLocation));
    }

    @Test
    public void tileShouldBeAdjacent() {
        Tile atOrigin = givenIHaveATile();
        Tile adjacent = builder.getAdjacentTile(atOrigin);
        Assert.assertTrue(areAdjacent(atOrigin, adjacent));

    }

    private Tile givenIHaveATile() {
        return builder.getTileAtOrigin();
    }

    private boolean areAdjacent(Tile t1, Tile t2) {
        Location[] t1Locations = t1.getArrayOfTerrainLocations();
        Location[] t2Locations = t2.getArrayOfTerrainLocations();
        boolean foundAdjacent = false;

        for(int i = 0; i < t1Locations.length; i++) {
            for(int j = 0; j < t2Locations.length; j++) {
                Location l1 = t1Locations[i];
                Location l2 = t2Locations[j];

                if (areAdjacentLocations(l1, l2)) {
                    foundAdjacent = true;
                    break;
                }
            }
        }

        return foundAdjacent;
    }

    private boolean areAdjacentLocations(Location l1, Location l2) {
     return  locationGen.up(l1).equals(l2) ||
             locationGen.upRight(l1).equals(l2) ||
             locationGen.downRight(l1).equals(l2) ||
             locationGen.down(l1).equals(l2) ||
             locationGen.downLeft(l1).equals(l2) ||
             locationGen.upLeft(l1).equals(l2);
    }
}
