package Steve.PlayGeneration;

import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.*;
import GamePieceMap.*;
import Player.*;
import java.util.*;

public class TotoroLocationHelper {

    static public BuildPhase pickTotoroLocation(Map<Location, Hexagon> hexes, List<Settlement> playerSettlements, GamePieceMap pieces, PlayerID playerID) {
        int TOTORO_SETTLEMENT_SIZE = 5;

        BuildPhase buildPhase;
        Settlement totoroCandidate = null;
        Location totoroLocation = null;

        for(int i = 0; i < playerSettlements.size(); i++) {
            totoroCandidate = playerSettlements.get(i);

            if (totoroCandidate.hasTotoroSanctuary())
                continue;

            else if (totoroCandidate.getNumberOfHexesInSettlement() < TOTORO_SETTLEMENT_SIZE)
                continue;

            totoroLocation = findTotoroLocation(hexes, totoroCandidate, pieces);

            if (totoroLocation != null) {
                GamePiece totoroToPlace = new GamePiece(playerID, TypeOfPiece.TOTORO);
                buildPhase = new BuildPhase(totoroToPlace, totoroLocation);
                buildPhase.setBuildType(BuildType.PLACE_TOTORO);

                return buildPhase;
            }
        }

        return null;
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