package TerrainTest;

import Terrain.TerrainLocation.TerrainLocation;
import Terrain.TerrainMovement.AxialMovement;
import Terrain.TerrainMovement.TerrainMovement;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AxialMovementTest {
    TerrainLocation currentLocation;
    TerrainLocation expectedLocation;
    static TerrainMovement coordinateSystem;

    @BeforeClass
    public static void initializeCoordinateSystemAsAxial(){
        coordinateSystem = new AxialMovement();
    }

    boolean terrainsAreEqual(TerrainLocation locationOne, TerrainLocation locationTwo) {
        return locationOne.getY() == locationTwo.getY() && locationOne.getX() == locationTwo.getX();
    }

    @Test
    public void TerrainLocationsThatAreAtTheSameColumnAndRowShouldBeEqual(){
        givenIHaveALocation(2,3);
        andIHaveAnotherLocation(2,3);
        Assert.assertTrue(terrainsAreEqual(expectedLocation, currentLocation));

        givenIHaveALocation(6, 4);
        andIHaveAnotherLocation(4,3);
        Assert.assertFalse(terrainsAreEqual(expectedLocation, currentLocation));
    }

    void givenIHaveALocation(int row, int column) {
        currentLocation = new TerrainLocation(column, row);
    }

    void andIHaveAnotherLocation(int row, int column) {
        expectedLocation = new TerrainLocation(column, row);
    }

    void givenIAmAtLocation(int row, int column){
        currentLocation = new TerrainLocation(column, row);
    }

    void thenIShouldArriveAt(int row, int column){
        expectedLocation = new TerrainLocation(column, row);
        Assert.assertTrue(terrainsAreEqual(currentLocation, expectedLocation));
    }

    @Test
    public void MovingUpwards() {
        givenIAmAtLocation(5, 4);
        whenIMoveUp();
        thenIShouldArriveAt(6, 4);
    }

    void whenIMoveUp(){
        currentLocation = coordinateSystem.up(currentLocation);
    }

    @Test
    public void MovingUpRight() {
        givenIAmAtLocation(2, 2);
        whenIMoveUpRight();
        thenIShouldArriveAt(2, 3);
    }

    void whenIMoveUpRight() {
        currentLocation = coordinateSystem.upRight(currentLocation);
    }

    @Test
    public void MovingDownRight() {
        givenIAmAtLocation(3, 5);
        whenIMoveDownRight();
        thenIShouldArriveAt(2, 6);
    }

    void whenIMoveDownRight() {
        currentLocation = coordinateSystem.downRight(currentLocation);
    }

    @Test
    public void MovingDown() {
        givenIAmAtLocation(2, 3);
        whenIMoveDown();
        thenIShouldArriveAt(1, 3);
    }

    void whenIMoveDown() {
        currentLocation = coordinateSystem.down(currentLocation);
    }

    @Test
    public void MovingDownLeft() {
        givenIAmAtLocation(0, 0);
        whenIMoveDownLeft();
        thenIShouldArriveAt(0, -1);
    }

    void whenIMoveDownLeft() {
        currentLocation = coordinateSystem.downLeft(currentLocation);
    }

    @Test
    public void MovingUpLeft() {
        givenIAmAtLocation(0, 0);
        whenIMoveUpLeft();
        thenIShouldArriveAt(1, -1);
    }

    void whenIMoveUpLeft() {
        currentLocation = coordinateSystem.upLeft(currentLocation);
    }
}
