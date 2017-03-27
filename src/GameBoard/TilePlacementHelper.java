package GameBoard;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.Rule.TilePlacementRules.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.*;

public class TilePlacementHelper {
    private boolean wasFirstTilePlaced;

    public TilePlacementHelper() {
        wasFirstTilePlaced = false;
    }

    boolean attemptFirstTilePlacementOrSimpleTilePlacement(
            TilePlacementPhase tilePlacementPhase,
            TileMap tileMap,
            int turnNumber,
            int firstTurn)
            throws InvalidTilePlacementRuleException {

        return  attemptFirstTilePlacement(tilePlacementPhase, tileMap, turnNumber, firstTurn) ||
                attemptSimpleTilePlacement(tilePlacementPhase, tileMap);
    }

    void insertTile(TilePlacementPhase tilePlacementPhase, TileMap tileMap) throws Exception {
        tileMap.insertTile(tilePlacementPhase.getTileToPlace());
    }

    public boolean attemptFirstTilePlacement(
            TilePlacementPhase placementPhase,
            TileMap tileMap,
            int turnNumber,
            int firstTurn)
            throws InvalidTilePlacementRuleException {

        try {
            if(turnNumber != firstTurn)
                throw new InvalidTilePlacementRuleException();

            if(!wasFirstTilePlaced) {
                applyRulesForFirstTilePlacement(placementPhase, tileMap);
                wasFirstTilePlaced = true;
            }
        }
        catch( InvalidTilePlacementRuleException e) {
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
        wasFirstTilePlaced = true;
    }
}