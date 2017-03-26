package Play.Rule.TilePlacementRules;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import Tile.Tile.Tile;

public class CannotWipeOutSettlementRule {
    private static String errorMessage = "Nuking can not destroy a settlement completely.";

    public static void applyRule(GamePieceMap gamePieceMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {
        SettlementCreator settlementCreator = new SettlementCreator();

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        if(noPiecesOnTileBeingNuked(locations, gamePieceMap)){
            return;
        }
        else if(onePieceOnTileBeingNuked(locations, gamePieceMap)){
            Location pieceBeingNuked = getPieceBeingNukedLocation(locations, gamePieceMap);
            Settlement settlement = settlementCreator.getSettlementAt(gamePieceMap, pieceBeingNuked);
            if(settlement.getNumberOfHexesInSettlement() > 1){
                return;
            }
            else{
                throw new InvalidTilePlacementRuleException(errorMessage);
            }
        }
        else {
            if(piecesBelongToDifferentPlayers(locations, gamePieceMap)){
                Settlement settlement1 = settlementCreator.getSettlementAt(gamePieceMap, locations[1]);
                Settlement settlement2 = settlementCreator.getSettlementAt(gamePieceMap, locations[2]);
                if(settlement1.getNumberOfHexesInSettlement() > 1 && settlement2.getNumberOfHexesInSettlement() > 1){
                    return;
                }
                else{
                    throw new InvalidTilePlacementRuleException(errorMessage);
                }
            }
            else{
                Settlement settlement = settlementCreator.getSettlementAt(gamePieceMap, locations[1]);
                if(settlement.getNumberOfHexesInSettlement() > 2){
                    return;
                }
                else{
                    throw new InvalidTilePlacementRuleException(errorMessage);
                }
            }
        }
    }

    public static boolean noPiecesOnTileBeingNuked(Location[] locations, GamePieceMap gamePieceMap){
        if(!gamePieceMap.isThereAPieceAt(locations[1]) && !gamePieceMap.isThereAPieceAt(locations[2])){
            return true;
        }
        return false;
    }

    public static boolean onePieceOnTileBeingNuked(Location[] locations, GamePieceMap gamePieceMap){
        if(gamePieceMap.isThereAPieceAt(locations[1]) && !gamePieceMap.isThereAPieceAt(locations[2])
                || gamePieceMap.isThereAPieceAt(locations[2]) && !gamePieceMap.isThereAPieceAt(locations[1])){
            return true;
        }
        return false;
    }

    public static Location getPieceBeingNukedLocation(Location[] locations, GamePieceMap gamePieceMap){
        if(gamePieceMap.isThereAPieceAt(locations[1])){
            return locations[1];
        }
        else{
            return locations[2];
        }
    }

    public static boolean piecesBelongToDifferentPlayers(Location[] locations, GamePieceMap gamePieceMap){
        if(gamePieceMap.getPieceOwnerIdAtLocation(locations[1]) != gamePieceMap.getPieceOwnerIdAtLocation(locations[2])){
            return true;
        }
        return false;
    }
}
