package Play.Rule.TilePlacementRules;

import Location.Location;
import Movement.*;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.*;

public class VolcanoMustBeOnTopOfVolcanoRule {

    public static void applyRule(TileMap tileMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        Location locationToPlaceVolcano = locations[0];
        Hexagon below = tileMap.getHexagonAt(locationToPlaceVolcano);
        Terrain belowTerrainAtLocation = below.getTerrain();

        String errorMessage = "New Tile's volcano not on top of another volcano";
        if(belowTerrainAtLocation != Terrain.VOLCANO)
            throw new InvalidTilePlacementRuleException(errorMessage);
    }
}
