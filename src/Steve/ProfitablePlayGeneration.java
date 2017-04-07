package Steve;

import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.*;
import Settlements.Expansion.SettlementExpansion;
import GamePieceMap.GamePieceMap;

import java.util.*;

public class ProfitablePlayGeneration {
    private Terrain [] terrainTypes = {Terrain.GRASSLANDS, Terrain.JUNGLE, Terrain.LAKE, Terrain.ROCKY};
    private Player currentPlayer = null;
    private String profitablePlay = "";
    private String safePlay = "";

    public ProfitablePlayGeneration(Player player) {
        currentPlayer = player;
    }

    public String chooseBuildAction(Player player) {
        if (player.getTotoroCount() > 0) {
            this.pickTotoroLocation();
            if (profitablePlay.length() != 0) {
                return profitablePlay;
            }
        }
        if (player.getTigerCount() > 0) {
            this.pickTigerLocation();
            if (profitablePlay.length() != 0) {
                return profitablePlay;
            }
        }
        if (canExpand()) {
            //this.expansionChoice();
            if (profitablePlay.length() != 0) {
                return profitablePlay;
            }
        }
        if (currentPlayer.getVillagerCount() > 0) {
            return safePlay;
        }
        else {
            return "UNABLE TO BUILD";
        }
    }

    private void pickTotoroLocation() {
        int TOTORO_SETTLEMENT_SIZE = 5;
        String totoroBuildRequest = "";
        Settlement totoroCandidate = null;
        List<Settlement> playerSettlements = currentPlayer.getListOfSettlements();
        for (int i = 0; i < playerSettlements.size(); i++) {
            totoroCandidate = playerSettlements.get(i);
            if (totoroCandidate.hasTotoroSanctuary()) {
                continue;
            }
            else if (totoroCandidate.getNumberOfHexesInSettlement() < TOTORO_SETTLEMENT_SIZE) {
                continue;
            }
            //totoroBuildRequest = findTotoroLocation(hexes, totoroCandidate, pieceMap);
            if (totoroBuildRequest.length() != 0) {
                return;
            }
        }
    }

    private void pickTigerLocation() {
        int TIGER_ADDITION_LEVEL = 3;
        List<Settlement> playerSettlements = currentPlayer.getListOfSettlements();
        for (int i = 0; i < playerSettlements.size(); i++) {
            if ((playerSettlements.get(i)).hasTigerPlayground()) {
                continue;
            }

        }
    }

    private void expansionChoice(Map<Location, Hexagon> hexes, GamePieceMap pieceMap) {
        Settlement expansionCandidate;
        List<Location> locationsForExpansion;
        List<Settlement> playerSettlements = currentPlayer.getListOfSettlements();
        for (int i = 0; i < playerSettlements.size(); i ++) {
            expansionCandidate = playerSettlements.get(i);
            for (int j = 0; j < terrainTypes.length; j++) {
                locationsForExpansion = SettlementExpansion.findLocationsToExpandInto(hexes, expansionCandidate, pieceMap, terrainTypes[i]);
                if (locationsForExpansion.size() <= 0) {
                    continue;
                }
                if (SettlementExpansion.numVillagersRequiredToExpansion(hexes, locationsForExpansion) <= currentPlayer.getVillagerCount()) {
                    //format string to indicate settlement expansion
                    return;
                }
            }
        }
    }

    public void findTotoroLocation(Map<Location, Hexagon> hexMap,
                                  Settlement settlement,
                                  GamePieceMap pieceMap) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        Location totoroLocation = null;

        while (!locationsToCheck.isEmpty()) {
            Location currCheckedLocation = locationsToCheck.remove();

            Location[] toCheck = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(currCheckedLocation);
            for(int i = 0; i < toCheck.length; i++) {
                Location currLocation = toCheck[i];

                boolean isValidLocation = hexMap.containsKey(currLocation) &&
                        !pieceMap.isThereAPieceAt(currLocation) &&
                        (hexMap.get(currLocation)).getTerrain() != Terrain.VOLCANO &&;

                if(isValidLocation) {
                    totoroLocation = currLocation;
                    break;
                }
            }
        }
    }

    public void findTigerLocation(Map<Location, Hexagon> hexMap,
                                                           Settlement settlement,
                                                           GamePieceMap pieceMap) {
        Set<Location> locationsInSettlement = settlement.getSetOfLocationsInSettlement();
        Queue<Location> locationsToCheck = new LinkedList<>(locationsInSettlement);
        Location tigerLocation = null;

        while (!locationsToCheck.isEmpty()) {
            Location currCheckedLocation = locationsToCheck.remove();

            Location[] toCheck = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(currCheckedLocation);
            for(int i = 0; i < toCheck.length; i++) {
                Location currLocation = toCheck[i];

                boolean isValidLocation = hexMap.containsKey(currLocation) &&
                        !pieceMap.isThereAPieceAt(currLocation) &&
                        (hexMap.get(currLocation)).getTerrain() != Terrain.VOLCANO &&
                        (hexMap.get(currLocation)).getHeight() >= 3;

                if(isValidLocation) {
                    tigerLocation = currLocation;
                    break;
                }
            }
        }
    }


    private boolean canExpand() {
        if (currentPlayer.getVillagerCount() > 0 && currentPlayer.getListOfSettlements().size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

}
