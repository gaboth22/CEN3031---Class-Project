package TileTest;

import Terrain.GrasslandsTerrain;
import Terrain.RockyTerrain;
import Tile.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileFactoryTest {
    TileFactory uut;
    TileInformationGeneratorTestDouble infoGenTestDouble;

    @Before
    public void initializeInstances() {
        infoGenTestDouble = new TileInformationGeneratorTestDouble();
        infoGenTestDouble.setLeftTerrain(new RockyTerrain());
        infoGenTestDouble.setRightTerrain(new GrasslandsTerrain());
        infoGenTestDouble.setTileOrientation(new TileOrientationTestDouble());
        uut = new TileFactory(infoGenTestDouble);
    }

    @Test
    public void theTileFactoryShouldCreateAValidTile() {
        Tile tile = uut.makeTile();
        Assert.assertTrue(tile.getLeftTerrain() != null);
        Assert.assertTrue(tile.getRightTerrain() != null);
        Assert.assertTrue(tile.getOrientation() != null);
        Assert.assertTrue(tile.getLocation() != null);
    }
}