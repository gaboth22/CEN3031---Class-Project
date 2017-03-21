package Position;

import Location.Location;
import java.security.InvalidParameterException;

public class Position {
    private Location location;
    private int height;

    public Position(Location location, int height) {
        if(height < 0)
            throw new InvalidParameterException("Position height can never be < 0, and it was set to: " + height);

        this.location = location;
        this.height = height;
    }

    public int getX() {
        return location.getX();
    }

    public int getY() {
        return location.getY();
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (height != position.height) return false;
        return location != null ? location.equals(position.location) : position.location == null;
    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + height;
        return result;
    }
}
