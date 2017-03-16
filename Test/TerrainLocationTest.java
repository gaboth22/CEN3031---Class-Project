import org.junit.Assert;
import org.junit.Test;

public class TerrainLocationTest {
    private TerrainLocation uut;

    public void givenTheTerrainLocationHasBeenInitialized() {
        this.uut = new TerrainLocation();
    }

    public void givenTheTerrainLocationIsInitializedWithRowAndCol(int row, int col) {
        this.uut.row = row;
        this.uut.column = col;
    }

    public void theRowShouldBe(int expectedRow) {
        Assert.assertEquals(expectedRow, uut.row);
    }

    public void theColumnShouldBe(int expectedCol) {
        Assert.assertEquals(expectedCol, uut.column);
    }

    @Test
    public void terrainLocationDataShouldBeSetCorrectly() {
        givenTheTerrainLocationHasBeenInitialized();
        givenTheTerrainLocationIsInitializedWithRowAndCol(3,2);
        theRowShouldBe(3);
        theColumnShouldBe(2);
    }
}
