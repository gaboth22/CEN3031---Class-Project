package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.ExtraCoordinateGenerator;
import org.junit.Assert;
import org.junit.Test;

public class ExtraCoordinateGeneratorTest {

    /*
        The extra coordinate is always -(x + y) --> regardless of the actual mapping of x and y
     */

    @Test
    public void extraCoordinateShouldReturnARightValue() {
        int x = 3;
        int y = -1;

        int extraCoordinate = ExtraCoordinateGenerator.generate(x, y);

        Assert.assertEquals(-2, extraCoordinate);
    }
}
