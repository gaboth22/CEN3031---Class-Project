package Steve;

import Terrain.Terrain.*;

public class TriHexTileStructure {

    Terrain leftTerrain;
    Terrain rightTerrain;

    //Left is the counterclockwise-most terrain.
    public TriHexTileStructure(Terrain[] terrains) {
        leftTerrain = terrains[0];
        rightTerrain = terrains[1];
    }


}