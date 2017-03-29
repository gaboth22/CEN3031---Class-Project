package GameBoard;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.Rule.TilePlacementRules.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.*;

public class TilePlacementHelper {

    public TilePlacementHelper() {}

    void insertTile(TilePlacementPhase tilePlacementPhase, TileMap tileMap) throws Exception {
        tileMap.insertTile(tilePlacementPhase.getTileToPlace());
    }

    public boolean attemptFirstTilePlacement(
            TilePlacementPhase placementPhase,
            TileMap tileMap)
            throws InvalidTilePlacementRuleException {

        try {
            applyRulesForFirstTilePlacement(placementPhase, tileMap);
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }
        return true;
    }

    private void applyRulesForFirstTilePlacement(TilePlacementPhase placementPhase, TileMap tileMap)
            throws InvalidTilePlacementRuleException {

        HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, placementPhase.getTileToPlace());
        FirstTileMustBePlacedWithVolcanoAtCenterOfBoard.applyRule(tileMap, placementPhase.getTileToPlace());
    }

    public boolean attemptSimpleTilePlacement(TilePlacementPhase placementPhase, TileMap tileMap) {
        try {
            applyRulesForSimpleTilePlacement(placementPhase, tileMap);
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }
        return true;
    }

    private void applyRulesForSimpleTilePlacement(
            TilePlacementPhase placementPhase,
            TileMap tileMap)
            throws InvalidTilePlacementRuleException {

        HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, placementPhase.getTileToPlace());
        TileMustTouchOneEdgeRule.applyRule(tileMap, placementPhase.getTileToPlace());
    }
}