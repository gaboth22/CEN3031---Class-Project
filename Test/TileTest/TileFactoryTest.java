package TileTest;

import Terrain.GrasslandsTerrain;
import Terrain.RockyTerrain;
import Tile.*;
import cucumber.api.java.en.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TileFactoryTest {
    TileFactory uut;
    TileInformationGeneratorTestDouble infoGenTestDouble;

    @Before
    public void initializeInstances() {
        infoGenTestDouble = new TileInformationGeneratorTestDouble();
        infoGenTestDouble.setLeftTerrain(new RockyTerrain());
        infoGenTestDouble.setRightTerrain(new GrasslandsTerrain());
        infoGenTestDouble.setTileOrientation(new TileOrientationTestDouble());
        uut = new TileFactory(infoGenTestDouble);
    }
    @Given("a game is in session")
    public void gameInSessionTest(){
        Assert.assertTrue(true);
    }

    @When("a player draws a tile")
    public void playerDrawsTileTest(){
        Assert.assertTrue(true);
    }

    @Then("each hexagon should be of either one of four legal terrains or a volcano")
    public void theTileFactoryShouldCreateAValidTile() {
        Tile tile = uut.makeTile();
        Assert.assertTrue(tile.getLeftTerrain() != null);
        Assert.assertTrue(tile.getRightTerrain() != null);
        Assert.assertTrue(tile.getOrientation() != null);
        Assert.assertTrue(tile.getLocation() != null);
    }
}