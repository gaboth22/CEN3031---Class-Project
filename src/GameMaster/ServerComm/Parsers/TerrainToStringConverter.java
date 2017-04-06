package GameMaster.ServerComm.Parsers;

import Terrain.Terrain.Terrain;
import java.security.InvalidParameterException;

public class TerrainToStringConverter {
    public static String getString(Terrain t) {
        if(t == Terrain.GRASSLANDS)
            return "GRASS";
        if(t == Terrain.ROCKY)
            return "ROCK";
        else if(t == Terrain.VOLCANO)
            throw new InvalidParameterException("Volcano should not be sent to server");
        else
            return t.name();
    }
}
