public class Tile {
    public Terrain leftTerrain;
    public Terrain rightTerrain;
    public TileLocation location;
    public TileOrientation orientation;

    public Tile(Terrain leftTerrain,
                Terrain rightTerrain,
                TileLocation location,
                TileOrientation orientation) {
        this.leftTerrain = leftTerrain;
        this.rightTerrain = rightTerrain;
        this.location = location;
        this.orientation = orientation;
    }
}
