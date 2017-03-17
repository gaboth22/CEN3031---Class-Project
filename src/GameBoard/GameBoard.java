package GameBoard;

import Player.Player;
import Player.PlayerID;
import Terrain.Terrain;
import Terrain.TerrainLocation;
import Tile.Tile;
import Tile.TileLocation;
import BuildPhase.BuildData;
import java.util.List;

public interface GameBoard {

    void insertTile(Tile tile, TileLocation location);

    void nukeTiles(Tile tile, TileLocation location);

    Tile getTile(TileLocation location);

    Settlement getSettlement(PlayerID ownerId, TerrainLocation location);

    List<Settlement> getAllSettlements(PlayerID ownerId);

    void appendMeeplesToSettlement(Settlement settlement, PlayerID playerId);

    void appendTotoroToSettlement(Settlement settlement, TerrainLocation location);

    void foundSettlement(TerrainLocation location);

    Terrain getTerrain(TerrainLocation location);

    void removeSettlement(Settlement settlementToRemove, PlayerID playerId);

    TileLocation getTileLocation(Tile tile);

    TerrainLocation getTerrainLocation(Terrain terrain);

    void doBuildAction(BuildData data);
}
