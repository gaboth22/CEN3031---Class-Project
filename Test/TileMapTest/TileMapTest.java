package TileMapTest;

import TileMap.*;
import Tile.*;
import TileTest.TileLocationTestDouble;
import TileTest.TileOrientationTestDouble;
import org.junit.Assert;
import org.junit.Test;
import Terrain.*;

public class TileMapTest {

    TileMap testMap = new TileMap();

    Tile tileToInsert;

    void givenThatYouHaveATileToInsertAtTheOrigin(){
        TerrainLocation volcanoLocation = new MapLocation(0,0,0);
        TerrainLocation leftTerrainLocation = volcanoLocation.up();
        TerrainLocation rightTerrainLocation = volcanoLocation.upLeft();
        Terrain volcano = new Volcano(volcanoLocation);
        Terrain leftTerrain = new RockyTerrain(leftTerrainLocation);
        Terrain rightTerrain = new LakeTerrain(rightTerrainLocation);
        TileLocation dummyLocation = new TileLocationTestDouble();
        TileOrientation dummyOrientation = new TileOrientationTestDouble();

        tileToInsert = new Tile(volcano, leftTerrain, rightTerrain, dummyLocation, dummyOrientation);
    }

    void whenYouPlaceTheTile() {
        try{
            testMap.placeTile(tileToInsert);
        }
        catch(LocationOccupiedException ex) {

        }
    }

    void thentheTileShouldBePlacedBePlacedAtTheOrigin() {
        TerrainLocation origin = new MapLocation(0,0,0);
        Tile tileAtGivenLocation = testMap.getTileAtLocation(origin);
        Assert.assertEquals(tileAtGivenLocation, tileToInsert);
    }

    @Test
    public void testThatTheFirstTileIsPlacedAtTheOrigin() {
        givenThatYouHaveATileToInsertAtTheOrigin();
        whenYouPlaceTheTile();
        thentheTileShouldBePlacedBePlacedAtTheOrigin();
    }

    @Test
    public void testThatTilesDoNotOverlapInLocation() {
        givenThatYouHaveATileToInsertAtTheOrigin();
        whenYouPlaceTheTile();
        thenYouShouldNotBeAbleToPlaceAnotherTileAtTheOrigin();
    }

    void thenYouShouldNotBeAbleToPlaceAnotherTileAtTheOrigin(){
        try{
            testMap.placeTile(tileToInsert);
            Assert.fail();
        }
        catch (LocationOccupiedException ex){

        }
    }
}
