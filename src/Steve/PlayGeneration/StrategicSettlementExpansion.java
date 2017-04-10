package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import GamePieceMap.*;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.*;
import Settlements.Creation.Settlement;
import Settlements.Helper.AdjacentLocationsToSettlement;
import Location.*;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StrategicSettlementExpansion {

    public static BuildPhase buildAdjacentToLargestSettlement(GameBoardState gameBoardState, Player player) {

        Map<Location, Hexagon> hexagonMap = gameBoardState.getPlacedHexagons();
        List<Settlement> settlementList = player.getListOfSettlements();
        GamePieceMap gamePieceMap = gameBoardState.getGamePieceMap();

        Settlement[] currentPlayerSettlements;
        currentPlayerSettlements = SortSettlementArrayHelper.convertListToSettlementAndSort(settlementList);

        if(currentPlayerSettlements.length == 0 || player.getVillagerCount() <= 0) {
            return null;
        }

        for(int i = 0; i < currentPlayerSettlements.length; i++) {
            Settlement biggestSettlement = currentPlayerSettlements[i];

            if(biggestSettlement.hasTotoroSanctuary()) {
                continue;
            }

            else {
                Location[] adjacentPlaceableLocations;
                adjacentPlaceableLocations = adjacentPlaceableLocationsToSettlement(biggestSettlement, hexagonMap, gamePieceMap);

                if(adjacentPlaceableLocations.length != 0) {
                    return returnStrategicBuildPhase(adjacentPlaceableLocations, player);
                }
            }
        }

        return null;
    }

    private static BuildPhase returnStrategicBuildPhase(Location[] placeableLocations, Player player) {
        GamePiece piece = new GamePiece(player.getID(), TypeOfPiece.VILLAGER);
        BuildPhase strategicBuildPhase = new BuildPhase(piece, placeableLocations[0]);
        strategicBuildPhase.setBuildType(BuildType.FOUND);
        return strategicBuildPhase;
    }

    private static Location[] adjacentPlaceableLocationsToSettlement(Settlement settlement, Map<Location, Hexagon> hexMap, GamePieceMap gamePieceMap) {

        Location[] adjLocToSettlement = AdjacentLocationsToSettlement.getLocationsAdjacentToSettlement(settlement);
        ArrayList<Location> placeableLocations = new ArrayList<Location>();

        for(int i = 0; i < adjLocToSettlement.length; i++) {
            if (hexMap.containsKey(adjLocToSettlement[i]) && !gamePieceMap.isThereAPieceAt(adjLocToSettlement[i])) {
                Hexagon hexagon = hexMap.get(adjLocToSettlement[i]);

                if(hexagon.getHeight() == 1 && hexagon.getTerrain() != Terrain.VOLCANO)
                    placeableLocations.add(adjLocToSettlement[i]);

            }
        }

        return placeableLocations.toArray(new Location[placeableLocations.size()]);
    }
}