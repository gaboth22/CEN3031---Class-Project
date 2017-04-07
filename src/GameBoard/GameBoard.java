package GameBoard;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.*;
import Settlements.Creation.Settlement;
import TileMap.Hexagon;
import java.util.Map;
import java.util.List;

public interface GameBoard {

    //Methods for AI to do actions
    void doBuildPhase(BuildPhase buildPhase) throws Exception;
    void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception;

    //Methods for Server to do actions
    //SERVER USE ONLY
    void serverDoBuildPhase(BuildPhase buildPhase) throws Exception;
    void serverDoTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception;

    //Methods to retrieve the state of the game
    Map<Location, Hexagon> getPlacedHexagons();
    int getCurrentTurn();
    List<Location> getPlaceableLocations();
    List<Location> getNukeableVolcanoLocations();
    GamePieceMap getGamePieceMap();

    //Methods to retrieve the state of a player
    int getPlayerOneScore();
    int getPlayerTwoScore();
    List<Settlement> getPlayerOneSettlements();
    List<Settlement> getPlayerTwoSettlements();
    Player getPlayer(PlayerID player);
}
