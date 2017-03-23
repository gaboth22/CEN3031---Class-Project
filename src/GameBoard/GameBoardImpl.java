package GameBoard;

import GamePieceMap.GamePieceMap;
import Play.Rule.TilePlacementRules.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.TileMap;

public class GameBoardImpl implements GameBoard {
    private GamePieceMap gamePieceMap;
    private TileMap tileMap;

    @Override
    public void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception {


        try{
            checkSimpleTilePlacementRules(tilePlacementPhase);
            tileMap.insertTile(tilePlacementPhase.getTileToPlace());
            return;
        }
        catch(Exception e) {
        }

        try {

        }
        catch(Exception e) {
        }
    }

    private void checkSimpleTilePlacementRules(TilePlacementPhase tilePlacementPhase) throws Exception {
        TileMustTouchOneEdgeRule.applyRule(tileMap, tilePlacementPhase.getTileToPlace());


    }
}
