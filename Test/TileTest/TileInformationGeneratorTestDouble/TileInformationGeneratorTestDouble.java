package TileTest.TileInformationGeneratorTestDouble;

import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.TileInformationGenerator.TileInformationGenerator;

public class TileInformationGeneratorTestDouble implements TileInformationGenerator {
    Terrain[] terrains;
    Location[] locations;

    public void setTerrains(Terrain t1, Terrain t2, Terrain t3) {
        terrains = new Terrain[] {t1, t2, t3};
    }

    public void setLocations(Location l1, Location l2, Location l3) {
        locations = new Location[] {l1, l2, l3};
    }


    @Override
    public Terrain[] getTerrains() {
        return terrains;
    }

    @Override
    public Location[] getLocations() {
        return locations;
    }
}
