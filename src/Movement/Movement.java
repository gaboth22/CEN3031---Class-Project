package Movement;

import Location.Location;

public interface Movement {

    Location up(Location loc);
    Location upRight(Location loc);
    Location downRight(Location loc);
    Location down(Location loc);
    Location downLeft(Location loc);
    Location upLeft(Location loc);
}
