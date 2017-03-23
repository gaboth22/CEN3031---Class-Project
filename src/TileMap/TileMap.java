package TileMap;

import Tile.Tile.Tile;
import Location.Location;

import java.util.List;
import java.util.Map;

public interface TileMap {

    void insertTile(Tile tile) throws LocationOccupiedException, InvalidTileLocationException;
    Hexagon getHexagonAt(Location location);
    boolean hasHexagonAt(Location location);
    Map<Location, Hexagon> getAllHexagons();
    List<Hexagon> getListOfHexagons(Location[] locations);
    boolean theLocationsAreAtTheSameHeight(List<Hexagon> hexagons);


}
