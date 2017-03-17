package GameBoardTest;

import GameBoard.*;
import Player.PlayerID;
import Terrain.*;
import TerrainTest.TerrainLocationTestDouble;
import Tile.*;
import BuildPhase.BuildData;
import TileTest.TileLocationTestDouble;
import TileTest.TileOrientationTestDouble;

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
        return findSettlementWithinPlayerSettlements(playerSettlements, anyLocationWithinSettlement);
    }

    private Settlement findSettlementWithinPlayerSettlements(List<Settlement> playerSettlements,
                                                             TerrainLocation anyLocationWithinSettlement) {
        for(Settlement settlement : playerSettlements) {
            if(terrainLocationWithinSettlement(settlement, anyLocationWithinSettlement))
                return settlement;
        }

        /*
         * If settlement does not exist, return empty settlement
         */
        return new Settlement();
    }

    private boolean terrainLocationWithinSettlement(Settlement settlement, TerrainLocation location) {
        for(Terrain terrain : settlement) {
            if(terrain.getLocation() == location)
                return true;
        }

        return false;
    }

    public Terrain getTerrain(TerrainLocation location) {
        return new Volcano();
    }

    public Tile getTile(TileLocation location) {
        /*
         * Hardcoded for now
         */
        return  new Tile(new Volcano(),
                        new GrasslandsTerrain(),
                        new RockyTerrain(),
                        new TileLocationTestDouble(),
                        new TileOrientationTestDouble());
    }

    public void appendMeeplesToSettlement(Settlement settlement, PlayerID playerId) {
    }

    public void appendTotoroToSettlement(Settlement settlement, TerrainLocation location) {
    }

    public void insertTile(Tile tile, TileLocation location) {
    }

    public void nukeTiles(Tile tile, TileLocation location){

    }

    public void removeSettlement(Settlement settlementToRemove, PlayerID playerID) {
        List<Settlement> playerSettlements = getPlayerSettlement(playerID);
        playerSettlements.remove(settlementToRemove);
    }

    public void doBuildAction(BuildData data) {
    }

    public void foundSettlement(TerrainLocation location) {
    }

    public TerrainLocation getTerrainLocation(Terrain terrain) {
        return new TerrainLocationTestDouble(0,0,0); //TODO: implement valid action here
    }

    public TileLocation getTileLocation(Tile tile) {
        return new TileLocationTestDouble();
    }

    public void nukeTiles(Tile newTile, TileLocation locationOfNewTiel) {
    }
}