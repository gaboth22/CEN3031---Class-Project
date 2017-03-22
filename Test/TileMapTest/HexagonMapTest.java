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

import java.util.*;

import Movement.Movement;
import Movement.AxialMovement;

public class HexagonMapTest {

    HexagonMap hexMap;
    Movement coordinateSystem;

    Tile toPlace;
    List<Tile> tilesToPlace;

    Hexagon toCheck;


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
    public void getAllHexagonsShouldReturnAValidMap() throws InvalidTileLocationException, LocationOccupiedException {
        givenIHaveAListOfValidTilesToAdd();
        whenIInsertATile();

    }

    void givenIHaveAListOfValidTilesToAdd() {
        tilesToPlace = new ArrayList();
        Location volcano = new Location(0, 0);
        Location left = coordinateSystem.downRight(volcano);
        Location right = coordinateSystem.upRight(volcano);
        List<Location> locations = givenIHaveAListOfTerrainLocations(volcano, left, right);
        List<Terrain> terrains = givenIHaveAListOfTerrains(Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.JUNGLE);
        tilesToPlace.add(new TileImpl(terrains, locations));

        volcano = coordinateSystem.up(volcano);
        left = coordinateSystem.upRight(volcano);
        right = coordinateSystem.up(volcano);
        locations = givenIHaveAListOfTerrainLocations(volcano, left, right);
        terrains = givenIHaveAListOfTerrains(Terrain.VOLCANO, Terrain.LAKE, Terrain.JUNGLE);
        tilesToPlace.add(new TileImpl(terrains, locations));

        volcano = coordinateSystem.down(volcano);
        left = coordinateSystem.upRight(volcano);
        right = coordinateSystem.up(volcano);
        locations = givenIHaveAListOfTerrainLocations(volcano, left, right);
        terrains = givenIHaveAListOfTerrains(Terrain.VOLCANO, Terrain.JUNGLE, Terrain.ROCKY);
        tilesToPlace.add(new TileImpl(terrains, locations));
    }

    void whenIInsertATile() throws InvalidTileLocationException, LocationOccupiedException {
        for(int i = 0; i < tilesToPlace.size(); i++) {
            hexMap.insertTile(tilesToPlace.get(i));
        }
    }

    void thenTheHexagonsICanSeeShouldBeValid(List<Tile> tiles) {
        Map<Location, Hexagon> hexagonMap = hexMap.getAllHexagons();

        Location locToCheck = new Location(0,0);
        toCheck = hexagonMap.get(locToCheck);
        assertThatHexagonHas(2, Terrain.VOLCANO, 2);

        locToCheck = coordinateSystem.up(locToCheck);
        toCheck = hexagonMap.get(locToCheck);
        assertThatHexagonHas(2, Terrain.ROCKY, 2);

        locToCheck = coordinateSystem.up(locToCheck);
        toCheck = hexagonMap.get(locToCheck);
        assertThatHexagonHas(1, Terrain.JUNGLE, 1);

        locToCheck = coordinateSystem.downLeft(locToCheck);
        toCheck = hexagonMap.get(locToCheck);
        assertThatHexagonHas(1, Terrain.LAKE, 1);

        locToCheck = coordinateSystem.down(locToCheck);
        toCheck = hexagonMap.get(locToCheck);
        assertThatHexagonHas(2, Terrain.JUNGLE, 2);

        locToCheck = coordinateSystem.down(locToCheck);
        toCheck = hexagonMap.get(locToCheck);
        assertThatHexagonHas(1, Terrain.GRASSLANDS, 0);
    }

    void assertThatHexagonHas(int height, Terrain terrain, int parentTileID) {
        Assert.assertEquals(height, toCheck.getHeight());
        Assert.assertEquals(terrain, toCheck.getTerrain());
        Assert.assertEquals(parentTileID, toCheck.getParentTileID());
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
