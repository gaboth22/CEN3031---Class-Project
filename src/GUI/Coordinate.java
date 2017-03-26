package GUI;

public class Coordinate implements Pair {
    public int x;
    public int y;
    /*
     * Needed for serialization
     */
    private static final long serialVersionUID = -1306760703066967345L;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate coord = (Coordinate) o;

        if (y != coord.y) return false;
        return x == coord.x;
    }

    @Override
    public int hashCode() {
        int result = y;
        result = 31 * result + x;
        return result;
    }
}
