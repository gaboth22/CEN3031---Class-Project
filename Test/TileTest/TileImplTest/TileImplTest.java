package TileTest.TileImplTest;

import Location.Location;
import Terrain.Terrain.Terrain;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Tile.Tile.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileImplTest {
    Tile tile;
    List<Terrain> terrainList;
    List<Location> locationList;

    List<Terrain> givenIHaveAListOfTerrains(Terrain volcano, Terrain left, Terrain right) {
        return new ArrayList<>(Arrays.asList(volcano, left, right));
    }


    List<Location> givenIHaveAListOfTerrainLocations(Location locOfVolcano,
                                                     Location locOfLeftTerrain,
                                                     Location locOfRightTerrain) {
        return new ArrayList<>(Arrays.asList(locOfVolcano, locOfLeftTerrain, locOfRightTerrain));
    }

    public void givenTheTerrainListDoesNotHaveTheVolcanoAsTheFirstTerrain() {
        terrainList = givenIHaveAListOfTerrains(Terrain.GRASSLANDS, Terrain.VOLCANO, Terrain.ROCKY);
    }

    public void whenTheTileIsInitialized() {
        tile = new TileImpl(terrainList, locationList);
    }

    @Before
    public void initializeTile() {
        terrainList = givenIHaveAListOfTerrains(Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.ROCKY);
        locationList = givenIHaveAListOfTerrainLocations(new Location(0,0),
                                                         new Location(0, 1),
                                                         new Location(1, 0));
        tile = new TileImpl(terrainList, locationList);
    }

    public void terrainsShouldNotBeNull(Terrain[] terrains) {
        for(int i = 0; i < terrains.length; i++) {
            Assert.assertTrue(terrains[i] != null);
        }
    }

    public void locationsShouldNotBeNull(Location[] locations) {
        for(int i = 0; i < locations.length; i++) {
            Assert.assertTrue(locations[i] != null);
        }
    }

    @Test
    public void equalsIsSymmetric() {
        Tile tileTwo = new TileImpl(terrainList, locationList);
        Assert.assertTrue(tileTwo.equals(tile) && tile.equals(tileTwo));
        Assert.assertTrue(tile.hashCode() == tileTwo.hashCode());
    }

    @Test
    public void tileCreationIsFinal() {
        Tile tileTwo = new TileImpl(terrainList, locationList);
        Location[] locations = tile.getArrayOfTerrainLocations();
        Terrain[] terrains = tile.getArrayOfTerrains();
        locations[0] = new Location(4,4);
        terrains[0] = Terrain.GRASSLANDS;
        Assert.assertEquals(tileTwo, tile);
    }

    @Test
    public void notEqualsTileAreNotEqual() {
        terrainList = givenIHaveAListOfTerrains(Terrain.VOLCANO, Terrain.JUNGLE, Terrain.ROCKY);
        locationList = givenIHaveAListOfTerrainLocations(new Location(0,0),
                new Location(0, 1),
                new Location(1, -1));
        Tile tileTwo = new TileImpl(terrainList, locationList);
        Assert.assertTrue(!tileTwo.equals(tile) && !tile.equals(tileTwo));
    }

    @Test(expected = InvalidParameterException.class)
    public void firstTerrainMustBeTheVolcano() {
        givenTheTerrainListDoesNotHaveTheVolcanoAsTheFirstTerrain();
        whenTheTileIsInitialized();
    }

    @Test
    public void firstTerrainShouldAlwaysBeTheVolcano() {
        Terrain[] terrains = tile.getArrayOfTerrains();
        Assert.assertTrue(Terrain.VOLCANO == terrains[0]);
    }

    @Test
    public void itShouldBePossibleToGetTheTerrainsAsAnArray() {
        Terrain[] terrains = tile.getArrayOfTerrains();
        terrainsShouldNotBeNull(terrains);
    }

    @Test public void itShouldBePossibleToGetTheLocationsAsAnArray() {
        Location[] locations = tile.getArrayOfTerrainLocations();
        locationsShouldNotBeNull(locations);
    }

    @Test
    public void terrainArrayDataShouldBeTheSameAsThatOfTheTerrainList() {
        Terrain[] terrains = tile.getArrayOfTerrains();

        for(int i = 0; i < terrainList.size(); i++) {
            Assert.assertEquals(terrainList.get(i), terrains[i]);
        }
    }

    @Test
    public void locationArrayDataShouldBeTheSameThanThatOfTheLocationList() {
        Location[] locations = tile.getArrayOfTerrainLocations();

        for(int i = 0; i < locationList.size(); i++) {
            Assert.assertEquals(locationList.get(i), locations[i]);
        }
    }
}
