package TileTest;

import Terrain.*;
import Tile.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileTest {
    public Tile uut;
    public Terrain volcano;
    public Terrain leftTerrain;
    public Terrain rightTerrain;
    public TileLocation location;
    public TileOrientation orientation;

    @Before
    public void setupInstances() {
        volcano = new Volcano();
        leftTerrain = new LakeTerrain();
        rightTerrain = new JungleTerrain();
        location = new TileLocationTestDouble();
        orientation = new TileOrientationTestDouble();
    }

    public void givenTheTileHasBeenInitialized() {
        uut = new Tile(volcano, leftTerrain, rightTerrain, location, orientation);
    }

    public void theLeftTerrainShouldBe(Terrain expectedLeftTerrain) {
        Assert.assertEquals(expectedLeftTerrain, uut.getLeftTerrain());
    }

    public void theRightTerrainShouldBe(Terrain expectedRightTerrain) {
        Assert.assertEquals(expectedRightTerrain, uut.getRightTerrain());
    }

    public void theTileLocationShouldBe(TileLocation expectedLocation) {
        Assert.assertEquals(expectedLocation, uut.getLocation());
    }

    public void theTileOrientationShouldBe(TileOrientation expectedOrientation) {
        Assert.assertEquals(expectedOrientation, uut.getOrientation());
    }

    public void givenTheTileLevelIs(int level) {
        for(int i = 0; i < level-1; i++) {
            uut.increaseLevel();
        }
    }

    public void whenTheTileLevelIsIncreased() {
        uut.increaseLevel();
    }

    public void theTileLevelShouldBe(int expectedLevel) {
        Assert.assertEquals(expectedLevel, uut.getLevel());
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
