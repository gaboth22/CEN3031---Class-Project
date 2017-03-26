package GameBoard;

import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.Hexagon;
import java.util.Map;

public interface GameBoard {

    void doBuildPhase(BuildPhase buildPhase) throws Exception;
    void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception;
    Map<Location, Hexagon> getGameBoardHexagons() throws Exception;
    boolean hasTileAt(Location[] locationsInTile);
    int getCurrentTurn();
}
