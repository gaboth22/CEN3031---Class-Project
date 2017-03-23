package Play.Rule.TilePlacementRules;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.Tile;
import TileMap.TileMap;
import Location.*;
import TileMap.*;
import java.util.ArrayList;
import java.util.List;

public class HexesBelowTileShouldBeSameLevelRule {
    private static String errorMessage = "The tile must be placed such that all hexagons below it are of the same level";

    public static void applyRule(TileMap tileMap, Tile tileToPlace)
            throws InvalidTilePlacementRuleException {

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        List<Hexagon> hexagons = getListOfHexagons(locations, tileMap);
        if(!theLocationsAreAtTheSameHeight(hexagons)){
            throw new InvalidTilePlacementRuleException(errorMessage);
        }

    }

    private static List<Hexagon> getListOfHexagons(Location[] locations, TileMap tileMap) {
        List<Hexagon> listOfHexagons = new ArrayList<Hexagon>();
        for(int i = 0; i < locations.length; i++) {
            Hexagon toAddToList = tileMap.getHexagonAt(locations[i]);
            listOfHexagons.add(toAddToList);
        }
        return listOfHexagons;
    }

    private static boolean theLocationsAreAtTheSameHeight(List<Hexagon> hexagons) {
        List<Integer> listOfHeights = getHeights(hexagons);
        int height = listOfHeights.get(0);
        for(int i = 0; i < listOfHeights.size(); i++) {
            if(listOfHeights.get(i) != height) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> getHeights(List<Hexagon> hexagons) {
        List<Integer> listOfHeights = new ArrayList();
        for (int i = 0; i < hexagons.size(); i++) {
            if (hexagons.get(i) != null) {
                listOfHeights.add(hexagons.get(i).getHeight());
            }
            else {
                final int NULL_HEIGHT = -1;
                listOfHeights.add(NULL_HEIGHT);
            }
        }
        return listOfHeights;
    }

}
