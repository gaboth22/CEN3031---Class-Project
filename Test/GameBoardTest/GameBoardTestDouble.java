package GameBoardTest;

import GameBoard.*;
import Player.PlayerID;
import Terrain.Terrain;
import Terrain.TerrainLocation;

import java.util.List;

public class GameBoardTestDouble implements GameBoard {
    private List<Settlement> playerOneSettlemtns;
    private List<Settlement> playerTwoSettlements;

    private List<Settlement> getPlayerSettlement(PlayerID ownerId) {
        if(ownerId == PlayerID.PLAYER_ONE)
            return this.playerOneSettlemtns;
        else
            return this.playerTwoSettlements;
    }

    public GameBoardTestDouble() {

    }

    public List<Settlement> getAllSettlements(PlayerID ownerId) {
        return getPlayerSettlement(ownerId);
    }

    public Settlement getSettlement(PlayerID ownerId, TerrainLocation anyLocationWithinSettlement) {
        List<Settlement> playerSettlements = getPlayerSettlement(ownerId);

        for(Settlement settlement : playerSettlements) {
            if(terrainLocationWithinSettlement(settlement, anyLocationWithinSettlement))
                return settlement;
        }

        return new Settlement();
    }

    private boolean terrainLocationWithinSettlement(Settlement settlement, TerrainLocation location) {
        for(Terrain terrain : settlement) {
            if(terrain.getLocation() == location)
                return true;
        }

        return false;
    }
}
