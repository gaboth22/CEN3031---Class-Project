public interface TileInformationGenerator {
    TileOrientation getOrientation();

    Terrain getLeftTerrain();

    Terrain getRightTerrain();

    TileLocation  getTileLocation();
}
