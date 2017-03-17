package Cucumber;
import Terrain.*;
import Tile.*;
import TileTest.TileLocationTestDouble;
import TileTest.TileOrientationTestDouble;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class Steps{
    public Tile uut = null;
    public Terrain volcano;
    public Terrain leftTerrain;
    public Terrain rightTerrain;
    public TileLocation location;
    public TileOrientation orientation;

    @Given("^terrains are initialized$")
    public void setupInstances() {
        volcano = new Volcano();
        leftTerrain = new LakeTerrain();
        rightTerrain = new JungleTerrain();
        location = new TileLocationTestDouble();
        orientation = new TileOrientationTestDouble();
    }
    @When("^a test is initialized")
    public void testTile() {
        uut = new Tile(volcano, leftTerrain, rightTerrain, location, orientation);
    }
    @Then("^there should be proper terrains$")
    public void theLeftTerrainShouldBe() {
        Assert.assertEquals(leftTerrain, uut.getLeftTerrain());
    }
    public void theRightTerrainShouldBe() {
        Assert.assertEquals(rightTerrain, uut.getRightTerrain());
    }
}