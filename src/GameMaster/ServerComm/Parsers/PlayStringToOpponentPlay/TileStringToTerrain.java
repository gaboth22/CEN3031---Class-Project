package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import Terrain.Terrain.Terrain;

import java.security.InvalidParameterException;

public class TileStringToTerrain {

    public static Terrain getTerrain(String string) {

        if(string == null) {
            throw new InvalidParameterException("The parameter passed into the function is not set");
        }

        Terrain terrainToReturn;

        if(string.equals("GRASS")) {
            terrainToReturn = Terrain.GRASSLANDS;
        }
        else if(string.equals("ROCK")) {
            terrainToReturn = Terrain.ROCKY;
        }
        else if(string.equals("LAKE")) {
            terrainToReturn = Terrain.LAKE;
        }
        else if(string.equals("JUNGLE")) {
            terrainToReturn = Terrain.JUNGLE;
        }
        else if(string.equals("VOLCANO")) {
            terrainToReturn = Terrain.VOLCANO;
        }
        else {
            throw new InvalidParameterException(string + "is not a valid terrain type.");
        }

        return terrainToReturn;
    }
}
