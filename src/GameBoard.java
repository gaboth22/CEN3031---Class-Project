import java.util.List;

public interface GameBoard {

    public void insertTile(Tile tile, TileLocation location);

    public Tile getTile(TileLocation location);

    public Settlement getSettlement(Player owner, TerrainLocation location);

    public List<Settlement> getAllSettlements(Player owner);

    public void appendMeepleToSettlement(Settlement settlement, TerrainLocation location);

    public void appendTotoroToSettlement(Settlement settlement, TerrainLocation location);

    public void foundSettlement(TerrainLocation location);

    public Terrain getTerrain(TerrainLocation location);

    public void removeSettlement(Settlement settlementToRemove);

    public TileLocation getTileLocation(Tile tile);

    public TerrainLocation getTerrainLocation(Terrain terrain);

    public void doBuildAction(BuildData data);
}
