package Play.Rule.TilePlacementRules;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileMap.TileMap;
import Location.*;
import TileMap.*;
import java.util.List;

public class HexesBelowTileShouldBeSameLevelRule {
    private static String errorMessage = "The tile must be placed such that all hexagons below it are of the same level";

    public static void applyRule(TileMap tileMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        List<Hexagon> hexagons = tileMap.getListOfHexagons(locations);
        if(!tileMap.theLocationsAreAtTheSameHeight(hexagons)){
            throw new InvalidTilePlacementRuleException(errorMessage);
        }

    }

}
