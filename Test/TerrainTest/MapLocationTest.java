package TerrainTest;

import Terrain.MapLocation;
import org.junit.Assert;
import org.junit.Test;

public class MapLocationTest {

    MapLocation currentLocation;
    MapLocation expectedLocation;

    void givenIAmAtLocation(int column, int row, int height){
        currentLocation = new MapLocation(column, row, height);
    }

    void thenIShouldArriveAt(int column, int row, int height){
        expectedLocation = new MapLocation(column, row, height);
        Assert.assertEquals(currentLocation, expectedLocation);
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
        givenIAmAtLocation(4,5, 6);
        thenIShouldArriveAt(4,5, 6);
        Assert.assertTrue(currentLocation.equals(expectedLocation));

        thenIShouldArriveAt(4,6, 6);
        Assert.assertFalse(currentLocation.equals(expectedLocation));
    }

    @Test
    public void MovingUpwards() {
        givenIAmAtLocation(4,5,0);
        whenIMoveUp();
        thenIShouldArriveAt(4, 6, 0);
    }

    @Test
    public void MovingUpRight() {
        givenIAmAtLocation(2,2,2);
        whenIMoveUpRight();
        thenIShouldArriveAt(2,3,2);
    }

    @Test
    public void MovingDownRight() {
        givenIAmAtLocation(5,3,2);
        whenIMoveDownRight();
        thenIShouldArriveAt(6,2,2);
    }

    @Test
    public void MovingDown() {
        givenIAmAtLocation(3,2,1);
        whenIMoveDown();
        thenIShouldArriveAt(3,1,1);
    }

    @Test
    public void MovingDownLeft() {
        givenIAmAtLocation(0,0,0);
        whenIMoveDownLeft();
        thenIShouldArriveAt(-1,0,0);
    }

    @Test
    public void MovingUpLeft() {
        givenIAmAtLocation(0,0,0);
        whenIMoveUpLeft();
        thenIShouldArriveAt(-1, 1, 0);
    }

    @Test
    public void MovingAbove() {
        givenIAmAtLocation(5,5,20);
        whenIMoveAbove();
        thenIShouldArriveAt(5,5,21);
    }

    @Test
    public void MovingBelow() {
        givenIAmAtLocation(0,0,1);
        whenIMoveBelow();
        thenIShouldArriveAt(0,0,0);
    }
}
