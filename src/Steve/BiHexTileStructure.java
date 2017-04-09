package Steve;

import Terrain.Terrain.*;

public class BiHexTileStructure {

    Terrain leftTerrain; // Terrain A in networking protocol v1.1
    Terrain rightTerrain; // Terrain B in networking protocol v1.1

    //Left is the counterclockwise-most terrain.
    public BiHexTileStructure(Terrain[] terrains) {
        leftTerrain = terrains[0];
        rightTerrain = terrains[1];
    }

    public Terrain getLeftTerrain() {
        return  leftTerrain;
    }

    public Terrain getRightTerrain() {
        return rightTerrain;
    }
}