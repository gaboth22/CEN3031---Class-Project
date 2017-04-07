package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import org.junit.Test;

import java.security.InvalidParameterException;

public class TileStringToTerrainTest {

    @Test(expected = InvalidParameterException.class)
    public void testThatAnInvalidStringThrowsAnException() {
        String inalidString = "Invalid String";


    }
}
