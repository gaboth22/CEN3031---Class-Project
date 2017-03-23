package Play.Rule.TilePlacementRules;

import GamePieceMap.GamePieceMap;
import Movement.*;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Location.Location;
import Tile.Tile.Tile;
import TileMap.TileMap;

public class TileMustTouchOneEdgeRule {

    private static Movement movement = new AxialMovement();

    public static void applyRule(TileMap tileMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        for(int i = 0; i < locations.length; i++) {
            if(foundAdjacentEdge(tileMap, locations[i]))
                return;
        }

        throw new InvalidTilePlacementRuleException();
    }

    private static boolean foundAdjacentEdge(TileMap tileMap, Location location) {
        return  tileMap.hasHexagonAt(movement.up(location)) ||
                tileMap.hasHexagonAt(movement.upRight(location)) ||
                tileMap.hasHexagonAt(movement.downRight(location)) ||
                tileMap.hasHexagonAt(movement.down(location)) ||
                tileMap.hasHexagonAt(movement.downLeft(location)) ||
                tileMap.hasHexagonAt(movement.upLeft(location));
    }
}
