package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import Location.Location;
import Terrain.Terrain.Terrain;
import Tile.TileInformationGenerator.TileInformationGenerator;
import Movement.*;

import java.security.InvalidParameterException;

public class TileFromOrientationAndTerrain implements TileInformationGenerator {

    private Location[] locationsInTile;
    private Terrain[] terrainsInTile;
    private Movement coordinateSystem;

    public TileFromOrientationAndTerrain(int orientation, Location volcanoLocation, Terrain[] terrains) {
        coordinateSystem = new AxialMovement();

        setArrayOfTerrain(terrains);
        setArrayOfLocations(volcanoLocation, orientation);
    }

    private void setArrayOfLocations(Location volcanoLocation, int orientation) {
        Location leftLocation;
        Location rightLocation;

        switch(orientation) {
            case 1:
                leftLocation = coordinateSystem.downLeft(volcanoLocation);
                rightLocation = coordinateSystem.upLeft(volcanoLocation);
                break;
            case 2:
                leftLocation = coordinateSystem.upLeft(volcanoLocation);
                rightLocation = coordinateSystem.up(volcanoLocation);
                break;
            case 3:
                leftLocation = coordinateSystem.up(volcanoLocation);
                rightLocation = coordinateSystem.upRight(volcanoLocation);
                break;
            case 4:
                leftLocation = coordinateSystem.upRight(volcanoLocation);
                rightLocation = coordinateSystem.downRight(volcanoLocation);
                break;
            case 5:
                leftLocation = coordinateSystem.downRight(volcanoLocation);
                rightLocation = coordinateSystem.down(volcanoLocation);
                break;
            case 6:
                leftLocation = coordinateSystem.down(volcanoLocation);
                rightLocation = coordinateSystem.downLeft(volcanoLocation);
                break;
            default:
                throw new InvalidParameterException(orientation +
                        " is not a valid orientation. Orientation must be between 1 and 6 inclusive.");
        }
        locationsInTile = new Location[] {volcanoLocation, leftLocation, rightLocation};
    }

    private void setArrayOfTerrain(Terrain[] terrains) {
        terrainsInTile = new Terrain[3];

        Terrain leftTerrain = terrains[0];
        Terrain rightTerrain = terrains[1];

        terrainsInTile[0] = Terrain.VOLCANO;
        terrainsInTile[1] = leftTerrain;
        terrainsInTile[2] = rightTerrain;
    }

    @Override
    public Location[] getLocations() {
        return locationsInTile;
    }

    @Override
    public Terrain[] getTerrains() {
        return terrainsInTile;
    }
}
