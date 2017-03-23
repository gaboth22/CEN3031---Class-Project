package Play.Rule.TilePlacementRules;

import GamePieceMap.GamePieceMap;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Location.Location;
import Tile.Tile.Tile;
import TileMap.TileMap;

public class TileMustTouchOneEdgeRule {

    public static void applyRule(TileMap tileMap, GamePieceMap gamePieceMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        for(int i = 0; i < locations.length; i++) {

        }
    }
}
