package Tile;

import Terrain.Terrain;

public class TileFactory {
    private TileInformationGenerator tileInformation;

    public TileFactory(TileInformationGenerator tileInformation) {
        this.tileInformation = tileInformation;
    }

    public Tile makeTile() {
        Terrain volcano = tileInformation.getVolcano();
        Terrain leftTerrain = tileInformation.getLeftTerrain();
        Terrain rightTerrain = tileInformation.getRightTerrain();
        TileOrientation tileOrientation = tileInformation.getOrientation();
        TileLocation tileLocation = tileInformation.getTileLocation();
        return new Tile(volcano, leftTerrain, rightTerrain, tileLocation, tileOrientation);
    }
}
