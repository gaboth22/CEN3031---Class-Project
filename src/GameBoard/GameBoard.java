package GameBoard;

import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Settlements.Creation.Settlement;
import TileMap.Hexagon;
import java.util.Map;
import java.util.List;

public interface GameBoard {

    void doBuildPhase(BuildPhase buildPhase) throws Exception;
    void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception;
    Map<Location, Hexagon> getPlacedHexagons() throws Exception;
    int getCurrentTurn();
    int getPlayerOneScore();
    int getPlayerTwoScore();
    List<Settlement> getPlayerOneSettlements();
    List<Settlement> getPlayerTwoSettlements();
    List<Location> getPlaceableLocations();
    GamePieceMap getGamePieceMap();
}
