package GameBoardTest;

import GameBoard.GameBoard;
import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.*;
import Settlements.Creation.Settlement;
import TestExceptions.MethodCalledException;
import TileMap.Hexagon;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void serverDoBuildPhase(BuildPhase buildPhase) throws Exception{

    }

    public void doBuildPhase(BuildPhase phase) throws Exception {
        if(failBuildPhase)
            throw new InvalidPiecePlacementRuleException();
    }

    public void serverDoTilePlacementPhase(TilePlacementPhase tilePlacementPhase){

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

    public Map<Location, Hexagon> getPlacedHexagons() {
        return new HashMap<Location, Hexagon>();
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

    public List<Location> getNukeableVolcanoLocations(){
        return null;
    }

    public int getPlayerOneScore() {
        return getP1Score();
    }

    public int getPlayerTwoScore() {
        return getP2Score();
    }

    public Player getPlayer(PlayerID player) {
        return new Player(PlayerID.PLAYER_ONE);
    }

    public List<Settlement> getPlayerOneSettlements() {
        return new ArrayList<>();
    }

    public List<Settlement> getPlayerTwoSettlements() {
        return new ArrayList<>();
    }

    public GamePieceMap getGamePieceMap() {
        return new GamePieceMap();
    }
}
