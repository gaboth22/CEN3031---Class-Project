import java.util.List;

public interface GameBoard {

    public void insertTileAtLocation(Tile tile, TileLocation location);

    public Tile getTileAtLocation(TileLocation location);

    public Settlement getSettlement(Player owner, TerrainLocation location);

    public List<Settlement> getAllSettlements(Player owner);

    public void appendMeepleToSettlementAtLocation(Settlement settlement, TerrainLocation location);

    public void appendTotoroToSettlementAtLocation(Settlement settlement, TerrainLocation location);

    public void foundSettlement(TerrainLocation location);

    public Terrain getTerrainAtLocation(TerrainLocation location);

    public void removeSettlement(Settlement settlementToRemove);

    public TileLocation getTileLocation(Tile tile);

    public TerrainLocation getTerrainLocation(Terrain terrain);

    public void doBuildAction(BuildData data);
}
