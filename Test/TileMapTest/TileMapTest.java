package TileMapTest;

import TileMap.*;
import Tile.*;
import TileTest.TileLocationTestDouble;
import TileTest.TileOrientationTestDouble;
import org.junit.Assert;
import org.junit.Test;
import Terrain.*;

import java.util.List;

public class TileMapTest {

    TileMap testMap = new TileMap();

    Tile tileToInsert;

    private void givenThatYouHaveATileToInsertAtTheOrigin(){
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

    private void whenYouPlaceTheTile() {
        try{
            testMap.placeTile(tileToInsert);
        }
        catch(LocationOccupiedException ex) {

        }
    }

    private void thenTheTilesLocationsShouldMapCorrectly() {
        List<Terrain> terrains = tileToInsert.getListOfTerrains();
        for(int i = 0; i < terrains.size(); i++) {
            TerrainLocation currentLocation = terrains.get(i).getLocation();
            Tile tileFromMapAtLocation = testMap.getTileAtLocation(currentLocation);
            Assert.assertEquals(tileToInsert, tileFromMapAtLocation);
        }
    }

    @Test
    public void testThatTheFirstTileIsPlacedAtTheOrigin() {
        givenThatYouHaveATileToInsertAtTheOrigin();
        whenYouPlaceTheTile();
        thenTheTilesLocationsShouldMapCorrectly();
    }

    @Test
    public void testThatTilesDoNotOverlapInLocation() {
        givenThatYouHaveATileToInsertAtTheOrigin();
        whenYouPlaceTheTile();
        thenYouShouldNotBeAbleToPlaceAnotherTileAtTheOrigin();
    }

    private void thenYouShouldNotBeAbleToPlaceAnotherTileAtTheOrigin(){
        try{
            testMap.placeTile(tileToInsert);
            Assert.fail();
        }
        catch (LocationOccupiedException ex){

        }
    }
}
