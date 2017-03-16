import java.util.List;

public interface GameBoard {

    void insertTile(Tile tile, TileLocation location);

    Tile getTile(TileLocation location);

    Settlement getSettlement(Player owner, TerrainLocation location);

    List<Settlement> getAllSettlements(Player owner);

    void appendMeepleToSettlement(Settlement settlement, TerrainLocation location);

    void appendTotoroToSettlement(Settlement settlement, TerrainLocation location);

    void foundSettlement(TerrainLocation location);

    Terrain getTerrain(TerrainLocation location);

    void removeSettlement(Settlement settlementToRemove);

    TileLocation getTileLocation(Tile tile);

    TerrainLocation getTerrainLocation(Terrain terrain);

    void doBuildAction(BuildData data);
}
