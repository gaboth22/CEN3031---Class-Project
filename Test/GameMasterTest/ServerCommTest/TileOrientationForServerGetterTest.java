package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.TileOrientationForServerGetter;
import Location.Location;
import Movement.*;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TileOrientationForServerGetterTest {
    private Movement mov;
    private int gottenOrienation;

    @Before
    public void initializeInstances() {
        mov = new AxialMovement();
    }

    @Test
    public void orientationShouldBeCorrect() {
        Tile tile = givenIHaveATileWith();
        whenIGetItsOrientation(tile);
        thenTheOrientationShouldBe(1);
    }

    private Tile givenIHaveATileWith() {
        Location ofVolcano = new Location(0,0);
        Location ofLeftTerrain = mov.downLeft(ofVolcano);
        Location ofRightTerrain = mov.upLeft(ofVolcano);

        Terrain[] terrains = new Terrain[]{Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.ROCKY};
        Location[] locations = new Location[]{ofVolcano, ofLeftTerrain, ofRightTerrain};

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private void whenIGetItsOrientation(Tile ofTile) {
        gottenOrienation = TileOrientationForServerGetter.getOrientation(ofTile);
    }

    private void thenTheOrientationShouldBe(int expectedOrientation) {
        Assert.assertEquals(expectedOrientation, gottenOrienation);
    }

    @Test
    public void orientationShouldBeCorrectAgain() {
        Tile tile = givenIHaveAnotherTile();
        whenIGetItsOrientation(tile);
        thenTheOrientationShouldBe(4);
    }

    private Tile givenIHaveAnotherTile() {
        Location ofVolcano = new Location(0,0);
        Location ofLeftTerrain = mov.upRight(ofVolcano);
        Location ofRightTerrain = mov.downRight(ofVolcano);

        Terrain[] terrains = new Terrain[]{Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.ROCKY};
        Location[] locations = new Location[]{ofVolcano, ofLeftTerrain, ofRightTerrain};

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }
}
