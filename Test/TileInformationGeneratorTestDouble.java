public class TileInformationGeneratorTestDouble implements TileInformationGenerator {
    private Orientation orientation;
    private TerrainType leftType;
    private TerrainType rightType;
    private int col;
    private int row;

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void  setLeftTerrainType(TerrainType leftType) {
        this.leftType = leftType;
    }

    public void  setRightTerrainType(TerrainType rightType) {
        this.rightType = rightType;
    }

    public void setLocationColumn(int col) {
        this.col = col;
    }

    public void setLocationRow(int row) {
        this.row = row;
    }

    public TileOrientation getOrientation() {
        return new TileOrientation(this.orientation);
    }

    public Terrain getLeftTerrain() {
        return new Terrain(this.leftType);
    }

    public Terrain getRightTerrain() {
        return new Terrain(this.rightType);
    }

    public TileLocation getTileLocation() {
        return new TileLocation(this.col, this.row);
    }
}
