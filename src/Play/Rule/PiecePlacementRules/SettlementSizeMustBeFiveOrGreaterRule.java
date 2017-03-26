package Play.Rule.PiecePlacementRules;

import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;

public class SettlementSizeMustBeFiveOrGreaterRule {

    public static void applyRule(GamePieceMap pieceMap,
                          Location locationToPlacePieceOn,
                          PlayerID pid)
        throws InvalidPiecePlacementRuleException {

        Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationToPlacePieceOn);

        for(int i = 0; i < adjacents.length; i++) {
            Settlement settlement;

            if (pieceMap.isThereAPieceAt(adjacents[i]) &&
                    pieceAtLocationBelongsToActivePlayer(pieceMap, adjacents[i], pid)) {

                settlement = SettlementCreator.getSettlementAt(pieceMap, adjacents[i]);

                if(settlement.getNumberOfHexesInSettlement() >= 5)
                    return;
            }
        }

        String errorMessage = "Putting totoro at " + locationToPlacePieceOn + " but settlement is not >= 5";
        throw new InvalidPiecePlacementRuleException(errorMessage);
    }


    private static boolean pieceAtLocationBelongsToActivePlayer(GamePieceMap pieceMap, Location loc, PlayerID pid) {
        return pieceMap.getPieceOwnerIdAtLocation(loc) == pid;
    }
}
