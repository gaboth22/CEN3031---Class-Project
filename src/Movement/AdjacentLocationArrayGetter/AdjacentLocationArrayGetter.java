package Movement.AdjacentLocationArrayGetter;

import Location.Location;
import Movement.*;

public class AdjacentLocationArrayGetter {
    private static Movement movement = new AxialMovement();

    public static Location[] getArrayOfAdjacentLocationsTo(Location loc) {
        return new Location[] { movement.up(loc),
                movement.upRight(loc),
                movement.downRight(loc),
                movement.down(loc),
                movement.downLeft(loc),
                movement.upLeft(loc)};
    }
}
