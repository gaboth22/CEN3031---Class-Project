package Play.Rule;

import Location.Location;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.TileMap;

public class TotoroCannotBePlacedOnVolcanoRule {

    public static void applyRule(TileMap tileMap, Location locationToPlace)
            throws InvalidPiecePlacementRuleException {
        throwIfLocationIsVolcano(tileMap, locationToPlace);
    }

    private static void throwIfLocationIsVolcano(TileMap tileMap, Location locationToPlace) throws InvalidPiecePlacementRuleException {

        if(tileMap.hasHexagonAt(locationToPlace)) {
            if(tileMap.getHexagonAt(locationToPlace).getTerrain() == Terrain.VOLCANO) {
                throw new InvalidPiecePlacementRuleException("A Totoro Sanctuary cannot be built on top of a Volcano.");
            }
        }
    }
}
