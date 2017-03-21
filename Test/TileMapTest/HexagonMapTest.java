package TileMapTest;

import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileMap.Hexagon;
import TileMap.HexagonMap;
import TileMap.InvalidTileLocationException;
import TileMap.LocationOccupiedException;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Movement.Movement;
import Movement.AxialMovement;
import org.omg.CORBA.DynAnyPackage.Invalid;

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
    public void getAllHexagonsShouldReturnAnEmptyListIfHashMapIsEmpty() {
        List<Hexagon> values = hexMap.getAllHexagons();
        Assert.assertEquals(0, values.size());
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
    public void returnFalseWhenNoPieceAtALocation() {
        Assert.assertFalse(hexMap.hasHexagonAt(new Location(5,3)));
    }
}
