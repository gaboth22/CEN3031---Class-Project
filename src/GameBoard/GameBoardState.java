package GameBoard;

import Location.Location;
import TileMap.Hexagon;

import java.util.Map;
import java.util.List;
import Player.*;

public class GameBoardState {
    private Player playerOne;
    private Player playerTwo;
    private int turnNumber;
    private Map<Location, Hexagon> placedHexagons;
    private List<Location> placeableLocations;

    public GameBoardState(
            Player playerOne,
            Player playerTwo,
            int turnNumber,
            Map<Location, Hexagon> placedHexagons,
            List<Location> placeableLocations) {

        this.turnNumber = turnNumber;
        this.placedHexagons = placedHexagons;
        this.placeableLocations = placeableLocations;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Map<Location, Hexagon> getListOfPlacedHexes() {
        return placedHexagons;
    }

    public List<Location> getGetPlaceableLocations() {
        return placeableLocations;
    }
}
