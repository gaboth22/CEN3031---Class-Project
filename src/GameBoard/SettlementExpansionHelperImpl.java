package GameBoard;

import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Settlements.Creation.Settlement;
import Settlements.Expansion.SettlementExpansion;
import Terrain.Terrain.Terrain;
import TileMap.*;
import GamePieceMap.*;
import Location.*;

import java.util.List;

public class SettlementExpansionHelperImpl implements SettlementExpansionHelper {
    private BuildPhase buildPhase;
    private TileMap tileMap;
    private GamePieceMap gamePieceMap;

    private List<Location> listOfLocationsExpandedTo;

    public SettlementExpansionHelperImpl(BuildPhase buildPhase, TileMap tileMap, GamePieceMap gamePieceMap){
        this.buildPhase = buildPhase;
        this.tileMap = tileMap;
        this.gamePieceMap = gamePieceMap;
    }

    public void expandSettlement() throws BuildPhaseException {
        Settlement settlementToExpand = buildPhase.getSettlement();
        Terrain terrainToExpandInto = getTerrainToPlaceOn(buildPhase, tileMap);

        listOfLocationsExpandedTo = SettlementExpansion.findLocationsToExpandInto(tileMap.getAllHexagons(), settlementToExpand, gamePieceMap, terrainToExpandInto);
        SettlementExpansion.expandSettlement(gamePieceMap,settlementToExpand, listOfLocationsExpandedTo);
    }

    public Location[] getArrayOfLocationsExpandedTo() {
        if(listOfLocationsExpandedTo == null) {
            return new Location[] {};
        }
        Location[] locationsExpandedTo = new Location[] {};
        locationsExpandedTo = listOfLocationsExpandedTo.toArray(locationsExpandedTo);
        return locationsExpandedTo;
    }

    private Terrain getTerrainToPlaceOn(BuildPhase buildPhase, TileMap tileMap) throws BuildPhaseException {
        Location locationOfTerrain = buildPhase.getLocationToPlacePieceOn();

        if (tileMap.hasHexagonAt(locationOfTerrain)) {
            Hexagon hexagonAtLocation = tileMap.getHexagonAt(locationOfTerrain);
            return hexagonAtLocation.getTerrain();
        }
        throw new BuildPhaseException("The location given by Build Phase for expansion, " + locationOfTerrain + ", does not have a hexagon.");
    }
}
