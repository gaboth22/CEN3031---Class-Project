package Settlements.Expansion;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.Terrain;

import java.util.*;

public class SettlementExpansion {

    public static int numberOfVillagersRequiredForExpansion(Map<Location, Hexagon> hexMap,
                                                            GamePieceMap pieceMap,
                                                            Settlement settlement,
                                                            Terrain terrain) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        List<Location> locationsToExpandTo = findLocationsToExpandInto(hexMap, locationsInSettlement, pieceMap, terrain);

        return costOfExpansion(hexMap, locationsToExpandTo);
    }

    private static int costOfExpansion(Map<Location, Hexagon> hexMap, List<Location> locationsToExpandTo) {
        int totalNumberOfMeeples = 0;
        for(int i = 0; i < locationsToExpandTo.size(); i++) {
            if(hexMap.containsKey(locationsToExpandTo.get(i))) {
                totalNumberOfMeeples += hexMap.get(locationsToExpandTo.get(i)).getHeight();
            }
        }
        return totalNumberOfMeeples;
    }

    private static List<Location> findLocationsToExpandInto(Map<Location, Hexagon> hexMap,
                                                           Set<Location> locationsInSettlement,
                                                           GamePieceMap pieceMap,
                                                           Terrain terrainToExpand) {

        List<Location> locationsToExpandInto = new ArrayList<Location>();

        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        Set<Location> locationsVisited = locationsInSettlement;

        while (!locationsToCheck.isEmpty()) {
            Location currCheckedLocation = locationsToCheck.remove();

            Location[] toCheck = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(currCheckedLocation);
            for(int i = 0; i < toCheck.length; i++) {
                Location currLocation = toCheck[i];

                boolean isValidLocation = !locationsVisited.contains(currLocation) &&
                        hexMap.containsKey(currLocation) &&
                        hexMap.get(currLocation).getTerrain() == terrainToExpand &&
                        !pieceMap.isThereAPieceAt(currLocation) ;

                if(isValidLocation) {
                    locationsToExpandInto.add(currLocation);
                    locationsToCheck.add(currLocation);
                }
                locationsVisited.add(currLocation);
            }
        }
        return locationsToExpandInto;
    }
}
