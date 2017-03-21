package Position;

import Location.Location;
import java.security.InvalidParameterException;

public class Position {
    private Location location;
    private int height;
    private String errorMessage = "Position height can never be < 0, and it was set to: ";

    public Position(Location location, int height) throws InvalidParameterException {
        if(height < 0)
            throw new InvalidParameterException(errorMessage + height);

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
}
