package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Settlements.Creation.Settlement;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;

import java.util.*;

public class NukeableVolcanoAroundSettlementListGetter {

    private Map<Location, Hexagon> hexMap;
    private Settlement settlement;

    public List<Location> getList(Settlement s, Map<Location, Hexagon> hexMap) {

        this.hexMap = hexMap;
        this.settlement = s;
        Set<Location> setOfNukeableVolcanoesAroundSettlement = new HashSet<Location>();
        Set<Location> locationsInSettlement = s.getSetOfLocationsInSettlement();

        for(Location l : locationsInSettlement) {

            Location[] adjacentToLocationInSettlement = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(l);

            for(Location adj : adjacentToLocationInSettlement) {
                if (    isAPlacedHex(adj) &&
                        notInSettlement(adj) &&
                        isVolcano(adj)
                        )
                    setOfNukeableVolcanoesAroundSettlement.add(adj);
            }
        }

        return new ArrayList<Location>(setOfNukeableVolcanoesAroundSettlement);
    }

    private boolean isAPlacedHex(Location l) {
        return hexMap.containsKey(l);
    }

    private boolean isVolcano(Location l) {
        return hexMap.get(l).getTerrain() == Terrain.VOLCANO;
    }

    private boolean notInSettlement(Location l) {
        return !settlement.locationIsInSettlement(l);
    }
}
