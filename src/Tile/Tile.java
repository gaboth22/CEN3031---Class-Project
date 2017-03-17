package Tile;

import Terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

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

    /*
     * I believe that the Tile should return all Terrains through getListOfTerrains. This way, if the tile shape is ever changed
     * only the implementation of getListOfTerrains and the constructor of Tile need to change. This 'abstracts' the implementation
     * of Tile further
     */
    public List<Terrain> getListOfTerrains() {
        List<Terrain> terrainsInTile = new ArrayList<Terrain>();
        terrainsInTile.add(this.leftTerrain);
        terrainsInTile.add(this.rightTerrain);
        terrainsInTile.add(this.volcano);

        return terrainsInTile;
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
