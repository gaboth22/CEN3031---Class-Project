public class Terrain {
    public Tile parent;
    public TerrainType type;
    public TerrainLocation location;
    public boolean hasPiece;
    public Piece piece;

    public Terrain(TerrainType type) {
        this.type = type;
    }
}
