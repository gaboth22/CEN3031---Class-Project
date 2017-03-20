package Tile.Tile;

import Terrain.Terrain.Terrain;
import Terrain.TerrainLocation.TerrainLocation;
import java.util.List;

public class TileImpl implements Tile {
    private List<Terrain> volcanoLeftRightTerrains;
    private List<TerrainLocation> volcanoLeftRightLocations;

    public TileImpl(List<Terrain> terrainList, List<TerrainLocation> terrainLocationList) {
        volcanoLeftRightTerrains = terrainList;
        volcanoLeftRightLocations = terrainLocationList;
    }

    @Override
    public Terrain[] getArrayOfTerrains() {
        Terrain[] terrainArray = new Terrain[volcanoLeftRightTerrains.size()];
        terrainArray = volcanoLeftRightTerrains.toArray(terrainArray);
        return  terrainArray;
    }

    @Override
    public TerrainLocation[] getArrayOfTerrainLocations() {
        TerrainLocation[] locationArray = new TerrainLocation[volcanoLeftRightLocations.size()];
        locationArray = volcanoLeftRightLocations.toArray(locationArray);
        return locationArray;
    }
}
