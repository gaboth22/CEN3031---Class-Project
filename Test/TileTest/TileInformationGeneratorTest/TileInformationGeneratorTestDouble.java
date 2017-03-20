package TileTest.TileInformationGeneratorTest;

import Terrain.Terrain.Terrain;
import Tile.TileInformationGenerator.TileInformationGenerator;
import Tile.TileOrientation.TileOrientation;

public class TileInformationGeneratorTestDouble implements TileInformationGenerator {
    private int col;
    private int row;
    private Terrain[] terrains;
    private TileOrientation orientation;

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setTerrains(Terrain[] terrains) {
        this.terrains = terrains;
    }

    public void setOrientation(TileOrientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public Terrain[] getTerrains() {
        return terrains;
    }

    @Override
    public TileOrientation getOrientation() {
        return orientation;
    }
}
