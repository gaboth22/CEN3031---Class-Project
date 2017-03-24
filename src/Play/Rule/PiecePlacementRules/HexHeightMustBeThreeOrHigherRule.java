package Play.Rule.PiecePlacementRules;

import Location.Location;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import TileMap.*;

public class HexHeightMustBeThreeOrHigherRule {

    public static void applyRule(TileMap tileMap, Location locationToPlacePieceOn)
            throws InvalidPiecePlacementRuleException {

        Hexagon below = tileMap.getHexagonAt(locationToPlacePieceOn);
        if(!(below.getHeight() >= 3)) {
            String errorMessage = "" + locationToPlacePieceOn + " height is not >= 3";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }
    }
}
