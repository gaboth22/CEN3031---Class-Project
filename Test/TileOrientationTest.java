import org.junit.Assert;
import org.junit.Test;

public class TileOrientationTest {
    public TileOrientation uut;

    public void GiveTheTileOrientationHasBeenInitializedWithOrientation(Orientation orientation) {
        uut = new TileOrientation(orientation);
    }

    public void TheOrientationShouldBe(Orientation expectedOrientation) {
        Assert.assertEquals(expectedOrientation, uut.orientation);
    }

    @Test
    public void TheTileOrientationDataShouldBeSetCorrectly() {
        GiveTheTileOrientationHasBeenInitializedWithOrientation(Orientation.NE);
        TheOrientationShouldBe(Orientation.NE);
    }
}
