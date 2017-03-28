package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Play.Rule.PiecePlacementRules.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import TileMap.*;

public class BuildPhaseHelper {

    void typeOfPieceToPlaceIsVillager(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap)
            throws Exception {

        if(attemptSettlementExpansion(buildPhase, tileMap, gamePieceMap)) {

            Location[] locs = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(buildPhase.getLocationToPlacePieceOn());
            Settlement s = null;
            for(Location loc : locs) {
                s = SettlementCreator.getSettlementAt(gamePieceMap, loc);
                if(s.getNumberOfHexesInSettlement() > 1 && s.getSettlementOwner() == buildPhase.getPlayerID())
                    break;
            }

            if(s != null) {
                //for locations adjacent to settlement that are not volcanos
                gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
                        buildPhase.getGamePiece());
            }
        }

        else if(attemptSettlementFoundation(buildPhase, tileMap, gamePieceMap)) {

            gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(),
                    buildPhase.getGamePiece());
        }

        else {
            throw new BuildPhaseException();
        }
    }

    void typeOfPieceToPlaceIsTigerOrTotoro(BuildPhase buildPhase, TileMap tileMap, GamePieceMap gamePieceMap) throws Exception {

        if(attemptSpecialConstruction(buildPhase, tileMap, gamePieceMap)) {
            gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(), buildPhase.getGamePiece());
        }

        else {
            throw new BuildPhaseException();
        }
    }

    private boolean attemptSettlementExpansion(BuildPhase buildPhase, TileMap tileMap, GamePieceMap gamePieceMap) {

        try {
            attemptSettlementExpansionRules(buildPhase, tileMap, gamePieceMap);
        }
        catch(InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private void attemptSettlementExpansionRules(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap)
            throws InvalidPiecePlacementRuleException {

        GamePieceCannotBePlacedOnVolcanoRule.applyRule(
                tileMap,
                buildPhase.getGamePiece(),
                buildPhase.getLocationToPlacePieceOn());

        HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

        HexMustBeNextToPlayerSettlementRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID());
    }

    private boolean attemptSettlementFoundation(BuildPhase buildPhase, TileMap tileMap, GamePieceMap gamePieceMap) {

        try {
            attemptSettlementFoundationRules(buildPhase, tileMap, gamePieceMap);
        }
        catch(InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private void attemptSettlementFoundationRules(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap)
            throws InvalidPiecePlacementRuleException {

        GamePieceCannotBePlacedOnVolcanoRule.applyRule(
                tileMap,
                buildPhase.getGamePiece(),
                buildPhase.getLocationToPlacePieceOn());

        HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());
    }

    private boolean attemptSpecialConstruction(BuildPhase buildPhase, TileMap tileMap, GamePieceMap gamePieceMap) {

        try {
            attemptSpecialConstructionRules(buildPhase, tileMap, gamePieceMap);

            if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER) {
                HexHeightMustBeThreeOrHigherRule.applyRule(tileMap, buildPhase.getLocationToPlacePieceOn());
            }

            else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
                SettlementSizeMustBeFiveOrGreaterRule.applyRule(
                        gamePieceMap,
                        buildPhase.getLocationToPlacePieceOn(),
                        buildPhase.getPlayerID());
            }

            SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(
                    gamePieceMap,
                    buildPhase.getLocationToPlacePieceOn(),
                    buildPhase.getPlayerID(),
                    buildPhase.getTypeOfPieceToPlace());
        }
        catch (InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }

        return true;
    }

    private void attemptSpecialConstructionRules(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap)
            throws InvalidPiecePlacementRuleException{

        GamePieceCannotBePlacedOnVolcanoRule.applyRule(
                tileMap,
                buildPhase.getGamePiece(),
                buildPhase.getLocationToPlacePieceOn());

        HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

        HexMustBeNextToPlayerSettlementRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID());
    }
}