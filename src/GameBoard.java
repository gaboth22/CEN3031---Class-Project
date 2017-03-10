public interface GameBoard {

    /*
     * @param tile: Tile to insert
     * @param location: Location where to insert tile
     * @return: void
     * @description: Inserts a tile at the desired tile location
     */
    public void insertTileAtLocation(Tile tile, TileLocation location);

    /*
     * @param location: Location to get tile from
     * @return The desired tile
     * @description: Retrieves a tile from the game board at te desired location
     */
    public Tile getTileAtLocation(TileLocation location);

    /*
     * @param location: Location of leftmost terrain in a settlement spanning several terrains
     * @return Desired settlement
     * @description: Returns a settlement whose leftmost location is the given location
     */
    public Settlement getSettlementWithLeftMostTerrainAtLocation(TerrainLocation location);

    /*
     * @param settlement: The settlement to append to
     * @param location: The terrain location of the newly appended Meeple
     * @return void
     * @description: Appends a Meeple to a settlement at the given location
     */
    public void appendMeepleToSettlementAtLocation(Settlement settlement, TerrainLocation location);

    /*
     * @param settlement: The settlement to append to
     * @return Location of the terrain where the Meeple was appended
     * @description: Appends a Meeple to a settlement at any empty,
     * adjacent terrain
     */
    public TerrainLocation appendMeepleToSettlement(Settlement settlement);

    /*
     * @param settlement: The settlement to append to
     * @param location: The terrain location of newly appended Totoro
     * @return void
     * @description: Appends a Totoro to a settlement ot the given location
     */
    public void appendTotoroToSettlementAtLocation(Settlement settlement, TerrainLocation location);

    /*
     * @param settlement: The settlement to append to
     * @return Location of terrain where the Totoro was appended
     * @description: Appends a Meeple to a settlement at any empty,
     * adjacent terrain
     */
    public TerrainLocation appendTotoroToSettlement(Settlement settlement);

    /*
     * @param location: Location of the desired terrain
     * @return Terrain at given location
     * @description: Returns a terrain at the given location
     */
    public Terrain getTerrainAtLocation(TerrainLocation location);

    /*
     * @param settlementToRemove
     * @return void
     * @description: Removes a settlement from the game board
     */
    public void removeSettlement(Settlement settlementToRemove);

    /*
     * @param tile: Tile whose location is desired
     * @return Location of the given tile
     */
    public TileLocation getTileLocation(Tile tile);

    /*
     * @param terrain: Terrain whose location is desired
     * @return Location of the given terrain
     */
    public TerrainLocation getTerrainLocation(Terrain terrain);
}
