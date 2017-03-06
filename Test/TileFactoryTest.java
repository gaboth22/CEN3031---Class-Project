import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileFactoryTest {
    TileFactory uut;
    TileInformationGeneratorTestDouble infoGenTestDouble;

    @Before
    public void initializeInstances() {
        infoGenTestDouble = new TileInformationGeneratorTestDouble();
        infoGenTestDouble.setLeftTerrainType(TerrainType.GRASSLANDS);
        infoGenTestDouble.setRightTerrainType(TerrainType.LAKE);
        infoGenTestDouble.setLocationColumn(4);
        infoGenTestDouble.setLocationRow(2);
        infoGenTestDouble.setOrientation(Orientation.SW);
        uut = new TileFactory(infoGenTestDouble);
    }

    @Test
    public void theTileFactoryShouldCreateAValidTile() {
        Tile tile = uut.makeTile();
        Assert.assertTrue(tile.leftTerrain != null);
        Assert.assertTrue(tile.rightTerrain != null);
        Assert.assertTrue(tile.orientation != null);
        Assert.assertTrue(tile.location != null);
    }

    @Test
    public void theOrientationInTheNewTileShouldBeTheOneFromTheTestDouble() {
        Tile tile = uut.makeTile();
        Assert.assertEquals(Orientation.SW, tile.orientation.orientation);
    }

    @Test
    public void theLeftTerrainTypeInThenNewTileShouldBeTheOneFromTheTestDouble() {
        Tile tile = uut.makeTile();
        Assert.assertEquals(TerrainType.GRASSLANDS, tile.leftTerrain.type);
    }

    @Test
    public void theRightTerrainTypeInThenNewTileShouldBeTheOneFromTheTestDouble() {
        Tile tile = uut.makeTile();
        Assert.assertEquals(TerrainType.LAKE, tile.rightTerrain.type);
    }

    @Test
    public void theLocationColumnInTheNewTileShouldBeTheOneFromTheTestDouble() {
        Tile tile = uut.makeTile();
        Assert.assertEquals(4, tile.location.column);
    }

    @Test
    public void theLocationRowInTheNewTileShouldBeTheOneFromTheTestDouble() {
        Tile tile = uut.makeTile();
        Assert.assertEquals(2, tile.location.row);
    }
}