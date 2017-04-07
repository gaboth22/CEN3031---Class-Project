package Steve.PlayGeneration;

import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.*;
import GamePieceMap.GamePieceMap;

import java.util.*;

public class TotoroLocationHelper {

    static public void pickTotoroLocation(Map<Location, Hexagon> hexes, List<Settlement> playerSettlements, GamePieceMap pieces) {
        int TOTORO_SETTLEMENT_SIZE = 5;
        Settlement totoroCandidate = null;
        Location totoroLocation = null;
        for (int i = 0; i < playerSettlements.size(); i++) {
            totoroCandidate = playerSettlements.get(i);
            if (totoroCandidate.hasTotoroSanctuary()) {
                continue;
            }
            else if (totoroCandidate.getNumberOfHexesInSettlement() < TOTORO_SETTLEMENT_SIZE) {
                continue;
            }
            totoroLocation = findTotoroLocation(hexes, totoroCandidate, pieces);
            if (totoroLocation != null) {
                //create BuildPhase
            }
        }
    }
    static public Location findTotoroLocation(Map<Location, Hexagon> hexes, Settlement settlement, GamePieceMap pieces) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);

        while (!locationsToCheck.isEmpty()) {
            Location currCheckedLocation = locationsToCheck.remove();

            Location[] toCheck = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(currCheckedLocation);
            for(int i = 0; i < toCheck.length; i++) {
                Location currLocation = toCheck[i];

                boolean isValidLocation = hexes.containsKey(currLocation) &&
                        !pieces.isThereAPieceAt(currLocation) &&
                        (hexes.get(currLocation)).getTerrain() != Terrain.VOLCANO;

                if(isValidLocation) {
                    return currLocation;
                }
            }
        }

        return null;
    }
}
