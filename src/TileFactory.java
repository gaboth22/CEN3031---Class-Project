public class TileFactory {
    private TileInformationGenerator tileInformation;

    public TileFactory(TileInformationGenerator tileInformation) {
        this.tileInformation = tileInformation;
    }

    public Tile makeTile() {
        Terrain leftTerrain = tileInformation.getLeftTerrain();
        Terrain rightTerrain = tileInformation.getRightTerrain();
        TileOrientation tileOrientation = tileInformation.getOrientation();
        TileLocation tileLocation = tileInformation.getTileLocation();
        return new Tile(leftTerrain, rightTerrain, tileLocation, tileOrientation);
    }
}
