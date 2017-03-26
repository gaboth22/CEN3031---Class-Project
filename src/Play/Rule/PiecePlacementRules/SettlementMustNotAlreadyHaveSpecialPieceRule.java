package Play.Rule.PiecePlacementRules;


import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;

public class SettlementMustNotAlreadyHaveSpecialPieceRule {
    public static void applyRule(GamePieceMap pieceMap,
                                 Location locationToPlacePieceOn,
                                 PlayerID pid,
                                 TypeOfPiece piece)
            throws InvalidPiecePlacementRuleException {

        Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationToPlacePieceOn);
        for(int i = 0; i < adjacents.length; i++) {
            Settlement settlement;

            if (pieceMap.isThereAPieceAt(adjacents[i]) &&
                    pieceAtLocationBelongsToActivePlayer(pieceMap, adjacents[i], pid)) {

                settlement = SettlementCreator.getSettlementAt(pieceMap, adjacents[i]);

                if(piece == TypeOfPiece.TIGER)
                    checkSettlementForTigerPlayground(settlement, locationToPlacePieceOn);
                else if(piece == TypeOfPiece.TOTORO)
                    checkSettlementForTotoroSanctuary(settlement, locationToPlacePieceOn);
            }
        }
    }

    private static boolean pieceAtLocationBelongsToActivePlayer(GamePieceMap pieceMap, Location loc, PlayerID pid) {
        return pieceMap.getPieceOwnerIdAtLocation(loc) == pid;
    }

    private static void checkSettlementForTigerPlayground(Settlement settlement, Location forPossibleError)
            throws InvalidPiecePlacementRuleException {

        if (settlement.hasTigerPlayground()) {
            String errorMessage = "Putting tiger at " + forPossibleError + " but settlement already has tiger";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }
    }

    private static void checkSettlementForTotoroSanctuary(Settlement settlement, Location forPossibleError)
            throws InvalidPiecePlacementRuleException {

        if(settlement.hasTotoroSanctuary()) {
            String errorMessage = "Putting totoro at " + forPossibleError + " but settlement already has totoro";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }

    }
}
