package GameBoard;


import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.Rule.TilePlacementRules.HexesBelowTileShouldBeSameLevelRule;
import Play.Rule.TilePlacementRules.HexesBelowTileShouldBelongToTwoOrMoreTiles;
import Play.Rule.TilePlacementRules.VolcanoMustBeOnTopOfVolcanoRule;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.*;

class NukeTileHelper {

    void removeNukedVillagersFromGamePieceMap() {
        //TODO: Needs to update removed villagers from GamePieceMap
    }

    void updateTileMapWithNewlyInsertedTile() {
        //TODO: Needs to update TileMap with the newly inserted tile
    }

    boolean attemptNuke(TilePlacementPhase placementPhase, TileMap tileMap) {
        try {
            applyRulesForAttemptNuke(placementPhase, tileMap);
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private void applyRulesForAttemptNuke(TilePlacementPhase placementPhase, TileMap tileMap) throws InvalidTilePlacementRuleException {
        VolcanoMustBeOnTopOfVolcanoRule.applyRule(tileMap, placementPhase.getTileToPlace());
        HexesBelowTileShouldBeSameLevelRule.applyRule(tileMap, placementPhase.getTileToPlace());
        HexesBelowTileShouldBelongToTwoOrMoreTiles.applyRule(tileMap, placementPhase.getTileToPlace());
        // TODO: this rule is not implemented yet, but we NEED it here -- rule is CannotWipeOutSettlementRule
        // TODO: CannotWipeOutSettlementRule.applyRule()
    }
}