import org.junit.Assert;
import org.junit.Test;

public class TileOrientationTest {
    public TileOrientation uut;

    public void givenTheTileOrientationHasBeenInitializedWithOrientation(Orientation orientation) {
        uut = new TileOrientation(orientation);
    }

    public void theOrientationShouldBe(Orientation expectedOrientation) {
        Assert.assertEquals(expectedOrientation, uut.orientation);
    }

    @Test
    public void theTileOrientationDataShouldBeSetCorrectly() {
        givenTheTileOrientationHasBeenInitializedWithOrientation(Orientation.NE);
        theOrientationShouldBe(Orientation.NE);
    }
}
