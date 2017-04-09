package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import TileMap.Hexagon;
import java.util.Map;

public class LocationsBelongToTwoDifferentTiles {

    public static boolean check(
            Location l1,
            Location l2,
            Map<Location, Hexagon> hexMap) {

        Hexagon hex1 = hexMap.get(l1);
        int hex1ParentId = hex1.getParentTileID();

        Hexagon hex2 = hexMap.get(l2);
        int hex2ParentId = hex2.getParentTileID();

        return hex1ParentId != hex2ParentId;
    }

}
