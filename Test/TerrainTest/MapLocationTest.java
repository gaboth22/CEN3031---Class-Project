package TerrainTest;

import Terrain.MapLocation;
import org.junit.Assert;
import org.junit.Test;

public class MapLocationTest {

    MapLocation currentLocation;
    MapLocation expectedLocation;

    void givenIAmAtLocation(int row, int column, int height){
        currentLocation = new MapLocation(row, column, height);
    }

    void thenIShouldArriveAt(int row, int column, int height){
        expectedLocation = new MapLocation(row, column, height);
        Assert.assertEquals(currentLocation, expectedLocation);
    }

    void givenIHaveALocation(int row, int column, int height) {
        currentLocation = new MapLocation(row, column, height);
    }

    void andIHaveAnotherLocation(int row, int column, int height) {
        expectedLocation = new MapLocation(row, column, height);
    }

    void whenIMoveUp(){
        currentLocation = currentLocation.up();
    }

    void whenIMoveUpRight() {
        currentLocation = currentLocation.upRight();
    }

    void whenIMoveDownRight() {
        currentLocation = currentLocation.downRight();
    }

    void whenIMoveDown() {
        currentLocation = currentLocation.down();
    }

    void whenIMoveDownLeft() {
        currentLocation = currentLocation.downLeft();
    }

    void whenIMoveUpLeft() {
        currentLocation = currentLocation.upLeft();
    }

    void whenIMoveAbove() {
        currentLocation = currentLocation.above();
    }

    void whenIMoveBelow() {
        currentLocation = currentLocation.below();
    }

    @Test
    public void MapLocationsThatAreAtTheSameColumnAndRowShouldBeEqual(){
        givenIHaveALocation(2,3,4);
        andIHaveAnotherLocation(2,3,4);
        Assert.assertEquals(currentLocation, expectedLocation);

        givenIHaveALocation(6, 4, 6);
        andIHaveAnotherLocation(4,3,2);
        Assert.assertNotEquals(currentLocation, expectedLocation);
    }

    @Test
    public void MovingUpwards() {
        givenIAmAtLocation(5, 4, 0);
        whenIMoveUp();
        thenIShouldArriveAt(6, 4, 0);
    }

    @Test
    public void MovingUpRight() {
        givenIAmAtLocation(2, 2, 2);
        whenIMoveUpRight();
        thenIShouldArriveAt(2, 3, 2);
    }

    @Test
    public void MovingDownRight() {
        givenIAmAtLocation(3, 5, 2);
        whenIMoveDownRight();
        thenIShouldArriveAt(2, 6, 2);
    }

    @Test
    public void MovingDown() {
        givenIAmAtLocation(2, 3, 1);
        whenIMoveDown();
        thenIShouldArriveAt(1, 3, 1);
    }

    @Test
    public void MovingDownLeft() {
        givenIAmAtLocation(0, 0, 0);
        whenIMoveDownLeft();
        thenIShouldArriveAt(0, -1, 0);
    }

    @Test
    public void MovingUpLeft() {
        givenIAmAtLocation(0, 0, 0);
        whenIMoveUpLeft();
        thenIShouldArriveAt(1, -1, 0);
    }

    @Test
    public void MovingAbove() {
        givenIAmAtLocation(5, 5, 20);
        whenIMoveAbove();
        thenIShouldArriveAt(5, 5, 21);
    }

    @Test
    public void MovingBelow() {
        givenIAmAtLocation(0, 0, 1);
        whenIMoveBelow();
        thenIShouldArriveAt(0, 0, 0);
    }
}
