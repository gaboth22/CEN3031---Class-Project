package TileMap;

import Position.Position;
import Tile.Tile.Tile;
import Terrain.Terrain.Terrain;
import Location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexagonMap implements TileMap {

    private int numberOfTilesInMap;

    private Map<Position, Integer> mapOfTiles;
    private Map<Position, Terrain> mapOfTerrains;
    private Map<Location, Integer> mapOfHeights;

    public HexagonMap() {
        numberOfTilesInMap = 0;
        mapOfHeights = new HashMap();
        mapOfTerrains = new HashMap();
        mapOfTiles = new HashMap();
    }

    @Override
    public void insertTile(Tile tile) throws LocationOccupiedException, InvalidTileLocationException {
        throwErrorIfPlacementInvalid(tile);

    }

    private void throwErrorIfPlacementInvalid(Tile tile) throws LocationOccupiedException, InvalidTileLocationException {
        if(numberOfTilesInMap == 0){
            mustBeVolcanoAtOrigin(tile);
        }
        checkHexagonsWillBePlacedOnTheSameLevel(tile);
    }

    /*
     * Guarantee that the first tile placed will be placed at the origin
     */
    private boolean mustBeVolcanoAtOrigin(Tile tile) throws InvalidTileLocationException {
        Location[] locations = tile.getArrayOfTerrainLocations();
        Terrain[] terrains = tile.getArrayOfTerrains();
        boolean found = false;
        for(int i = 0; i < locations.length; i++) {
            if(locations[i].getX() == 0 && locations[i].getY() == 0 && terrains[i] == Terrain.VOLCANO) {
                found = true;
            }
        }

        if (found == false) {
            throw new InvalidTileLocationException("The first tile must be placed with a volcano at the origin.");
        }
    }

    /*
     * Guarantee that a placed tile will have all of its locations on the same level
     */
    private boolean checkHexagonsWillBePlacedOnTheSameLevel(Tile tile) {
        Location[] locations = tile.getArrayOfTerrainLocations();
        List<Hexagon> hexagons = getListOfHexagons(locations);

    }

    private List<Hexagon> getListOfHexagons(Location[] locations) {
        List<Hexagon> listOfHexagons = new ArrayList<Hexagon>();
        for(int i = 0; i < locations.length; i++) {
            Hexagon toAddToList = this.getHexagonAt(locations[i]);
            listOfHexagons.add(toAddToList);
        }
        return listOfHexagons;
    }

    private boolean theLocationsAreAtTheSameHeight(List<Hexagon> hexagons) {
        return true;
    }

    public List<Hexagon> getAllHexagons() {
        List<Hexagon> allTopLevelHexagons = new ArrayList();

        return allTopLevelHexagons;
    }

    /*
     * Get the hexagon at the top of a location; returns null if there is no hexagon at that location
     */
    public Hexagon getHexagonAt(Location location) {
        if(numberOfTilesInMap == 0){
            //TODO: Implement some sort of error here?
            return null;
        }
        int height = mapOfHeights.get(location);
        Position position = new Position(location, height);
        Terrain terrain = mapOfTerrains.get(position);
        int tileID = mapOfTiles.get(position);
        return new Hexagon(tileID, location, height, terrain);
    }

    public boolean hasHexagonAt(Location location) {
        if(mapOfHeights.containsKey(location)) {
            return true;
        }
        return false;
    }

}
