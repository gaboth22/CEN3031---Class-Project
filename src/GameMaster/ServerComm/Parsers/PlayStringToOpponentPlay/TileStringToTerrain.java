package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import Terrain.Terrain.Terrain;

import java.security.InvalidParameterException;

public class TileStringToTerrain {

    static Terrain getTerrain(String string) {

        Terrain terrainToReturn;

        if(string.equals("GRASS")) {
            terrainToReturn = Terrain.GRASSLANDS;
        }
        else if(string.equals("ROCK")) {
            terrainToReturn = Terrain.ROCKY;
        }
        else if(string.equals("LAKE")) {
            terrainToReturn = Terrain.JUNGLE;
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
