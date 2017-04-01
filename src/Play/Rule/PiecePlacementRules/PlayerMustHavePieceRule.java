package Play.Rule.PiecePlacementRules;

import GamePieceMap.GamePieceMap;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;
import Settlements.Expansion.SettlementExpansion;
import TileMap.*;

import java.util.List;

public class PlayerMustHavePieceRule {

    public static void applyRule(Player player, GamePieceMap gamePieceMap, BuildPhase buildPhase, TileMap tileMap)
            throws InvalidPiecePlacementRuleException {

        if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.VILLAGER
                && buildPhase.getBuildType() == BuildType.EXPAND) {
            String errorMessage = "" + player.getID() + " does not have enough villagers";
            if(playerDoesNotHavePiecesForExpansion(player, gamePieceMap, buildPhase, tileMap)) {
                throw new InvalidPiecePlacementRuleException(errorMessage);
            }
        }
        else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.VILLAGER
                && buildPhase.getBuildType() == BuildType.FOUND){
            String errorMessage = "" + player.getID() + " does not have enough villagers";
            if(playerDoesNotHavePiecesForFoundation(player)) {
                throw new InvalidPiecePlacementRuleException(errorMessage);
            }
        }
        else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER) {
            String errorMessage = "" + player.getID() + " does not have enough tigers";
            if(!(player.getTigerCount() > 0)) {

                throw new InvalidPiecePlacementRuleException(errorMessage);
            }
        }

        else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
            if (!(player.getTotoroCount() > 0)) {
                String errorMessage = "" + player.getID() + " does not have enough totoro";
                throw new InvalidPiecePlacementRuleException(errorMessage);
            }
        }
    }

    private static boolean playerDoesNotHavePiecesForFoundation(Player player) {
        return player.getVillagerCount() <= 0;
    }

    private static boolean playerDoesNotHavePiecesForExpansion(Player player,
                                                               GamePieceMap gamePieceMap,
                                                               BuildPhase buildPhase,
                                                               TileMap tileMap) {

        Hexagon hexAtLocation = tileMap.getHexagonAt(buildPhase.getLocationToPlacePieceOn());

        List<Location> listOfLocationsToExpand = SettlementExpansion.findLocationsToExpandInto(
                tileMap.getAllHexagons(),
                buildPhase.getSettlement(),
                gamePieceMap,
                hexAtLocation.getTerrain());

        int requiredNumberOfVillager = SettlementExpansion.numVillagersRequiredToExpansion(tileMap.getAllHexagons(), listOfLocationsToExpand);

        return player.getVillagerCount() < requiredNumberOfVillager;
    }
}
