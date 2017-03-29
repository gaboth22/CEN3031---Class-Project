package GameBoard;

import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.Hexagon;

import java.util.List;

public class GameBoardState {
    private int p1Score;
    private int p2Score;
    private int turnNumer;
    private List<Hexagon> placedHexagons;
    private List<Settlement> p1Settlements;
    private List<Settlement> p2Settlements;
    private List<Location> placeableLocations;

    public GameBoardState(
            int p1Score,
            int p2Score,
            int turnNumber,
            List<Hexagon> placedHexagons,
            List<Settlement> p1Settlements,
            List<Settlement> p2Settlements,
            List<Location> placeableLocations) {

        this.p1Score = p1Score;
        this.p2Score = p2Score;
        this.turnNumer = turnNumber;
        this.placedHexagons = placedHexagons;
        this.p1Settlements = p1Settlements;
        this.p2Settlements = p2Settlements;
        this.placeableLocations = placeableLocations;
    }

    public int getP1Score() {
        return p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }

    public List<Hexagon> getListOfPlacedHexes() {
        return placedHexagons;
    }

    public List<Settlement> getP1Settlements() {
        return p1Settlements;
    }

    public List<Settlement> getP2Settlements() {
        return p2Settlements;
    }

    public List<Location> getGetPlaceableLocations() {
        return placeableLocations;
    }
}
