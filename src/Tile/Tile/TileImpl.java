package Tile.Tile;

import Terrain.Terrain.Terrain;
import Location.Location;

import java.security.InvalidParameterException;
import java.util.List;

public class TileImpl implements Tile {
    private List<Terrain> volcanoLeftRightTerrains;
    private List<Location> volcanoLeftRightLocations;
    private static String errorMessage = "The first terrain should always be the volcano, but it is: ";

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;
        Location[] locations = tile.getArrayOfTerrainLocations();
        Terrain[] terrains = tile.getArrayOfTerrains();

        for(int i = 0; i < locations.length; i++) {
            if(!locations[i].equals(volcanoLeftRightLocations.get(i)) ||
                    terrains[i] != volcanoLeftRightTerrains.get(i))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = volcanoLeftRightTerrains != null ? volcanoLeftRightTerrains.hashCode() : 0;
        result = 31 * result + (volcanoLeftRightLocations != null ? volcanoLeftRightLocations.hashCode() : 0);
        return result;
    }
}
