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

    public void terrainsShouldBe(Terrain expected1, Terrain expected2, Terrain expected3, Terrain[] actualTerrains) {
        Assert.assertEquals(expected1, actualTerrains[0]);
        Assert.assertEquals(expected2, actualTerrains[1]);
        Assert.assertEquals(expected3, actualTerrains[2]);
    }

    @Test
    public void terrainsShouldShouldBeSetCorrectly() {
        givenTheTileOrientationIs(TileOrientation.UP);
        Tile tile = givenIMakeATile();
        Terrain[] terrains = tile.getArrayOfTerrains();
        terrainsShouldBe(Terrain.VOLCANO, Terrain.ROCKY, Terrain.JUNGLE, terrains);
    }

    @Test
    public void leftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsDown() {
        givenTheTileOrientationIs(TileOrientation.DOWN);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.upRight(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void rightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsDown() {
        givenTheTileOrientationIs(TileOrientation.DOWN);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theRightTerrainLocationShouldBe(locationGenerator.up(new TerrainLocation(1,1)), locations[2]);
    }

    @Test
    public void leftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsRight() {
        givenTheTileOrientationIs(TileOrientation.RIGHT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.upLeft(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void rightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsRight() {
        givenTheTileOrientationIs(TileOrientation.RIGHT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.downLeft(new TerrainLocation(1,1)), locations[2]);
    }

    @Test
    public void leftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsUp() {
        givenTheTileOrientationIs(TileOrientation.UP);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.down(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void rightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsUp() {
        givenTheTileOrientationIs(TileOrientation.UP);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.downRight(new TerrainLocation(1,1)), locations[2]);
    }

    @Test
    public void leftTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsLeft() {
        givenTheTileOrientationIs(TileOrientation.LEFT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.downRight(new TerrainLocation(1,1)), locations[1]);
    }

    @Test
    public void rightTerrainLocationInGeneratedTileShouldBeCorrectWhenOrientationIsLeft() {
        givenTheTileOrientationIs(TileOrientation.LEFT);
        Tile tile = givenIMakeATile();
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        theLeftTerrainLocationShouldBe(locationGenerator.upRight(new TerrainLocation(1,1)), locations[2]);
    }

}
