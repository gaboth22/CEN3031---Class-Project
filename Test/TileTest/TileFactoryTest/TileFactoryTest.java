package TileTest.TileFactoryTest;

import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.TileFactory.ImpossibleTileException;
import Tile.TileFactory.TileFactory;
import TileTest.TileInformationGeneratorTestDouble.TileInformationGeneratorTestDouble;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

public class TileFactoryTest {
    TileFactory factory;
    TileInformationGeneratorTestDouble informationGenerator;

    @Before
    public void initializeInstances() {
        factory = TileFactory.getTileFactory();
        informationGenerator = new TileInformationGeneratorTestDouble();
        Location l1 = new Location(0, 0);
        Location l2 = new Location(0, 1);
        Location l3 = new Location(1, 0);
        informationGenerator.setLocations(l1, l2, l3);
        informationGenerator.setTerrains(Terrain.VOLCANO, Terrain.LAKE, Terrain.ROCKY);
    }

    @Test
    public void theTileFactoryShouldBeStatic() {
        TileFactory factoryReference = TileFactory.getTileFactory();
        TileFactory anotherFactoryReference = TileFactory.getTileFactory();
        Assert.assertEquals(factoryReference, anotherFactoryReference);
    }

    @Test
    public void theTileLocationsShouldBeThoseFromTheFactory() throws ImpossibleTileException {
        Tile tile = factory.makeTile(informationGenerator);
        Location[] locations = tile.getArrayOfTerrainLocations();

        for(int i = 0; i < locations.length; i++) {
            Assert.assertEquals(informationGenerator.getLocations()[i], locations[i]);
        }
    }

    @Test
    public void theTerrainsShouldBeThoseFromTheFactory() throws ImpossibleTileException {
        Tile tile = factory.makeTile(informationGenerator);
        Terrain[] terrains = tile.getArrayOfTerrains();

        for(int i = 0; i < terrains.length; i++) {
            Assert.assertEquals(informationGenerator.getTerrains()[i], terrains[i]);
        }
    }

    @Test
    public void theTileTerrainsShouldBeThoseFromTheFactory() {

    }

    @Test(expected = InvalidParameterException.class)
    public void theFirstTerrainCannotBeOtherThanTheVolcano() throws ImpossibleTileException{
        informationGenerator.setTerrains(Terrain.LAKE, Terrain.VOLCANO, Terrain.GRASSLANDS);
        factory.makeTile(informationGenerator);
    }

    @Test(expected = ImpossibleTileException.class)
    public void firstLocationNotPossibleInAConnectedTile() throws ImpossibleTileException {
        Location loc1 = new Location(2, 1);
        Location loc2 = new Location(0, 0);
        Location loc3 = new Location(1, 0);
        informationGenerator.setLocations(loc1, loc2, loc3);
        factory.makeTile(informationGenerator);
    }

    @Test(expected = ImpossibleTileException.class)
    public void secondLocationNotPossibleInAConnectedTile() throws ImpossibleTileException {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 2);
        Location loc3 = new Location(1, 0);
        informationGenerator.setLocations(loc1, loc2, loc3);
        factory.makeTile(informationGenerator);
    }

    @Test(expected = ImpossibleTileException.class)
    public void thirdLocationNotPossibleInAConnectedTile() throws ImpossibleTileException {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 1);
        Location loc3 = new Location(2, 0);
        informationGenerator.setLocations(loc1, loc2, loc3);
        factory.makeTile(informationGenerator);
    }

    @Test(expected = ImpossibleTileException.class)
    public void thereCannotBeMoreThanOneVolcanoPerTile() throws  ImpossibleTileException {
        informationGenerator.setTerrains(Terrain.VOLCANO, Terrain.VOLCANO, Terrain.LAKE);
        factory.makeTile(informationGenerator);
    }

    @Test(expected = ImpossibleTileException.class)
    public void thereCannotBeTwoEqualLocationsWithinAtile() throws ImpossibleTileException {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 0);
        Location loc3 = new Location(-1, 1);
        informationGenerator.setLocations(loc1, loc2, loc3);
        factory.makeTile(informationGenerator);
    }
}
