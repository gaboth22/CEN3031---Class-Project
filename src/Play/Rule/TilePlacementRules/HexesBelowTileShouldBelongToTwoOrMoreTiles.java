package Play.Rule.TilePlacementRules;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileMap.TileMap;
import Location.Location;

public class HexesBelowTileShouldBelongToTwoOrMoreTiles {

    static private final int NULL_TILE_ID = -1;

    public static void applyRule(TileMap tileMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        int[] tileIDs = getArrayOfTileID(tileMap, locations);

        throwIfAllTileIDsAreEqual(tileIDs);
    }

    private static int[] getArrayOfTileID(TileMap tileMap, Location[] locations) {

        int[] tileID = new int[locations.length];
        for(int i = 0; i < locations.length; i++) {
            if(tileMap.hasHexagonAt(locations[i])) {
                tileID[i] = tileMap.getHexagonAt(locations[i]).getParentTileID();
            }
            else{
                tileID[i] = NULL_TILE_ID;
            }
        }

        return tileID;
    }

    private static void throwIfAllTileIDsAreEqual(int[] tileIDs) throws InvalidTilePlacementRuleException {
        for(int i = 1; i < tileIDs.length; i++) {
            if(tileIDs[i] != tileIDs[0]) {
                return;
            }
        }

        if(tileIDs.length > 0 && tileIDs[0] != NULL_TILE_ID) {
            throw new InvalidTilePlacementRuleException(
                    "A Tile placed on another Tile must be placed over hexes that are part of two or three different tiles");
        }
    }
}
