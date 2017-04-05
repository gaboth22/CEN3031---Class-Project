package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.TerrainToStringConverter;
import Terrain.Terrain.Terrain;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class TerrainToStringConverterTest {

    @Test
    public void conversionShouldBeRightForRocky() {
        String converted = TerrainToStringConverter.getString(Terrain.ROCKY);
        Assert.assertEquals("ROCK", converted);
    }

    @Test
    public void conversionShouldBeRightForJungle() {
        String converted = TerrainToStringConverter.getString(Terrain.JUNGLE);
        Assert.assertEquals("JUNGLE", converted);
    }

    @Test
    public void conversionShouldBeRightForGrasslands() {
        String converted = TerrainToStringConverter.getString(Terrain.GRASSLANDS);
        Assert.assertEquals("GRASS", converted);
    }

    @Test
    public void conversionShouldBeRightForLake() {
        String converted = TerrainToStringConverter.getString(Terrain.LAKE);
        Assert.assertEquals("LAKE", converted);
    }

    @Test(expected = InvalidParameterException.class)
    public void converterShouldThrowSinceVolcanoIsNeverToBeSentToServerSoNoNeedForConversion() {
        TerrainToStringConverter.getString(Terrain.VOLCANO);
    }
}
