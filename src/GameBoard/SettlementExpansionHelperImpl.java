package GameBoard;

import Play.BuildPhase.BuildPhase;
import TileMap.TileMap;
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

    public void expandSettlement(){

        //TODO: implementation
    }

    public Location[] getListOfLocationsExpandedTo(){
        Location[] locs = null;
        //TODO: implementation
        return locs;
    }

}
