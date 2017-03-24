package Play.Rule.TilePlacementRules;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileMap.*;

import java.util.List;

public class CannotWipeOutSettlementRule {
    public static void applyRule(TileMap tileMap, GamePieceMap gamePieceMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        List<Hexagon> hexagons = tileMap.getListOfHexagons(locations);


    }
}
