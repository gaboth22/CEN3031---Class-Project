package Settlements.Expansion;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.TileMap;
import Terrain.Terrain.Terrain;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class SettlementExpansion {

    public int numberOfVillagersRequiredForExpansion(TileMap tileMap,
                                                     GamePieceMap pieceMap,
                                                     Settlement settlement,
                                                     Terrain terrain) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        Set<Location> locationsToExpandTo = findLocationsToExpandInto(tileMap, locationsInSettlement, terrain);
        return 0;
    }

    private Set<Location> findLocationsToExpandInto(TileMap tileMap, Set<Location> locationsInSettlement, Terrain terrainToExpand) {

        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        Set<Location> locationsVisited = locationsInSettlement;

        while(!locationsToCheck.isEmpty()) {

        }
        return null;
    }

}
