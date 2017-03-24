package GameBoard;

import GamePieceMap.GamePieceMap;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.Rule.TilePlacementRules.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
import TileMap.*;

public class GameBoardImpl implements GameBoard {
    private GamePieceMap gamePieceMap;
    private TileMap tileMap;
    private int turnNumber;
    private final int FIRST_TURN = 1;

    public GameBoardImpl() {
        this.gamePieceMap = new GamePieceMap();
        this.tileMap = new HexagonMap();
        this.turnNumber = 0;
    }

    @Override
    public void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception {

        if (attemptFirstTilePlacement(tilePlacementPhase)) {
            tileMap.insertTile(tilePlacementPhase.getTileToPlace());
            incrementTurnNumber();
            return;
        }

        else if(attemptSimpleTilePlacement(tilePlacementPhase)) {
            tileMap.insertTile(tilePlacementPhase.getTileToPlace());
            incrementTurnNumber();
            return;
        }

        else if(attemptNuke(tilePlacementPhase)){
            //TODO: Needs to update removed villagers from GamePieceMap
            //TODO: Needs to update TileMap with the newly inserted tile
            return;
        }

        else {
            throw new TilePlacementPhaseException();
        }
    }

    private boolean attemptFirstTilePlacement(TilePlacementPhase placementPhase) {
        try {
            if(turnNumber != FIRST_TURN)
                throw new InvalidTilePlacementRuleException();
            HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, placementPhase.getTileToPlace());
            FirstTileMustBePlacedWithVolcanoAtCenterOfBoard.applyRule(tileMap, placementPhase.getTileToPlace());
        }
        catch( InvalidTilePlacementRuleException e) {
            System.out.println(e.getStackTrace());
            return false;
        }

        return true;
    }

    private boolean attemptSimpleTilePlacement(TilePlacementPhase placementPhase) {
        try {
            HexesBelowShouldBeAtLevelZeroRule.applyRule(tileMap, placementPhase.getTileToPlace());
            TileMustTouchOneEdgeRule.applyRule(tileMap, placementPhase.getTileToPlace());
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getStackTrace());
            return false;
        }

        return true;
    }

    private boolean attemptNuke(TilePlacementPhase placementPhase) {
        try {
            VolcanoMustBeOnTopOfVolcanoRule.applyRule(tileMap, placementPhase.getTileToPlace());
            HexesBelowTileShouldBeSameLevelRule.applyRule(tileMap, placementPhase.getTileToPlace());
            HexesBelowTileShouldBelongToTwoOrMoreTiles.applyRule(tileMap, placementPhase.getTileToPlace());
            // TODO: this rule is not implemented yet, but we NEED it here -- rule is CannotWipeOutSettlementRule
            // TODO: CannotWipeOutSettlementRule.applyRule()
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getStackTrace());
            return false;
        }

        return true;
    }

    private void incrementTurnNumber() {
        this.turnNumber++;
    }
}
