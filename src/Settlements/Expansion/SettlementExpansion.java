package Settlements.Expansion;

import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.Terrain;
import GamePieceMap.*;

import java.util.*;

public class SettlementExpansion {

    public static int numVillagersRequiredToExpansion(Map<Location, Hexagon> hexMap, List<Location> locationsToExpandTo) {

        return costOfExpansion(hexMap, locationsToExpandTo);
    }

    private static int costOfExpansion(Map<Location, Hexagon> hexMap, List<Location> locationsToExpandTo) {
        int totalNumberOfVillagersRequired = 0;
        for(int i = 0; i < locationsToExpandTo.size(); i++) {
            if(hexMap.containsKey(locationsToExpandTo.get(i))) {
                totalNumberOfVillagersRequired += hexMap.get(locationsToExpandTo.get(i)).getHeight();
            }
        }
        return totalNumberOfVillagersRequired;
    }

    public static List<Location> findLocationsToExpandInto(Map<Location, Hexagon> hexMap,
                                                           Settlement settlement,
                                                           GamePieceMap pieceMap,
                                                           Terrain terrainToExpand) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
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

    public static void expandSettlement(GamePieceMap pieceMap,
                                        Settlement settlement,
                                        List<Location> locationsToExpandTo) throws LocationNotEmptyException {

        expandSettlementFromList(pieceMap, locationsToExpandTo, settlement);
    }

    private static void expandSettlementFromList(GamePieceMap pieceMap, List<Location> locationsToExpandTo, Settlement settlement) throws LocationNotEmptyException {

        PlayerID id = settlement.getSettlementOwner();
        TypeOfPiece pieceType = TypeOfPiece.VILLAGER;

        for(Location toExpandTo : locationsToExpandTo) {
            GamePiece gamePiece = new GamePiece(id, pieceType);
            pieceMap.insertAPieceAt(toExpandTo, gamePiece);
        }
    }
}
