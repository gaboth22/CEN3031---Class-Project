package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.TileFromOrientationAndTerrain;
import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.TileInformationGenerator.TileInformationGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class TileFromOrientationAndTerrainTest {

    private int orientation;
    private Location volcanoLocation;
    private Terrain[] terrains;

    private TileInformationGenerator testTileInformationGenerator;

    @Test(expected = InvalidParameterException.class)
    public void invalidOrientationShouldThrowInvalidParameterError() {
        givenIHaveATileWithInvalidOrientation();
        whenICreateATileInformationGenerator();
    }

    private void givenIHaveATileWithInvalidOrientation() {
        final int INVALID_ORIENTATION = -1;

        orientation = INVALID_ORIENTATION;
        volcanoLocation = new Location(1,1);
        terrains = new Terrain[] {Terrain.GRASSLANDS, Terrain.JUNGLE};
    }

    @Test
    public void aValidCreatedTileShouldHaveValidTerrainOutput() {
        givenYouHaveASimpleTile();
        whenICreateATileInformationGenerator();
        whenICheckTheLocations();
    }

    @Test
    public void orientationOneShouldGiveValidLocations() {
        givenIHaveASimpleTileWithOrientation(1);
        whenICreateATileInformationGenerator();
        thenTheLocationsShouldBeCorrectForOrientation(1);
    }

    @Test
    public void theRemainingOrientationsShouldGiveValidLocations() {
        for(int i = 2; i < 7; i++) {
            givenIHaveASimpleTileWithOrientation(i);
            whenICreateATileInformationGenerator();
            thenTheLocationsShouldBeCorrectForOrientation(i);
        }

    }

    public void givenIHaveASimpleTileWithOrientation(int orientation) {
        this.orientation = orientation;
        volcanoLocation = new Location(0,0);
        terrains = new Terrain[] {Terrain.GRASSLANDS, Terrain.JUNGLE};
    }

    public void thenTheLocationsShouldBeCorrectForOrientation(int orientation) {

        Assert.assertEquals(volcanoLocation, testTileInformationGenerator.getLocations()[0]);

        Location left = null;
        Location right = null;

        switch(orientation) {
            case 1:
                left = new Location(-1,0);
                right = new Location(-1,1);
                break;
            case 2:
                left = new Location(-1,1);
                right = new Location(0,1);
                break;
            case 3:
                left = new Location(0,1);
                right = new Location(1,0);
                break;
            case 4:
                left = new Location(1,0);
                right = new Location(1,-1);
                break;
            case 5:
                left = new Location(1,-1);
                right = new Location(0,-1);
                break;
            case 6:
                left = new Location(0,-1);
                right = new Location(-1,0);
                break;
            default:

        }
        Assert.assertEquals(left, testTileInformationGenerator.getLocations()[1]);
        Assert.assertEquals(right, testTileInformationGenerator.getLocations()[2]);
    }

    public void givenYouHaveASimpleTile() {
        orientation = 1;
        volcanoLocation = new Location(1,1);
        terrains = new Terrain[] {Terrain.ROCKY, Terrain.GRASSLANDS};
    }

    public void whenICreateATileInformationGenerator() {
        testTileInformationGenerator = new TileFromOrientationAndTerrain(orientation, volcanoLocation, terrains);
    }

    public void whenICheckTheLocations() {
        Assert.assertEquals(terrains[0], testTileInformationGenerator.getTerrains()[1]);
        Assert.assertEquals(terrains[1], testTileInformationGenerator.getTerrains()[2]);
    }
}
