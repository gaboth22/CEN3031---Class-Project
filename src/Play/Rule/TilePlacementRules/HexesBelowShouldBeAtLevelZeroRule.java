package Play.Rule.TilePlacementRules;

import Location.Location;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileMap.TileMap;

public class HexesBelowShouldBeAtLevelZeroRule {

    public static void applyRule(TileMap tileMap, Tile tileToPlace) throws InvalidTilePlacementRuleException {
        if(!locationsInBoardAreEmpty(tileMap, tileToPlace)) {
            String errorMessage = "Locations below are not at level zero";
            throw new InvalidTilePlacementRuleException(errorMessage);
        }
    }

    private static boolean locationsInBoardAreEmpty(TileMap tileMap, Tile tileToPlace) {
        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        for(int i = 0; i < locations.length; i++) {
            if(tileMap.hasHexagonAt(locations[i]))
                return false;
        }

        return true;
    }
}
