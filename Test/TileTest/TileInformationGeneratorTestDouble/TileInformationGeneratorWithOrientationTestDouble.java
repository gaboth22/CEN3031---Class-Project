package TileTest.TileInformationGeneratorTestDouble;

import Location.Location;
import Movement.Movement;
import Terrain.Terrain.Terrain;
import Tile.TileInformationGenerator.TileInformationGenerator;

public class TileInformationGeneratorWithOrientationTestDouble implements TileInformationGenerator {

    Terrain[] terrains;
    Location[] locations;
    Movement coordinateSystem;

    public TileInformationGeneratorWithOrientationTestDouble(Movement coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    public void createTile(Location volcanoLocation, OrientationTestDouble orientation, Terrain left, Terrain right) {
        switch(orientation) {
            case UP:
                this.setLocations(volcanoLocation, coordinateSystem.up(volcanoLocation), coordinateSystem.upLeft(volcanoLocation));
                break;
            case UPLEFT:
                this.setLocations(volcanoLocation, coordinateSystem.upLeft(volcanoLocation), coordinateSystem.downLeft(volcanoLocation));
                break;
            case DOWNLEFT:
                this.setLocations(volcanoLocation, coordinateSystem.downLeft(volcanoLocation), coordinateSystem.down(volcanoLocation));
                break;
            case DOWN:
                this.setLocations(volcanoLocation, coordinateSystem.down(volcanoLocation), coordinateSystem.downRight(volcanoLocation));
                break;
            case DOWNRIGHT:
                this.setLocations(volcanoLocation, coordinateSystem.downRight(volcanoLocation), coordinateSystem.upRight(volcanoLocation));
                break;
            case UPRIGHT:
                this.setLocations(volcanoLocation, coordinateSystem.upRight(volcanoLocation), coordinateSystem.up(volcanoLocation));
                break;
        }
        this.setTerrains(Terrain.VOLCANO, left, right);
    }

    private void setTerrains(Terrain t1, Terrain t2, Terrain t3) {
        terrains = new Terrain[] {t1, t2, t3};
    }

    private void setLocations(Location l1, Location l2, Location l3) {
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
