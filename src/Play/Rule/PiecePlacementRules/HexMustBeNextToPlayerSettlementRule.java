package Play.Rule.PiecePlacementRules;

import GamePieceMap.*;
import Location.Location;
import Movement.*;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;

public class HexMustBeNextToPlayerSettlementRule {
    private static Movement movement = new AxialMovement();

    public static void applyRule(GamePieceMap pieceMap, Location locationToPlacePieceOn, PlayerID pid)
            throws InvalidPiecePlacementRuleException {

        Location[] adjacentLocations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationToPlacePieceOn);
        for(int i = 0; i < adjacentLocations.length; i++) {
            if(foundAdjacentPlayerSettlement(pieceMap, adjacentLocations[i], pid))
                return;
        }

        String errorMessage = "No adjacent settlements found for player " + pid + " from " + locationToPlacePieceOn;
        throw new InvalidPiecePlacementRuleException(errorMessage);
    }

    private static boolean foundAdjacentPlayerSettlement(GamePieceMap pieceMap, Location loc, PlayerID pid) {
        if(pieceMap.isThereAPieceAt(loc)) {
            if(settlementAtLocationBelongsToPlayer(pieceMap, loc, pid))
                return true;
        }

        return false;
    }

    private static boolean settlementAtLocationBelongsToPlayer(GamePieceMap pieceMap, Location loc, PlayerID pid) {
        return pid == pieceMap.getPieceOwnerIdAtLocation(loc);
    }
}
