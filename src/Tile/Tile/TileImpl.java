package Tile.Tile;

import Terrain.Terrain.Terrain;
import Location.Location;

import java.security.InvalidParameterException;
import java.util.List;

public class TileImpl implements Tile {
    private List<Terrain> volcanoLeftRightTerrains;
    private List<Location> volcanoLeftRightLocations;
    private String errorMessage = "The first terrain should always be the volcano, but it is: ";

    public TileImpl(List<Terrain> terrainList, List<Location> locationList) {
        if(terrainList.get(0) != Terrain.VOLCANO) {
            throw new InvalidParameterException(errorMessage + terrainList.get(0));
        }

        volcanoLeftRightTerrains = terrainList;
        volcanoLeftRightLocations = locationList;
    }

    @Override
    public Terrain[] getArrayOfTerrains() {
        Terrain[] terrainArray = new Terrain[volcanoLeftRightTerrains.size()];
        terrainArray = volcanoLeftRightTerrains.toArray(terrainArray);
        return terrainArray;
    }

    @Override
    public Location[] getArrayOfTerrainLocations() {
        Location[] locationArray = new Location[volcanoLeftRightLocations.size()];
        locationArray = volcanoLeftRightLocations.toArray(locationArray);
        return locationArray;
    }
}
