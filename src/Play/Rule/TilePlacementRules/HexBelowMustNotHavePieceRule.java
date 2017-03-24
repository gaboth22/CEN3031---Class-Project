package Play.Rule.TilePlacementRules;

import GamePieceMap.*;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;

public class HexBelowMustNotHavePieceRule {
    public static void applyRule(GamePieceMap pieceMap, Location locationToPlacePieceOn)
            throws InvalidPiecePlacementRuleException {

        if(pieceMap.isThereAPieceAt(locationToPlacePieceOn)) {
            String errorMessage = "Attempting to place a piece on an occupied location: " + locationToPlacePieceOn;
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }
    }
}
