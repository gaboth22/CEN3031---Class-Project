package GameBoard;

import GamePieceMap.GamePieceMap;
import Play.Rule.TilePlacementRules.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.TileMap;
import Play.Rule.*;
import java.util.Map;

public class GameBoardImpl implements GameBoard {
    private GamePieceMap gamePieceMap;
    private TileMap tileMap;
    private Map<RuleType, Rule> rules;

    @Override
    public void insertRule(Rule rule) {
        rules.put(rule.getType(), rule);
    }

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
        TileMustTouchOneEdgeRule.applyRule(tileMap, gamePieceMap, tilePlacementPhase.getTileToPlace());


    }
}
