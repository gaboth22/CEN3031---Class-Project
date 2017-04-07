package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.TileStringToTerrain;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;
import Terrain.Terrain.Terrain;

public class TileStringToTerrainTest {

    @Test(expected = InvalidParameterException.class)
    public void testThatAnInvalidStringThrowsAnException() {
        String invalidString = "Invalid String";
        TileStringToTerrain.getTerrain(invalidString);
    }

    @Test
    public void aValidStringTerrainReturnsValidTerrainType() {
        String validString = "GRASS";
        Terrain terrainToTest = TileStringToTerrain.getTerrain(validString);
        Assert.assertEquals(Terrain.GRASSLANDS, terrainToTest);
    }

    @Test(expected = InvalidParameterException.class)
    public void testThatNullParameterThrows() {
        String nullString = null;
        TileStringToTerrain.getTerrain(nullString);
    }
}
