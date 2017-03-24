package Play.Rule.TilePlacementRules;

import Tile.Tile.Tile;
import Location.Location;
import GamePieceMap.*;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;

public class HexMustNotContainTiger {
    privateStaticString errorMessage = "You cannot place a tile on a tiger playground.";

    public static void applyRule(GameMap pieceMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {
        Location[] locations =  tileToPlace.getArrayOfTileLocations();
        for (int i = 0; i < 3; i++) {
            if (!pieceMap.containsKey(locations[i])) {
                break;
            }
            if (pieceMap.getPieceTypeAtLocation(location[i]) == TypeOfPiece.TIGER) {
                throw new InavlidTilePlacementRuleException(errorMessage);
            }
        }
    }
}