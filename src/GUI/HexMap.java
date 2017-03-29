package GUI;

import Location.Location;
import java.util.HashMap;
import java.util.Map;

public class HexMap {

    private  HashMap<Location, Coordinate> map;
    private  HashMap<Coordinate, Location> map2;

    public HexMap() {
        map = new HashMap<Location, Coordinate>();

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 9), new Coordinate(405 + (45 * x), -175 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 9), new Coordinate(360 - (45 * -(x+1)), -150 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 8), new Coordinate(405 + (45 * x), -125 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 8), new Coordinate(360 - (45 * -(x+1)), -100 + (25 * -(x+1))));
        }


        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 7), new Coordinate(405 + (45 * x), -75 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 7), new Coordinate(360 - (45 * -(x+1)), -50 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 6), new Coordinate(405 + (45 * x), -25 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 6), new Coordinate(360 - (45 * -(x+1)), 0 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 5), new Coordinate(405 + (45 * x), 25 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 5), new Coordinate(360 - (45 * -(x+1)), 50 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 4), new Coordinate(405 + (45 * x), 75 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 4), new Coordinate(360 - (45 * -(x+1)), 100 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 3), new Coordinate(405 + (45 * x), 125 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 3), new Coordinate(360 - (45 * -(x+1)), 150 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 2), new Coordinate(405 + (45 * x), 175 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 2), new Coordinate(360 - (45 * -(x+1)), 200 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 1 ), new Coordinate(405 + (45 * x), 225 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 1 ), new Coordinate(360 - (45 * -(x+1)), 250 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, 0 ), new Coordinate(405 + (45 * x), 275 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, 0 ), new Coordinate(360 - (45 * -(x+1)), 300 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -1 ), new Coordinate(405 + (45 * x), 325 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -1 ), new Coordinate(360 - (45 * -(x+1)), 350 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -2 ), new Coordinate(405 + (45 * x), 375 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -2 ), new Coordinate(360 - (45 * -(x+1)), 400 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -3 ), new Coordinate(405 + (45 * x), 425 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -3 ), new Coordinate(360 - (45 * -(x+1)), 450 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -4 ), new Coordinate(405 + (45 * x), 475 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -4 ), new Coordinate(360 - (45 * -(x+1)), 500 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -5 ), new Coordinate(405 + (45 * x), 525 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -5 ), new Coordinate(360 - (45 * -(x+1)), 550 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -6 ), new Coordinate(405 + (45 * x), 575 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -6 ), new Coordinate(360 - (45 * -(x+1)), 600 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -7 ), new Coordinate(405 + (45 * x), 625 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -7 ), new Coordinate(360 - (45 * -(x+1)), 650 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -8 ), new Coordinate(405 + (45 * x), 675 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -8 ), new Coordinate(360 - (45 * -(x+1)), 700 + (25 * -(x+1))));
        }

        for(int x = 0; x < 10; x++) {
            map.put(new Location(x, -9 ), new Coordinate(405 + (45 * x), 725 - (25 * x)));
        }

        for(int x = -1; x > -10; x--) {
            map.put(new Location(x, -9 ), new Coordinate(360 - (45 * -(x+1)), 750 + (25 * -(x+1))));
        }

        map2 = new HashMap<>();
        for(Map.Entry<Location, Coordinate> entry : map.entrySet()){
            map2.put(entry.getValue(), entry.getKey());
        }
    }

    public Coordinate getCoordinateFromLocation(Location loc) {
        if(map.containsKey(loc))
            return map.get(loc);
        else
            return null;
    }

    public Location getLocationFromCoordinate(Coordinate coord) {
        if(map2.containsKey(coord))
            return map2.get(coord);
        else
            return null;
    }

}
