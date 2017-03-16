package Terrain;

public abstract class TerrainLocation {
    int row;
    int column;
    int height;

    int getRow(){
        return this.row;
    }

    int getColumn(){
        return this.column;
    }

    int getHeight(){
        return this.height;
    }

}
