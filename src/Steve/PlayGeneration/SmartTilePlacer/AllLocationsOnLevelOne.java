package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import TileMap.Hexagon;
import java.util.Map;

public class AllLocationsOnLevelOne {

    public static boolean check(
            Location l1,
            Location l2,
            Location l3,
            Map<Location, Hexagon> hexMap) {

        Hexagon hex1 = hexMap.get(l1);
        int hex1Height = hex1.getHeight();

        Hexagon hex2 = hexMap.get(l2);
        int hex2Height = hex2.getHeight();

        Hexagon hex3 = hexMap.get(l3);
        int hex3Height = hex3.getHeight();

        return (hex1Height == hex2Height && hex1Height == hex3Height) & (hex1Height == 1);
    }
}
