package BuildPhase;

import GameBoard.GameBoard;
import Terrain.TerrainLocation;

public class FoundSettlement extends BuildPhase {
    private TerrainLocation terrainLocation;
    private GameBoard gameBoard;

    public FoundSettlement(TerrainLocation terrainLocation) {
        this.terrainLocation = terrainLocation;
    }

    public void build() {
        this.gameBoard.foundSettlement(this.terrainLocation);
    }
}