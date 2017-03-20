package TerrainTest.TerrainPositionTest;

import Terrain.TerrainLocation.TerrainLocation;
import Terrain.TerrainPosition.TerrainPosition;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class TerrainPositionTest {
    TerrainPosition position;
    TerrainLocation location;
    int height;

    public void givenIHaveATerrainLocationWithRowAndCol(int row, int col) {
        location = new TerrainLocation(row, col);
    }

    public void givenTheHeightIsSetTo(int height) {
        this.height = height;
    }

    public void whenThePositionIsInitialized() {
        position = new TerrainPosition(location, height);
    }

    public void thePositionRowShouldBe(int expectedRow) {
        Assert.assertEquals(expectedRow, position.getRow());
    }

    public void thePositionColumnShouldBe(int expectedCol) {
        Assert.assertEquals(expectedCol, position.getCol());
    }

    public void thePositionHeightShouldBe(int expectedHeight) {
        Assert.assertEquals(expectedHeight, position.getHeight());
    }

    @Test(expected = InvalidParameterException.class)
    public void theHeightShouldNeverBeNegative() {
        givenIHaveATerrainLocationWithRowAndCol(3, 2);
        givenTheHeightIsSetTo(-1);
        whenThePositionIsInitialized();
    }

    @Test
    public void theRowShouldBeThatOfTheLocation() {
        givenIHaveATerrainLocationWithRowAndCol(3, 4);
        givenTheHeightIsSetTo(1);
        whenThePositionIsInitialized();
        thePositionRowShouldBe(location.getX());
    }

    @Test
    public void theColumnShouldBeThatOfTheLocation() {
        givenIHaveATerrainLocationWithRowAndCol(0, 1);
        givenTheHeightIsSetTo(1);
        whenThePositionIsInitialized();
        thePositionColumnShouldBe(location.getY());
    }

    @Test
    public void theHeightShouldBeThatWhichWasSet() {
        givenIHaveATerrainLocationWithRowAndCol(0, 1);
        givenTheHeightIsSetTo(3);
        whenThePositionIsInitialized();
        thePositionHeightShouldBe(height);
    }
}
