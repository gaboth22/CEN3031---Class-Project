package Settlements.Creation;

import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Player.PlayerID;
import Settlements.SettlementException.SettlementException;

import java.util.*;

public class SettlementCreator {

    public static Settlement getSettlementAt(GamePieceMap pieceMap, Location location) throws SettlementException {
        if(pieceMap.isThereAPieceAt(location)) {
            return createSettlement(pieceMap, location);
        }
        return new Settlement();
    }

    private static Settlement createSettlement(GamePieceMap pieceMap, Location location) throws SettlementException {
        List<Location> locationsInSettlement = getLocationsInSettlement(pieceMap, location);
        return createSettlementWithLocations(pieceMap, locationsInSettlement);
    }

    private static Settlement createSettlementWithLocations(GamePieceMap pieceMap, List<Location> locations) throws SettlementException {
        Settlement newSettlement = new Settlement();
        for(int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            PlayerID playerID = pieceMap.getPieceOwnerIdAtLocation(location);
            TypeOfPiece pieceType = pieceMap.getPieceTypeAtLocation(location);

            GamePiece toAdd = new GamePiece(playerID, pieceType);
            newSettlement.addPieceToSettlement(locations.get(i), toAdd);
        }
        return newSettlement;
    }

    private static List<Location> getLocationsInSettlement(GamePieceMap pieceMap, Location location) {
        List<Location> settlementLocations = new ArrayList<Location>();
        PlayerID playerIDOfSettlement = pieceMap.getPieceOwnerIdAtLocation(location);

        Queue<Location> locationsToCheck = new LinkedList<Location>();
        Set<Location> visitedLocations = new HashSet<Location>();

        locationsToCheck.add(location);
        while(locationsToCheck.size() > 0) {
            Location locationToCheck = locationsToCheck.remove();

            boolean pieceIsValid = pieceMap.isThereAPieceAt(locationToCheck)
                    && pieceMap.getPieceOwnerIdAtLocation(locationToCheck) == playerIDOfSettlement;
            if(pieceIsValid) {
                settlementLocations.add(locationToCheck);

                //Add adjacent Locations if they have not already been visited
                Location[] adjacentLocations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationToCheck);
                for(int i = 0; i < adjacentLocations.length; i++) {
                    if (!visitedLocations.contains(adjacentLocations[i])) {
                        locationsToCheck.add(adjacentLocations[i]);
                        visitedLocations.add(adjacentLocations[i]);
                    }
                }
            }
        }

        return settlementLocations;
    }
}
