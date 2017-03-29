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
        Set<Location> locationsToExpandTo = findLocationsToExpandInto(hexMap, locationsInSettlement, pieceMap, terrain);
        return 0;
    }

    private static Set<Location> findLocationsToExpandInto(Map<Location, Hexagon> hexMap,
                                                           Set<Location> locationsInSettlement,
                                                           GamePieceMap pieceMap,
                                                           Terrain terrainToExpand) {

        Set<Location> locationsToExpandInto = new HashSet<Location>();

        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        Set<Location> locationsVisited = locationsInSettlement;

        while (!locationsToCheck.isEmpty()) {
            Location currCheckedLocation = locationsToCheck.remove();

            Location[] toCheck = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(currCheckedLocation);
            for(int i = 0; i < toCheck.length; i++) {
                Location currLocation = toCheck[i];

                boolean isValidLocation = hexMap.containsKey(currLocation) &&
                        hexMap.get(currLocation).getTerrain() == terrainToExpand &&
                        !pieceMap.isThereAPieceAt(currLocation) &&
                        !locationsVisited.contains(currLocation);

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
