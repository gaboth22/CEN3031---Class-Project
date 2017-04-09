package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Settlements.Creation.Settlement;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;

import java.util.*;

public class NukeableVolcanoAroundSettlementListGetter {

    private NukeableVolcanoValidator validator;
    private Map<Location, Hexagon> hexMap;
    private Settlement settlement;

    public NukeableVolcanoAroundSettlementListGetter() {
        validator = new NukeableVolcanoValidator();
    }

    public List<Location> getList(Settlement s, Map<Location, Hexagon> hexMap) {

        this.hexMap = hexMap;
        this.settlement = s;
        Set<Location> setOfNukeableVolcanoesAroundSettlement = new HashSet<Location>();
        Set<Location> locationsInSettlement = s.getSetOfLocationsInSettlement();

        for(Location l : locationsInSettlement) {

            Location[] adjacentToLocationInSettlement = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(l);

            for(Location adj : adjacentToLocationInSettlement) {
                if (    notInSettlement(adj) &&
                        isVolcano(adj) &&
                        validator.isNukeableVolcano(adj, s, hexMap)
                        )
                    setOfNukeableVolcanoesAroundSettlement.add(adj);
            }
        }

        return new ArrayList<Location>(setOfNukeableVolcanoesAroundSettlement);
    }

    private boolean isVolcano(Location l) {
        if(hexMap.containsKey(l))
            return hexMap.get(l).getTerrain() == Terrain.VOLCANO;
        else
            return false;
    }

    private boolean notInSettlement(Location l) {
        return !settlement.locationIsInSettlement(l);
    }
}
