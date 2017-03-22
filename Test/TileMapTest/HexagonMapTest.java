package TileMapTest;

import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileMap.Hexagon;
import TileMap.HexagonMap;
import TileMap.InvalidTileLocationException;
import TileMap.LocationOccupiedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Movement.Movement;
import Movement.AxialMovement;

public class HexagonMapTest {

    HexagonMap hexMap;
    Movement coordinateSystem;

    Tile toPlace;

    List<Terrain> givenIHaveAListOfTerrains(Terrain volcano, Terrain left, Terrain right) {
        return new ArrayList<>(Arrays.asList(volcano, left, right));
    }


    List<Location> givenIHaveAListOfTerrainLocations(Location locOfVolcano,
                                                     Location locOfLeftTerrain,
                                                     Location locOfRightTerrain) {
        return new ArrayList<>(Arrays.asList(locOfVolcano, locOfLeftTerrain, locOfRightTerrain));
    }

    void whenYouInsertATile() throws InvalidTileLocationException, LocationOccupiedException {
        hexMap.insertTile(toPlace);
    }

    @Before
    public void initializeHexMap() {
        hexMap = new HexagonMap();
        coordinateSystem = new AxialMovement();
    }

    @Test
    public void getAllHexagonsShouldReturnAnEmptyMapsIfHashMapIsEmpty() {
        Map<Location, Hexagon> values = hexMap.getAllHexagons();
        Assert.assertEquals(0, values.size());
    }

    @Test
    public void getAllHexagonsShouldReturnAValidList() {
        givenIHaveAListOf
    }

    @Test(expected = InvalidTileLocationException.class)
    public void anErrorIsThrownWhenTheFirstTileIsNotPlacedWithVolcanoOnOrigin() throws InvalidTileLocationException, LocationOccupiedException {
        givenIHaveATileToPlaceWithVolcanoAt(0,1);
        whenYouInsertATile();
    }

    @Test
    public void aCorrectFirstTilePlacementWillHaveAVolcanoAtTheOrigin() throws InvalidTileLocationException, LocationOccupiedException {
        givenIHaveATileToPlaceWithVolcanoAt(0,0);
        whenYouInsertATile();
        thenThereShouldBeAVolcanoAtTheOrigin();
    }

    private void thenThereShouldBeAVolcanoAtTheOrigin() {
        Hexagon atOrigin = hexMap.getHexagonAt(new Location(0,0));
        Assert.assertEquals(Terrain.VOLCANO, atOrigin.getTerrain());
    }

    private void givenIHaveATileToPlaceWithVolcanoAt(int x, int y) {
        Location volcano = new Location(x, y);
        Location left = coordinateSystem.upRight(volcano);
        Location right = coordinateSystem.up(volcano);
        List<Location> locations = givenIHaveAListOfTerrainLocations(volcano, left, right);
        List<Terrain> terrains = givenIHaveAListOfTerrains(Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.JUNGLE);
        toPlace = new TileImpl(terrains, locations);
    }

    @Test
    public void whenThereIsNoPieceAtALocationNullShouldReturn() {
        Assert.assertNull(hexMap.getHexagonAt(new Location(0,0)));
    }

    @Test
    public void whenThereIsAPieceAtALocationThatPieceShouldReturn() throws InvalidTileLocationException, LocationOccupiedException{
        givenIHaveATileToPlaceWithVolcanoAt(0,0);
        whenYouInsertATile();
        thenIShouldGetAValidHexagonAtTheTilesThreeLocations();
    }

    void thenIShouldGetAValidHexagonAtTheTilesThreeLocations() {
        Location[] locations = toPlace.getArrayOfTerrainLocations();
        Terrain[] terrains = toPlace.getArrayOfTerrains();
        for(int i = 0; i < locations.length; i++) {
            Hexagon current = hexMap.getHexagonAt(locations[i]);
            Assert.assertEquals(terrains[i], current.getTerrain());
            Assert.assertEquals(1, current.getHeight());
            Assert.assertEquals(locations[i],current.getTerrainLocation());
            Assert.assertEquals(0, current.getParentTileID());
        }
    }

    @Test
    public void returnFalseWhenNoPieceAtALocation() {
        Assert.assertFalse(hexMap.hasHexagonAt(new Location(5,3)));
    }

    @Test
    public void returnTrueWhenAPieceAtALocation() throws InvalidTileLocationException, LocationOccupiedException{
        givenIHaveATileToPlaceWithVolcanoAt(0,0);
        whenYouInsertATile();
        thenThereShouldBeAPieceAtTheOrigin();
    }

    void thenThereShouldBeAPieceAtTheOrigin() {
        Assert.assertTrue(hexMap.hasHexagonAt(new Location(0,0)));
    }
}
