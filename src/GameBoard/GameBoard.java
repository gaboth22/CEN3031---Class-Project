package GameBoard;

import Location.Location;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.Hexagon;
import java.util.Map;

public interface GameBoard {

    void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception;
    Map<Location, Hexagon> getGameBoardHexagons() throws Exception;
    boolean hasTileAt(Location[] locationsInTile);
    int getCurrentTurn();
}
