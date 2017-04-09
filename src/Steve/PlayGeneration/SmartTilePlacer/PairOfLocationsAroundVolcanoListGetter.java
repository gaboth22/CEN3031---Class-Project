package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import TileMap.Hexagon;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PairOfLocationsAroundVolcanoListGetter {

    public static List<Integer> get(Map<Location, Hexagon> hexMap, Location[] adjacents) {

        LinkedList<Integer> adjacentPairLocationIndices = new LinkedList<Integer>();

        for (int i = 1; i <= adjacents.length; i++) {

            if(i == adjacents.length) {
                /*
                    Needed to do a backwards compare between
                    the last and the first indices
                 */
                if (hexMap.containsKey(adjacents[0]) && hexMap.containsKey(adjacents[i - 1])) {
                    adjacentPairLocationIndices.add(i - 1);
                    adjacentPairLocationIndices.add(0);
                }

                break;
            }

            if (hexMap.containsKey(adjacents[i]) && hexMap.containsKey(adjacents[i - 1])) {
                adjacentPairLocationIndices.add(i - 1);
                adjacentPairLocationIndices.add(i);
            }
        }

        return adjacentPairLocationIndices;
    }
}
