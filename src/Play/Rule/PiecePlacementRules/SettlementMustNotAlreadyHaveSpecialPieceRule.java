package Play.Rule.PiecePlacementRules;


import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;

import java.util.ArrayList;

public class SettlementMustNotAlreadyHaveSpecialPieceRule {
    public static void applyRule(GamePieceMap pieceMap,
                                 Location locationToPlacePieceOn,
                                 PlayerID pid,
                                 TypeOfPiece piece)
            throws InvalidPiecePlacementRuleException {

        Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationToPlacePieceOn);
        ArrayList<Boolean> settlementHasSpecialPieceList = new ArrayList<>();

        for(int i = 0; i < adjacents.length; i++) {
            Settlement settlement;

            if (pieceMap.isThereAPieceAt(adjacents[i]) &&
                pieceAtLocationBelongsToActivePlayer(pieceMap, adjacents[i], pid)) {

                settlement = SettlementCreator.getSettlementAt(pieceMap, adjacents[i]);

                if(piece == TypeOfPiece.TIGER) {
                    boolean hasTiger = checkSettlementForTigerPlayground(settlement, locationToPlacePieceOn);
                    settlementHasSpecialPieceList.add(hasTiger);
                }

                else if(piece == TypeOfPiece.TOTORO) {
                    boolean hasTotoro = checkSettlementForTotoroSanctuary(settlement, locationToPlacePieceOn);
                    settlementHasSpecialPieceList.add(hasTotoro);
                }
            }
        }

        checkIfAtLeastOnceSettlementDoesNotHaveASpecialPiece(settlementHasSpecialPieceList, piece);
    }

    private static boolean pieceAtLocationBelongsToActivePlayer(GamePieceMap pieceMap, Location loc, PlayerID pid) {

        return pieceMap.getPieceOwnerIdAtLocation(loc) == pid;
    }

    private static boolean checkSettlementForTigerPlayground(Settlement settlement, Location forPossibleError) {

       return settlement.hasTigerPlayground();
    }

    private static boolean checkSettlementForTotoroSanctuary(Settlement settlement, Location forPossibleError) {

        return settlement.hasTotoroSanctuary();
    }

    private static void checkIfAtLeastOnceSettlementDoesNotHaveASpecialPiece(
            ArrayList<Boolean> settlementHasSpecialPieceList,
            TypeOfPiece piece)
            throws InvalidPiecePlacementRuleException {

        boolean foundOneThatDoesNotHaveSpecialPiece = false;

        for(Boolean hasSpecialPiece : settlementHasSpecialPieceList) {
            if(!hasSpecialPiece)
                foundOneThatDoesNotHaveSpecialPiece = true;
        }

        if(!foundOneThatDoesNotHaveSpecialPiece) {
            String errorMessage = "Attempting to place " + piece + " but all adjacent settlements already have one";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }
    }
}
