package Settlements.Helper;

import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Settlements.Creation.Settlement;

import java.util.HashSet;
import java.util.Set;

public class AdjacentLocationsToSettlement {

    public static Location[] getLocationsAdjacentToSettlement(Settlement settlement) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();

        Set<Location> returnSet = new HashSet<Location>();

        for(Location locationIn : locationsInSettlement) {
            Location[] locations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationIn);
            for(Location adjacent : locations) {
                if(!locationsInSettlement.contains(adjacent)) {
                    returnSet.add(adjacent);
                }
            }
        }

        return returnSet.toArray(new Location[returnSet.size()]);
    }
}
