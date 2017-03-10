public class Tile {
    public Terrain leftTerrain;
    public Terrain rightTerrain;
    public TileLocation location;
    public TileOrientation orientation;
    public int level;

    public Tile(Terrain leftTerrain,
                Terrain rightTerrain,
                TileLocation location,
                TileOrientation orientation) {
        this.leftTerrain = leftTerrain;
        this.rightTerrain = rightTerrain;
        this.location = location;
        this.orientation = orientation;
        this.level = 1;
    }

    public void increaseLevel() {
        this.level++;
    }
}
