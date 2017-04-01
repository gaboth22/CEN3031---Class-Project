package GameBoardTest;

import GameBoard.GameBoard;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.TilePlacementPhase.TilePlacementPhase;
import Settlements.Creation.Settlement;
import TestExceptions.MethodCalledException;
import TileMap.Hexagon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameBoardTestDouble implements GameBoard {

    private boolean failBuildPhase;
    private boolean failTilePlacementPhase;

    public GameBoardTestDouble() {
        failBuildPhase = false;
        failTilePlacementPhase = false;
    }

    public void failBuildPhase() {
        failBuildPhase = true;
    }

    public void failTilePlacementPhase() {
        failTilePlacementPhase = true;
    }

    public void doBuildPhase(BuildPhase phase) throws Exception {
        if(failBuildPhase)
            throw new InvalidPiecePlacementRuleException();
    }

    public void doTilePlacementPhase(TilePlacementPhase phase) throws Exception {
        if(failTilePlacementPhase)
            throw new InvalidTilePlacementRuleException();
    }

    public Map<Location, Hexagon> getGameBoardHexagons() throws Exception {
        throw new MethodCalledException("getGameBoardHexagons");
    }

    public boolean hasTileAt(Location[] locationsInTile) {
        return true;
    }

    public int getCurrentTurn() {
        return -100;
    }

    public int getP1Score() {
        return -100;
    }

    public int getP2Score() {
        return -100;
    }

    public List<Hexagon> getPlacedHexagons() {
        return new ArrayList<>();
    }

    public List<Settlement> getP1Settlements() {
        return new ArrayList<>();
    }

    public List<Settlement> getP2Settlements() {
        return new ArrayList<>();
    }

    public List<Location> getPlaceableLocations() {
        return new ArrayList<Location>();
    }
}
