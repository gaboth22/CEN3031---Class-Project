package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Settlements.Creation.Settlement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjacentLocationsToSettlementGetter {

    public List<Location> getAdjacentLocationsToSettlement(Settlement s) {

        Set<Location> locationsInSettlement = s.getSetOfLocationsInSettlement();
        Set<Location> foundAdjacentLocationsToSettlement = new HashSet<Location>();

        for(Location loc : locationsInSettlement) {

            Location[] adjacentLocationsToCurrentLocationInSettlement =
                    AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(loc);

            for(Location currentAdjacentLocation : adjacentLocationsToCurrentLocationInSettlement) {
                if(!s.locationIsInSettlement(currentAdjacentLocation))
                    foundAdjacentLocationsToSettlement.add(currentAdjacentLocation);
            }
        }

        return new ArrayList<Location>(foundAdjacentLocationsToSettlement);
    }
}
