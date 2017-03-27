package GameBoard;


import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.Rule.TilePlacementRules.CannotWipeOutSettlementRule;
import Play.Rule.TilePlacementRules.HexesBelowTileShouldBeSameLevelRule;
import Play.Rule.TilePlacementRules.HexesBelowTileShouldBelongToTwoOrMoreTiles;
import Play.Rule.TilePlacementRules.VolcanoMustBeOnTopOfVolcanoRule;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileMap.*;

class NukeTileHelper {

    void removeNukedVillagersFromGamePieceMap(TilePlacementPhase tilePlacementPhase, GamePieceMap pieceMap) {

        Location[] locations = tilePlacementPhase.getTileToPlace().getArrayOfTerrainLocations();
        for(int i = 0; i < locations.length; i++) {
            if(pieceMap.isThereAPieceAt(locations[i]))
                pieceMap.removeAPieceAt(locations[i]);
        }
    }

    void updateTileMapWithNewlyInsertedTile(TilePlacementPhase placementPhase, TileMap tileMap) throws Exception {
        tileMap.insertTile(placementPhase.getTileToPlace());
    }

    boolean attemptNuke(TilePlacementPhase placementPhase, TileMap tileMap, GamePieceMap pieceMap) {

        try {
            applyRulesForAttemptNuke(placementPhase, tileMap, pieceMap);
        }
        catch(InvalidTilePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private void applyRulesForAttemptNuke(
            TilePlacementPhase placementPhase,
            TileMap tileMap,
            GamePieceMap pieceMap)
            throws InvalidTilePlacementRuleException {

        VolcanoMustBeOnTopOfVolcanoRule.applyRule(tileMap, placementPhase.getTileToPlace());
        HexesBelowTileShouldBeSameLevelRule.applyRule(tileMap, placementPhase.getTileToPlace());
        HexesBelowTileShouldBelongToTwoOrMoreTiles.applyRule(tileMap, placementPhase.getTileToPlace());
        CannotWipeOutSettlementRule.applyRule(pieceMap, placementPhase.getTileToPlace());
    }
}