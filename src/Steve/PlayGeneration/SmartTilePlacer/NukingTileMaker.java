package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileMap.Hexagon;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NukingTileMaker {
    private Settlement nukeableSettlement;
    private Map<Location, Hexagon> hexMap;

    public Tile makeTile(Map<Location, Hexagon> hexMap,
                         Settlement nukeableSettlement,
                         Location ofVolcano,
                         BiHexTileStructure terrains) {

        this.nukeableSettlement = nukeableSettlement;
        this.hexMap = hexMap;

        Location[] adjacentLocationsToVolcano = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(ofVolcano);
        List<Integer> buildingIndices = PairOfLocationsAroundVolcanoListGetter.get(hexMap, adjacentLocationsToVolcano);

        Location left = null;
        Location right = null;

        for(int i = 0; i < buildingIndices.size(); i += 2) {
            Location l1 = adjacentLocationsToVolcano[buildingIndices.get(i)];
            Location l2 = adjacentLocationsToVolcano[buildingIndices.get(i + 1)];

            if (atLeastOneInSettlement(l1, l2) && belongToDifferentTiles(l1, l2) && areOnSameLevel(l1, l2, ofVolcano)) {
                left = l1;
                right = l2;
                break;
            }
        }

        Location[] locationInTile = {ofVolcano, left, right};
        Terrain[] terrainsInTile = {Terrain.VOLCANO, terrains.getLeftTerrain(), terrains.getRightTerrain()};

        return new TileImpl(Arrays.asList(terrainsInTile), Arrays.asList(locationInTile));
    }

    private boolean atLeastOneInSettlement(Location l1, Location l2) {
        return nukeableSettlement.locationIsInSettlement(l1) || nukeableSettlement.locationIsInSettlement(l2);
    }

    private boolean belongToDifferentTiles(Location l1, Location l2) {
        return LocationsBelongToTwoDifferentTiles.check(l1, l2, hexMap);
    }

    private boolean areOnSameLevel(Location l1, Location l2, Location l3) {
        return AllLocationsOnLevelOne.check(l1, l2, l3, hexMap);
    }
}
