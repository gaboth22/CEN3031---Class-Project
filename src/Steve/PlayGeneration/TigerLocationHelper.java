package Steve.PlayGeneration;

import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Location.Location;
import Play.BuildPhase.*;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.*;
import GamePieceMap.*;

import java.util.*;

public class TigerLocationHelper {

    static public BuildPhase pickTigerLocation(Map<Location, Hexagon> hexes, List<Settlement> playerSettlements, GamePieceMap pieces) {
        Settlement tigerCandidate = null;
        Location tigerLocation = null;
        for (int i = 0; i < playerSettlements.size(); i++) {
            tigerCandidate = playerSettlements.get(i);
            if (tigerCandidate.hasTigerPlayground()) {
                continue;
            }
            tigerLocation = findTigerLocation(hexes, tigerCandidate, pieces);
            if (tigerLocation != null) {
                GamePiece tigerToPlace = new GamePiece((playerSettlements.get(i)).getSettlementOwner(), TypeOfPiece.TIGER);
                BuildPhase placeTiger = new BuildPhase(tigerToPlace, tigerLocation);
                placeTiger.setBuildType(BuildType.PLACE_TIGER);
                return placeTiger;
            }
        }
        return null;
    }

    static public Location findTigerLocation(Map<Location, Hexagon> hexes, Settlement settlement, GamePieceMap pieces) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        int TIGER_ADDITION_LEVEL = 3;
        while (!locationsToCheck.isEmpty()) {
            Location currCheckedLocation = locationsToCheck.remove();

            Location[] toCheck = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(currCheckedLocation);
            for(int i = 0; i < toCheck.length; i++) {
                Location currLocation = toCheck[i];

                boolean isValidLocation = hexes.containsKey(currLocation) &&
                        !pieces.isThereAPieceAt(currLocation) &&
                        (hexes.get(currLocation)).getTerrain() != Terrain.VOLCANO &&
                        (hexes.get(currLocation)).getHeight() >= TIGER_ADDITION_LEVEL;

                if(isValidLocation) {
                    return currLocation;
                }
            }
        }
        return null;
    }
}

