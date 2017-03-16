import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileTest {
    public Tile uut;
    public Terrain leftTerrain;
    public Terrain rightTerrain;
    public TileLocation location;
    public TileOrientation orientation;

    @Before
    public void setupInstances() {
        leftTerrain = new Terrain(TerrainType.GRASSLANDS);
        rightTerrain = new Terrain(TerrainType.JUNGLE);
        location = new TileLocation(2, 5);
        orientation = new TileOrientation(Orientation.NW);
    }

    public void givenTheTileHasBeenInitialized() {
        uut = new Tile(leftTerrain, rightTerrain, location, orientation);
    }

    public void theLeftTerrainShouldBe(Terrain expectedLeftTerrain) {
        Assert.assertEquals(expectedLeftTerrain, uut.leftTerrain);
    }

    public void theRightTerrainShouldBe(Terrain expectedRightTerrain) {
        Assert.assertEquals(expectedRightTerrain, uut.rightTerrain);
    }

    public void theTileLocationShouldBe(TileLocation expectedLocation) {
        Assert.assertEquals(expectedLocation, uut.location);
    }

    public void theTileOrientationShouldBe(TileOrientation expectedOrientation) {
        Assert.assertEquals(expectedOrientation, uut.orientation);
    }

    public void givenTheTileLevelIs(int level) {
        uut.level = level;
    }

    public void whenTheTileLevelIsIncreased() {
        uut.increaseLevel();
    }

    public void theTileLevelShouldBe(int expectedLevel) {
        Assert.assertEquals(expectedLevel, uut.level);
    }

    @Test
    public void theTileDataShouldBeSetCorrectly() {
        givenTheTileHasBeenInitialized();
        theLeftTerrainShouldBe(this.leftTerrain);
        theRightTerrainShouldBe(this.rightTerrain);
        theTileLocationShouldBe(this.location);
        theTileOrientationShouldBe(this.orientation);
    }

    @Test
    public void theTileLevelShouldBeOneWhenOriginallyInitialized() {
        givenTheTileHasBeenInitialized();
        theTileLevelShouldBe(1);
    }

    @Test
    public void theTileLevelShouldBeIncreasedCorrectly() {
        givenTheTileHasBeenInitialized();
        givenTheTileLevelIs(3);
        whenTheTileLevelIsIncreased();
        theTileLevelShouldBe(4);
    }
}
