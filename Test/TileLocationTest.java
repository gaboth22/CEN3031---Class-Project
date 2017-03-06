import org.junit.Assert;
import org.junit.Test;

public class TileLocationTest {
    TileLocation uut;

    public void givenTheTileLocationHasBeenInitializedWithColAndRowData(int col, int row) {
        uut = new TileLocation(col, row);
    }

    public void theColumnDataShouldBe(int expectedColumnData) {
        Assert.assertEquals(expectedColumnData, uut.column);
    }

    public void theRowDataShouldBe(int expectedRowData) {
        Assert.assertEquals(expectedRowData, uut.row);
    }

    @Test
    public void tileLocationDataShouldBeSetCorrectly() {
        givenTheTileLocationHasBeenInitializedWithColAndRowData(0, 3);
        theColumnDataShouldBe(0);
        theRowDataShouldBe(3);
    }
}
