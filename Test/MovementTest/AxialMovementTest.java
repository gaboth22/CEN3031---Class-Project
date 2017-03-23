package MovementTest;

import Location.Location;
import Movement.AxialMovement;
import Movement.Movement;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AxialMovementTest {
    Location currentLocation;
    Location expectedLocation;
    static Movement coordinateSystem;

    @BeforeClass
    public static void initializeCoordinateSystemAsAxial(){
        coordinateSystem = new AxialMovement();
    }

    boolean terrainsAreEqual(Location locationOne, Location locationTwo) {
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

    void givenIHaveALocation(int x, int y) {
        currentLocation = new Location(x, y);
    }

    void andIHaveAnotherLocation(int x, int y) {
        expectedLocation = new Location(x, y);
    }

    void givenIAmAtLocation(int x, int y){
        currentLocation = new Location(x, y);
    }

    void thenIShouldArriveAt(int x, int y){
        expectedLocation = new Location(x, y);
        Assert.assertTrue(terrainsAreEqual(currentLocation, expectedLocation));
    }

    @Test
    public void MovingUpwards() {
        givenIAmAtLocation(5, 4);
        whenIMoveUp();
        thenIShouldArriveAt(5, 5);
    }

    void whenIMoveUp(){
        currentLocation = coordinateSystem.up(currentLocation);
    }

    @Test
    public void MovingUpRight() {
        givenIAmAtLocation(2, 2);
        whenIMoveUpRight();
        thenIShouldArriveAt(3, 2);
    }

    void whenIMoveUpRight() {
        currentLocation = coordinateSystem.upRight(currentLocation);
    }

    @Test
    public void MovingDownRight() {
        givenIAmAtLocation(3, 5);
        whenIMoveDownRight();
        thenIShouldArriveAt(4, 4);
    }

    void whenIMoveDownRight() {
        currentLocation = coordinateSystem.downRight(currentLocation);
    }

    @Test
    public void MovingDown() {
        givenIAmAtLocation(2, 3);
        whenIMoveDown();
        thenIShouldArriveAt(2, 2);
    }

    void whenIMoveDown() {
        currentLocation = coordinateSystem.down(currentLocation);
    }

    @Test
    public void MovingDownLeft() {
        givenIAmAtLocation(0, 0);
        whenIMoveDownLeft();
        thenIShouldArriveAt(-1, 0);
    }

    void whenIMoveDownLeft() {
        currentLocation = coordinateSystem.downLeft(currentLocation);
    }

    @Test
    public void MovingUpLeft() {
        givenIAmAtLocation(0, 0);
        whenIMoveUpLeft();
        thenIShouldArriveAt(-1, 1);
    }

    void whenIMoveUpLeft() {
        currentLocation = coordinateSystem.upLeft(currentLocation);
    }

    @Test
    public void testEquals_Symmetric() {
        Location locationOne = new Location(0,0);
        Location locationTwo = new Location(0,0);
        Assert.assertTrue(locationOne.equals(locationTwo) == locationTwo.equals(locationTwo));
        Assert.assertTrue(locationOne.hashCode() == locationTwo.hashCode());
    }

    @Test
    public void movingInAnyDirectionShouldNotChangeTheOriginalLocationReference() {
        Location original = new Location(0,0);
        Assert.assertNotEquals(original, coordinateSystem.up(original));
        Assert.assertNotEquals(original, coordinateSystem.upLeft(original));
        Assert.assertNotEquals(original, coordinateSystem.downLeft(original));
        Assert.assertNotEquals(original, coordinateSystem.down(original));
        Assert.assertNotEquals(original, coordinateSystem.downRight(original));
        Assert.assertNotEquals(original, coordinateSystem.upRight(original));
    }
}
