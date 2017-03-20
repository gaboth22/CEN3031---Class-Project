package PositionTest;

import Location.Location;
import Position.Position;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class PositionTest {
    Position position;
    Location location;
    int height;

    public void givenIHaveATerrainLocationWithRowAndCol(int x, int y) {
        location = new Location(x, y);
    }

    public void givenTheHeightIsSetTo(int height) {
        this.height = height;
    }

    public void whenThePositionIsInitialized() {
        position = new Position(location, height);
    }

    public void thePositionXShouldBe(int expectedX) {
        Assert.assertEquals(expectedX, position.getX());
    }

    public void thePositionYShouldBe(int expectedY) {
        Assert.assertEquals(expectedY, position.getY());
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
    public void xShouldBeThatOfTheLocation() {
        givenIHaveATerrainLocationWithRowAndCol(3, 4);
        givenTheHeightIsSetTo(1);
        whenThePositionIsInitialized();
        thePositionXShouldBe(location.getX());
    }

    @Test
    public void yShouldBeThatOfTheLocation() {
        givenIHaveATerrainLocationWithRowAndCol(0, 1);
        givenTheHeightIsSetTo(1);
        whenThePositionIsInitialized();
        thePositionYShouldBe(location.getY());
    }

    @Test
    public void theHeightShouldBeThatWhichWasSet() {
        givenIHaveATerrainLocationWithRowAndCol(0, 1);
        givenTheHeightIsSetTo(3);
        whenThePositionIsInitialized();
        thePositionHeightShouldBe(height);
    }
}
