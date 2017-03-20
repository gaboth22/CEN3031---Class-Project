package TileTest.TileImplTest;

import Terrain.TerrainLocation.TerrainLocation;
import Terrain.Terrain.Terrain;

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
    List<TerrainLocation> locationList;

    List<Terrain> givenIHaveAListOfTerrains(Terrain volcano, Terrain left, Terrain right) {
        return new ArrayList<>(Arrays.asList(volcano, left, right));
    }


    List<TerrainLocation> givenIHaveAListOfTerrainLocations(TerrainLocation locOfVolcano,
                                                            TerrainLocation locOfLeftTerrain,
                                                            TerrainLocation locOfRightTerrain) {
        return new ArrayList<>(Arrays.asList(locOfVolcano, locOfLeftTerrain, locOfRightTerrain));
    }

    @Before
    public void initializeTile() {
        terrainList = givenIHaveAListOfTerrains(Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.ROCKY);
        locationList = givenIHaveAListOfTerrainLocations(new TerrainLocation(0,0),
                                                         new TerrainLocation(0, 1),
                                                         new TerrainLocation(1, 0));
        tile = new TileImpl(terrainList, locationList);
    }

    public void terrainsShouldNotBeNull(Terrain[] terrains) {
        for(int i = 0; i < terrains.length; i++) {
            Assert.assertTrue(terrains[i] != null);
        }
    }

    public void locationsShouldNotBeNull(TerrainLocation[] locations) {
        for(int i = 0; i < locations.length; i++) {
            Assert.assertTrue(locations[i] != null);
        }
    }

    @Test
    public void itShouldBePossibleToGetTheTerrainsAsAnArray() {
        Terrain[] terrains = tile.getArrayOfTerrains();
        terrainsShouldNotBeNull(terrains);
    }

    @Test public void itShouldBePossibleToGetTheLocationsAsAnArray() {
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();
        locationsShouldNotBeNull(locations);
    }

    @Test
    public void terrainArrayDataShouldBeTheSameThanThatOfTheTerrainList() {
        Terrain[] terrains = tile.getArrayOfTerrains();

        for(int i = 0; i < terrainList.size(); i++) {
            Assert.assertEquals(terrainList.get(i), terrains[i]);
        }
    }

    @Test
    public void locationArrayDataShouldBeTheSameThanThatOfTheLocationList() {
        TerrainLocation[] locations = tile.getArrayOfTerrainLocations();

        for(int i = 0; i < locationList.size(); i++) {
            Assert.assertEquals(locationList.get(i), locations[i]);
        }
    }
}
