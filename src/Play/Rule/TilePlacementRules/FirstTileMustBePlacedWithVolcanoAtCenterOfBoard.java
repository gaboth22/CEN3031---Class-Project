package Play.Rule.TilePlacementRules;

import Location.Location;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Terrain.Terrain.Terrain;
import TileMap.*;
import Tile.Tile.Tile;

public class FirstTileMustBePlacedWithVolcanoAtCenterOfBoard {

    public static void applyRule(TileMap tileMap, Tile tileToPlace) throws InvalidTilePlacementRuleException {
        if(!volcanoIsAtCenter(tileToPlace) || !locationsInBoardAreEmpty(tileMap, tileToPlace)) {
            String errorMessage = "Invalid first Tile placement.";
            throw new InvalidTilePlacementRuleException(errorMessage);
        }

    }

    private static boolean volcanoIsAtCenter(Tile tileToPlace) {
        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        Terrain[] terrains = tileToPlace.getArrayOfTerrains();
        Location origin = new Location(0, 0);
        return locations[0].equals(origin) && terrains[0] == Terrain.VOLCANO;

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
