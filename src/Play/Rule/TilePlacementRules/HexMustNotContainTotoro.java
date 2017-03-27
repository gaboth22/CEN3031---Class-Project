package Play.Rule.TilePlacementRules;

import Tile.Tile.Tile;
import Location.Location;
import GamePieceMap.*;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;

public class HexMustNotContainTotoro {

    public static void applyRule(GamePieceMap pieceMap, Tile tileToPlace)
        throws InvalidTilePlacementRuleException {
        Location[] locations =  tileToPlace.getArrayOfTerrainLocations();
        for (int i = 0; i < locations.length; i++) {
            if (!pieceMap.isThereAPieceAt(locations[i])) {
                break;
            }
            if (pieceMap.getPieceTypeAtLocation(locations[i]) == TypeOfPiece.TOTORO) {
                throw new InvalidTilePlacementRuleException("You cannot place a tile on a totoro sanctuary.");
            }
        }
    }
}