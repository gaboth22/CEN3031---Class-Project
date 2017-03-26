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
}
