package Steve;

import GameMaster.ServerComm.Parsers.Split;
import Terrain.Terrain.Terrain;
import java.security.InvalidParameterException;

public class BiHexTileStructureBuilderFromString {

    public static BiHexTileStructure getBiHexFromString(String terrainInfo) {

        String[] terrainStrings = Split.byPlus(terrainInfo);
        Terrain left = getTerrainFromString(terrainStrings[0]);
        Terrain right = getTerrainFromString(terrainStrings[1]);

        return new BiHexTileStructure(new Terrain[]{left, right});
    }

    private static Terrain getTerrainFromString(String terrain) {
        if(terrain.equals("ROCK"))
            return Terrain.ROCKY;

        else if(terrain.equals("LAKE"))
            return Terrain.LAKE;

        else if(terrain.equals("GRASS"))
            return Terrain.GRASSLANDS;

        else if(terrain.equals("JUNGLE"))
            return Terrain.JUNGLE;

        else
            throw new InvalidParameterException("Unexpected string to parse for Terrain, given " + terrain);
    }
}
