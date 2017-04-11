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
    private int currentTileID;

    private Map<Position, Integer> mapOfTiles;
    private Map<Position, Terrain> mapOfTerrains;
    private Map<Location, Integer> mapOfHeights;

    public HexagonMap() {
        numberOfTilesInMap = 0;
        currentTileID = 0;
        mapOfHeights = new HashMap();
        mapOfTerrains = new HashMap();
        mapOfTiles = new HashMap();
    }

    public HexagonMap(HexagonMap mapToCopy) {
        this.numberOfTilesInMap = mapToCopy.numberOfTilesInMap;
        this.currentTileID = mapToCopy.currentTileID;
        this.mapOfHeights = new HashMap<Location, Integer>(mapToCopy.mapOfHeights);
        this.mapOfTerrains = new HashMap<Position, Terrain>(mapToCopy.mapOfTerrains);
        this.mapOfTiles = new HashMap<Position, Integer>(mapToCopy.mapOfTiles);
    }

    /*
     * Get the hexagon at the top of a location; returns null if there is no hexagon at that location
     */
    public boolean hasHexagonAt(Location location) {
        if(mapOfHeights.containsKey(location)) {
            return true;
        }
        return false;
    }

    public int getHeightAt(Location location){
        return mapOfHeights.get(location);
    }

    public Hexagon getHexagonAt(Location location) {
        if(numberOfTilesInMap == 0 || !mapOfHeights.containsKey(location)){
            //TODO: Implement some sort of error here?
            return null;
        }
        int height = mapOfHeights.get(location);
        Position position = new Position(location, height);
        Terrain terrain = mapOfTerrains.get(position);
        int tileID = mapOfTiles.get(position);
        return new Hexagon(tileID, location, height, terrain);
    }

    @Override
    public void insertTile(Tile tileToPlace) throws LocationOccupiedException, InvalidTileLocationException {
        throwErrorIfPlacementInvalid(tileToPlace);
        placeTileIntoMaps(tileToPlace);
    }

    private void placeTileIntoMaps(Tile tile) {
        Location[] locations = tile.getArrayOfTerrainLocations();
        Terrain[] terrains = tile.getArrayOfTerrains();
        for(int i = 0; i < locations.length; i++) {
            placeHexagon(locations[i], terrains[i]);
        }
        numberOfTilesInMap++;
        currentTileID++;
    }

    private void placeHexagon(Location location, Terrain terrain) {
        int height = mapOfHeights.get(location) != null ? mapOfHeights.get(location) + 1 : 1;
        mapOfHeights.put(location, height);
        Position position = new Position(location, height);
        mapOfTiles.put(position, currentTileID);
        mapOfTerrains.put(position, terrain);
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
    private void mustBeVolcanoAtOrigin(Tile tile) throws InvalidTileLocationException {
        Location[] locations = tile.getArrayOfTerrainLocations();
        Terrain[] terrains = tile.getArrayOfTerrains();
        boolean found = false;
        for(int i = 0; i < locations.length; i++) {
            if(locations[i].getX() == 0 && locations[i].getY() == 0 && terrains[i] == Terrain.VOLCANO) {
                found = true;
            }
        }

        if (found == false) {
            throw new InvalidTileLocationException("The first tile placed in the map must have its volcano at the origin.");
        }
    }

    /*
     * Guarantee that a placed tile will have all of its locations on the same level
     */
    private void checkHexagonsWillBePlacedOnTheSameLevel(Tile tile) throws LocationOccupiedException{
        Location[] locations = tile.getArrayOfTerrainLocations();
        List<Hexagon> hexagons = getListOfHexagons(locations);
        if(!theLocationsAreAtTheSameHeight(hexagons)){
            throw new LocationOccupiedException("The tile must be placed such that all hexagons below it are of the same level");
        }
    }

    public List<Hexagon> getListOfHexagons(Location[] locations) {
        List<Hexagon> listOfHexagons = new ArrayList<Hexagon>();
        for(int i = 0; i < locations.length; i++) {
            Hexagon toAddToList = this.getHexagonAt(locations[i]);
            listOfHexagons.add(toAddToList);
        }
        return listOfHexagons;
    }

    public boolean theLocationsAreAtTheSameHeight(List<Hexagon> hexagons) {
        List<Integer> listOfHeights = getHeights(hexagons);
        int height = listOfHeights.get(0);
        for(int i = 0; i < listOfHeights.size(); i++) {
            if(listOfHeights.get(i) != height) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> getHeights(List<Hexagon> hexagons) {
        List<Integer> listOfHeights = new ArrayList();
        for(int i = 0; i < hexagons.size(); i++) {
            if(hexagons.get(i) != null) {
                listOfHeights.add(hexagons.get(i).getHeight());
            }
            else {
                final int NULL_HEIGHT = -1;
                listOfHeights.add(NULL_HEIGHT);
            }
        }
        return listOfHeights;
    }

    public Map<Location, Hexagon> getAllHexagons() {
        Map<Location, Hexagon> allTopLevelHexagons = new HashMap();
        List<Location> l = new ArrayList(mapOfHeights.keySet());
        for(int i = 0; i < l.size(); i++) {
            allTopLevelHexagons.put(l.get(i), this.getHexagonAt(l.get(i)));
        }
        return allTopLevelHexagons;
    }

}
