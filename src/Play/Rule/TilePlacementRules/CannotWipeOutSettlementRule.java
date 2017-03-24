package Play.Rule.TilePlacementRules;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileMap.*;
import java.util.List;

public class CannotWipeOutSettlementRule {
    private static String errorMessage = "Nuking can not destroy a settlement completely.";

    public static void applyRule(TileMap tileMap, GamePieceMap gamePieceMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        if(NoSettlementsBelowTileToPlace(locations, gamePieceMap)){
            return;
        }
        else if(PlacementDoesNotDestroysSettlement(locations, gamePieceMap)){
            return;
        }
        else{
            throw new InvalidTilePlacementRuleException(errorMessage);
        }

    }i

    private static boolean NoSettlementsBelowTileToPlace(Location[] locations, GamePieceMap gamePieceMap){
        for(int i = 0; i < locations.length; i++){
            if(!(gamePieceMap.getPieceAtLocation(locations[i]) == null)) {
                return false;
            }
        }
        return true;
    }

    private static boolean PlacementDoesNotDestroysSettlement(Location[] locations, GamePieceMap gamePieceMap){


        return true;
    }
}
