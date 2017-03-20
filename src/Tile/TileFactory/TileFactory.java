package Tile.TileFactory;

import Terrain.Terrain.Terrain;
import Terrain.TerrainLocation.TerrainLocation;
import Terrain.TerrainMovement.AxialMovement;
import Terrain.TerrainMovement.TerrainMovement;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import Tile.TileInformationGenerator.TileInformationGenerator;
import Tile.TileOrientation.TileOrientation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileFactory {
    private TerrainMovement locationGenerator;
    private static TileFactory factory = null;

    private TileFactory() {
        locationGenerator = new AxialMovement();
    }

    static public TileFactory getTileFactory() {
        if(factory == null)
            factory = new TileFactory();

        return factory;
    }

    public void setLocationGenerator(TerrainMovement locationGenerator) {
        this.locationGenerator = locationGenerator;
    }

    public Tile makeTile(TileInformationGenerator tileInformationGenerator) {

        int volcanoRow = tileInformationGenerator.getRow();
        int volcanoCol = tileInformationGenerator.getCol();
        TileOrientation orientation = tileInformationGenerator.getOrientation();
        Terrain[] terrains = tileInformationGenerator.getTerrains();
        TerrainLocation volcanoLocation = new TerrainLocation(volcanoRow, volcanoCol);
        TerrainLocation leftLocation = null;
        TerrainLocation rightLocation = null;

        if(orientation == TileOrientation.UP) {
            leftLocation = locationGenerator.down(volcanoLocation);
            rightLocation = locationGenerator.downRight(volcanoLocation);
        }

        else if(orientation == TileOrientation.RIGHT) {
            leftLocation = locationGenerator.upLeft(volcanoLocation);
            rightLocation = locationGenerator.downLeft(volcanoLocation);
        }

        else if(orientation == TileOrientation.DOWN) {
            leftLocation = locationGenerator.upRight(volcanoLocation);
            rightLocation = locationGenerator.up(volcanoLocation);
        }

        else if(orientation == TileOrientation.LEFT) {
            leftLocation = locationGenerator.downRight(volcanoLocation);
            rightLocation = locationGenerator.upRight(volcanoLocation);
        }

        List<Terrain> terrainList = new ArrayList<>(Arrays.asList(terrains));
        List<TerrainLocation> locationList = new ArrayList<>(Arrays.asList(volcanoLocation, leftLocation, rightLocation));

        return new TileImpl(terrainList, locationList);
    }
}
