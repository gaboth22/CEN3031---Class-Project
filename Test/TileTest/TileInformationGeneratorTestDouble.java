package TileTest;

import Terrain.*;
import Tile.TileInformationGenerator;
import Tile.TileLocation;
import Tile.TileOrientation;

public class TileInformationGeneratorTestDouble implements TileInformationGenerator {
    private TileOrientation orientation;
    private Terrain leftTerrain;
    private Terrain rightTerrain;

    public void setTileOrientation(TileOrientation orientation) {
        this.orientation = orientation;
    }

    public void setLeftTerrain(Terrain leftTerrain) {
        this.leftTerrain = leftTerrain;
    }

    public void setRightTerrain(Terrain rightTerrain) {
        this.rightTerrain = rightTerrain;
    }


    public TileOrientation getOrientation() {
        return new TileOrientationTestDouble();
    }

    public Terrain getVolcano() {
        return new Volcano();
    }

    public Terrain getLeftTerrain() {
        return this.leftTerrain;
    }

    public Terrain getRightTerrain() {
        return this.rightTerrain;
    }

    public TileLocation getTileLocation() {
        return new TileLocationTestDouble();
    }
}
