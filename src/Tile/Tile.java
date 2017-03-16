package Tile;

import Terrain.Terrain;

public class Tile {
    private Terrain volcano;
    private Terrain leftTerrain;
    private Terrain rightTerrain;
    private TileLocation location;
    private TileOrientation orientation;
    private int level;

    public Tile(Terrain volcano,
                Terrain leftTerrain,
                Terrain rightTerrain,
                TileLocation location,
                TileOrientation orientation) {

        this.volcano = volcano;
        this.leftTerrain = leftTerrain;
        this.rightTerrain = rightTerrain;
        this.location = location;
        this.orientation = orientation;
        this.level = 1;
    }

    public void increaseLevel() {
        this.level++;
    }

    public int getLevel() {
        return this.level;
    }

    public Terrain getLeftTerrain() {
        return  this.leftTerrain;
    }

    public Terrain getRightTerrain() {
        return this.rightTerrain;
    }

    public Terrain getVolcano() {
        return this.volcano;
    }

    public TileLocation getLocation() {
        return this.location;
    }

    public TileOrientation getOrientation() {
        return this.orientation;
    }

    public void resetLocation(TileLocation newLocation) {
        this.location = location;
    }

    public void resetOrientation(TileOrientation newOrientation) {
        this.orientation = orientation;
    }
}
