package Play.Rule.PiecePlacementRules;

import GamePieceMap.GamePiece;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Terrain.Terrain.Terrain;
import TileMap.TileMap;

public class GamePieceCannotBePlacedOnVolcanoRule {

    public static void applyRule(TileMap tileMap, GamePiece gamePiece, Location locationToPlace)
            throws InvalidPiecePlacementRuleException {
        throwIfLocationIsVolcano(tileMap, gamePiece, locationToPlace);
    }

    private static void throwIfLocationIsVolcano(TileMap tileMap,GamePiece gamePiece, Location locationToPlace) throws InvalidPiecePlacementRuleException {

        if(tileMap.hasHexagonAt(locationToPlace)) {
            if(tileMap.getHexagonAt(locationToPlace).getTerrain() == Terrain.VOLCANO) {
                throw new InvalidPiecePlacementRuleException("A " + gamePiece.getPieceType() + " cannot be built on top of a Volcano.");
            }
        }
    }
}
