package Tile.TileFactory;

import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import Tile.TileInformationGenerator.TileInformationGenerator;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Arrays;

public class TileFactory {
    private static TileFactory factory;

    static public TileFactory getTileFactory() {
    if(factory == null) {
        factory = new TileFactory();
    }

    return factory;
    }

    public Tile makeTile(TileInformationGenerator infoGenerator) throws InvalidParameterException, ImpossibleTileException {
        List<Terrain> terrains = Arrays.asList(infoGenerator.getTerrains());
        checkThatFirstTerrainIsVolcano(terrains);
        checkThatThereIsOnlyOneVolcano(terrains);
        List<Location> locations = Arrays.asList(infoGenerator.getLocations());
        checkLocationsArePossible(locations);
        checkNoLocationsAreTheSame(locations);
        return new TileImpl(terrains, locations);
    }

    private void checkThatFirstTerrainIsVolcano(List<Terrain> terrains) {
        if(terrains.get(0) != Terrain.VOLCANO) {
            throw new InvalidParameterException(firstVolcanoErrorMessage + terrains.get(0));
        }
    }

    private String firstVolcanoErrorMessage = "First terrain should always be the volcano, bit it is: ";

    private void checkThatThereIsOnlyOneVolcano(List<Terrain> terrains) throws ImpossibleTileException {
        int volcanoCount = 0;
        for(Terrain t : terrains) {
            if(t == Terrain.VOLCANO)
                volcanoCount++;
        }

        if(volcanoCount > 1)
            throw new ImpossibleTileException(repeatedVolcanoErrorMessage + volcanoCount);
    }

    private String repeatedVolcanoErrorMessage = "There should only be one volcano per Tile, but there are: ";

    private void checkLocationsArePossible(List<Location> locations) throws ImpossibleTileException {
        int volcanoX = Math.abs(locations.get(0).getX());
        int volcanoY = Math.abs(locations.get(0).getY());
        int secondLocX = Math.abs(locations.get(1).getX());
        int secondLocY = Math.abs(locations.get(1).getY());
        int thirdLocX = Math.abs(locations.get(2).getX());
        int thirdLocY = Math.abs(locations.get(2).getY());
        int secondLocXCheck = Math.abs(volcanoX - secondLocX);
        int secondLocYCheck = Math.abs(volcanoY - secondLocY);
        int thirdLocXCheck = Math.abs(volcanoX - thirdLocX);
        int thirdLocYCheck = Math.abs(volcanoY - thirdLocY);

        if(secondLocXCheck > 1 || secondLocYCheck > 1 || thirdLocXCheck > 1 || thirdLocYCheck > 1) {
            throw new ImpossibleTileException("Locations are not possible for a connected Tile");
        }
    }

    private void checkNoLocationsAreTheSame(List<Location> locations) throws ImpossibleTileException {
        for(int i = 0; i < locations.size(); i++) {
            for(int j = 0; j < locations.size(); j++) {
                if(locations.get(i) != locations.get(j)) {
                    if(areEqual(locations.get(i), locations.get(j))) {
                        throw new ImpossibleTileException("Two locations cannot be the same on a Tile");
                    }
                }
            }
        }
    }

    private boolean areEqual(Location l1, Location l2) {
        return l1.getX() == l2.getX() && l1.getY() == l2.getY();
    }
}
