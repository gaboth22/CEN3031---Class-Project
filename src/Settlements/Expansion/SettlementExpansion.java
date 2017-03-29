package Settlements.Expansion;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.Terrain;

import java.util.*;

public class SettlementExpansion {

    public int numberOfVillagersRequiredForExpansion(Map<Location, Hexagon> hexMap,
                                                     GamePieceMap pieceMap,
                                                     Settlement settlement,
                                                     Terrain terrain) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        Set<Location> locationsToExpandTo = findLocationsToExpandInto(hexMap, locationsInSettlement, pieceMap, terrain);
        return 0;
    }

    private Set<Location> findLocationsToExpandInto(Map<Location, Hexagon> hexMap,
                                                    Set<Location> locationsInSettlement,
                                                    GamePieceMap pieceMap,
                                                    Terrain terrainToExpand) {

        Set<Location> locationsToExpandInto = new HashSet<Location>();

        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        Set<Location> locationsVisited = locationsInSettlement;

        while(!locationsToCheck.isEmpty()) {

        }
        return null;
    }


}
