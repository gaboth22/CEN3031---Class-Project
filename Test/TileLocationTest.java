import org.junit.Assert;
import org.junit.Test;

public class TileLocationTest {
    TileLocation uut;

    public void GivenTheTileLocationHasBeenInitializedWithColAndRowData(int col, int row) {
        uut = new TileLocation(col, row);
    }

    public void TheColumnDataShouldBe(int expectedColumnData) {
        Assert.assertEquals(expectedColumnData, uut.column);
    }

    public void TheRowDataShouldBe(int expectedRowData) {
        Assert.assertEquals(expectedRowData, uut.row);
    }

    @Test
    public void TileLocationDataShouldBeSetCorrectly() {
        GivenTheTileLocationHasBeenInitializedWithColAndRowData(0, 3);
        TheColumnDataShouldBe(0);
        TheRowDataShouldBe(3);
    }
}
