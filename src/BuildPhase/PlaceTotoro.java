package BuildPhase;

import GameBoard.*;
import Terrain.TerrainLocation;

public class PlaceTotoro extends BuildPhase {
    private GameBoard gameBoard;
    private TerrainLocation locationToExpandOn;
    private Settlement settlementToExpand;

    public PlaceTotoro(Settlement settlementToExpand, TerrainLocation locationToExpandOn) {
        this.settlementToExpand = settlementToExpand;
        this.locationToExpandOn = locationToExpandOn;
    }

    public void build() {
        this.gameBoard.appendTotoroToSettlement(this.settlementToExpand, this.locationToExpandOn);
    }
}