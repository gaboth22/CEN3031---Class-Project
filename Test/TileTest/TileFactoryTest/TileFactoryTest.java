package TileTest.TileFactoryTest;

import Terrain.Terrain.Terrain;
import Terrain.TerrainLocation.TerrainLocation;
import Terrain.TerrainMovement.AxialMovement;
import Terrain.TerrainMovement.TerrainMovement;
import Tile.Tile.Tile;
import Tile.TileFactory.TileFactory;
import Tile.TileOrientation.TileOrientation;
import TileTest.TileInformationGeneratorTest.TileInformationGeneratorTestDouble;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileFactoryTest {
    TileInformationGeneratorTestDouble tileInformationGenerator;
    TerrainMovement locationGenerator;
    TileFactory factory;

    @Before
    public void initializeInstances() {
        locationGenerator = new AxialMovement();
        tileInformationGenerator = new TileInformationGeneratorTestDouble();
        tileInformationGenerator.setCol(1);
        tileInformationGenerator.setRow(1);
        Terrain[] terrains = {Terrain.VOLCANO, Terrain.ROCKY, Terrain.JUNGLE};
        tileInformationGenerator.setTerrains(terrains);
        factory = TileFactory.getTileFactory();
        factory.setLocationGenerator(locationGenerator);
    }

    @Test
    public void theTileFactoryShouldBeStatic() {
        TileFactory factoryReference = TileFactory.getTileFactory();
        TileFactory anotherFactoryReference = TileFactory.getTileFactory();
        Assert.assertEquals(factoryReference, anotherFactoryReference);
    }

    public void givenTheTileOrientationIs(TileOrientation orientation) {
        tileInformationGenerator.setOrientation(orientation);
    }

    public void theLeftTerrainLocationShouldBe(TerrainLocation expectedLocation, TerrainLocation actualLocation) {
        boolean areEqual = expectedLocation.getCol() == actualLocation.getCol() &&
                           expectedLocation.getRow() == actualLocation.getRow();

        Assert.assertTrue(areEqual);
    }

    public void theRightTerrainLocationShouldBe(TerrainLocation expectedLocation, TerrainLocation actualLocation) {
        boolean areEqual = expectedLocation.getCol() == actualLocation.getCol() &&
                           expectedLocation.getRow() == actualLocation.getRow();

        Assert.assertTrue(areEqual);
    }

    public Tile givenIMakeATile() {
        return factory.makeTile(tileInformationGenerator);
    }

    @Test
    public void LeftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsDown() {
        givenTheTileOrientationIs(TileOrientation.DOWN);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.upRight(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void RightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsDown() {
        givenTheTileOrientationIs(TileOrientation.DOWN);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theRightTerrainLocationShouldBe(locationGenerator.up(new TerrainLocation(1,1)), locations[2]);
    }

    @Test
    public void LeftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsRight() {
        givenTheTileOrientationIs(TileOrientation.RIGHT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.upLeft(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void RightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsRight() {
        givenTheTileOrientationIs(TileOrientation.RIGHT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.downLeft(new TerrainLocation(1,1)), locations[2]);
    }

    @Test
    public void LeftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsUp() {
        givenTheTileOrientationIs(TileOrientation.UP);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.down(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void RightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsUp() {
        givenTheTileOrientationIs(TileOrientation.UP);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.downRight(new TerrainLocation(1,1)), locations[2]);
    }

    @Test
    public void LeftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsLeft() {
        givenTheTileOrientationIs(TileOrientation.LEFT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.downRight(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void RightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsLeft() {
        givenTheTileOrientationIs(TileOrientation.LEFT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.upRight(new TerrainLocation(1,1)), locations[2]);
    }

}
