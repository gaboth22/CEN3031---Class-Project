package Movement;

import Location.Location;

public class AxialMovement implements Movement {

    @Override
    public Location up(Location loc) {
        return new Location(loc.getX(), loc.getY() + 1);
    }

    @Override
    public Location upRight(Location loc) {
        return new Location(loc.getX() + 1, loc.getY());
    }

    @Override
    public Location downRight(Location loc) {
        return new Location(loc.getX() + 1, loc.getY() - 1);
    }

    @Override
    public Location down(Location loc) {
        return new Location(loc.getX(), loc.getY() - 1);
    }

    @Override
    public Location downLeft(Location loc) {
        return new Location(loc.getX() - 1, loc.getY());
    }

    @Override
    public Location upLeft(Location loc) {
        return new Location(loc.getX() - 1, loc.getY() + 1);
    }

}
