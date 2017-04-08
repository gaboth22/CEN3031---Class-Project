package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import Terrain.Terrain.Terrain;
import TileMap.*;
import GamePieceMap.*;

import java.util.*;

public class FoundSettlementHelper {
    static public BuildPhase pickLocationForNewSettlement(GameBoardState gameState, PlayerID currentPlayer) {
        List<Settlement> allOpponentSettlements = null;
        if (currentPlayer == PlayerID.PLAYER_ONE) {
            allOpponentSettlements = (gameState.getPlayerTwo()).getListOfSettlements();
        }
        else {
            allOpponentSettlements = (gameState.getPlayerOne()).getListOfSettlements();
        }
        Settlement opponentSettlement = null;
        Location newSettlementLocation = null;
        for (int i = 0; i < allOpponentSettlements.size(); i++) {
            opponentSettlement = allOpponentSettlements.get(i);
            newSettlementLocation = findNewSettlementLocation(gameState.getPlacedHexagons(), opponentSettlement, gameState.getGamePieceMap());
            if (newSettlementLocation != null) {
                GamePiece foundingVillager = new GamePiece(currentPlayer, TypeOfPiece.VILLAGER);
                BuildPhase foundSettlement = new BuildPhase(foundingVillager, newSettlementLocation);
                foundSettlement.setBuildType(BuildType.FOUND);
                return foundSettlement;
            }
        }
        return null;
    }

    static public Location findNewSettlementLocation(Map<Location, Hexagon> hexes, Settlement settlement, GamePieceMap pieces) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        while (!locationsToCheck.isEmpty()) {
            Location currCheckedLocation = locationsToCheck.remove();

            Location[] toCheck = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(currCheckedLocation);
            for (int i = 0; i < toCheck.length; i++) {
                Location currLocation = toCheck[i];

                boolean isValidLocation = hexes.containsKey(currLocation) &&
                        !pieces.isThereAPieceAt(currLocation) &&
                        (hexes.get(currLocation)).getTerrain() != Terrain.VOLCANO;

                if (isValidLocation) {
                    return currLocation;
                }
            }
        }
        return null;
    }
}
