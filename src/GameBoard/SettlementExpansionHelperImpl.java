package GameBoard;

import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Settlements.Creation.Settlement;
import Settlements.Expansion.SettlementExpansion;
import Terrain.Terrain.Terrain;
import TileMap.*;
import GamePieceMap.*;
import Location.*;

public class SettlementExpansionHelperImpl implements SettlementExpansionHelper {
    private BuildPhase buildPhase;
    private TileMap tileMap;
    private GamePieceMap gamePieceMap;

    public SettlementExpansionHelperImpl(BuildPhase buildPhase, TileMap tileMap, GamePieceMap gamePieceMap){
        this.buildPhase = buildPhase;
        this.tileMap = tileMap;
        this.gamePieceMap = gamePieceMap;
    }

    public void expandSettlement() throws BuildPhaseException {
        Settlement settlementToExpand = buildPhase.getSettlement();
        Terrain terrainToExpandInto = getTerrainToPlaceOn(buildPhase, tileMap);

        SettlementExpansion.findLocationsToExpandInto(tileMap.getAllHexagons(), settlementToExpand, gamePieceMap, terrainToExpandInto);
    }

    public Location[] getListOfLocationsExpandedTo(){
        Location[] locs = null;
        //TODO: implementation
        return locs;
    }

    private Terrain getTerrainToPlaceOn(BuildPhase buildPhase, TileMap tileMap) throws BuildPhaseException {
        Location locationOfTerrain = buildPhase.getLocationToPlacePieceOn();

        if(tileMap.hasHexagonAt(locationOfTerrain)) {
            Hexagon hexagonAtLocation = tileMap.getHexagonAt(locationOfTerrain);
            return hexagonAtLocation.getTerrain();
        }
        throw new BuildPhaseException("The location given by Build Phase for expansion, " + locationOfTerrain + ", does not have a hexagon.");
    }

}
