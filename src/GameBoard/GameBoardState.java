package GameBoard;

import GamePieceMap.GamePieceMap;
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
    private GamePieceMap gamePieceMap;
    private List<Location> placeableLocations;
    private List<Location> nukeableLocations;

    public GameBoardState(
            Player playerOne,
            Player playerTwo,
            int turnNumber,
            Map<Location, Hexagon> placedHexagons,
            GamePieceMap gamePieceMap,
            List<Location> placeableLocations,
            List<Location> nukeableLocations) {

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.turnNumber = turnNumber;
        this.placedHexagons = placedHexagons;
        this.gamePieceMap = gamePieceMap;
        this.placeableLocations = placeableLocations;
        this.nukeableLocations = nukeableLocations;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public List<Location> getPlaceableLocations() {
        return placeableLocations;
    }

    public List<Location> getNukeableLocations(){
        return nukeableLocations;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Map<Location, Hexagon> getPlacedHexagons() {
        return placedHexagons;
    }

    public GamePieceMap getGamePieceMap() {
        return gamePieceMap;
    }

}
