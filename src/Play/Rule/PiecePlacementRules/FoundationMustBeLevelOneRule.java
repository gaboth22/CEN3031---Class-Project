package Play.Rule.PiecePlacementRules;

import Location.Location;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import TileMap.*;

public class FoundationMustBeLevelOneRule {

    public static void applyRule(TileMap tileMap, Location locationToPlacePieceOn)
            throws InvalidPiecePlacementRuleException {

        Hexagon below = tileMap.getHexagonAt(locationToPlacePieceOn);
        if (!(below.getHeight() == 1)) {
            String errorMessage = "" + locationToPlacePieceOn + " height is not == 1";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }
    }
}
