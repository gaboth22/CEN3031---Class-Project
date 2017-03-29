package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Play.BuildPhase.BuildType;
import Play.Rule.PiecePlacementRules.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import TileMap.*;

class BuildPhaseHelper {
    private int lastPlayVillagerScore = 0;

    public void insertVillager(BuildPhase buildPhase, GamePieceMap gamePieceMap, TileMap tileMap) throws Exception{
        gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(), buildPhase.getGamePiece());
        updateLastPlayScoreForVillager(buildPhase, tileMap);
    }

    public void insertSpecialPiece(BuildPhase buildPhase, GamePieceMap gamePieceMap) throws Exception{
        gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(), buildPhase.getGamePiece());
    }

    public void expandSettlement(){
        //TODO: expand function call here and call update score
    }

    public boolean attemptSettlementFoundation(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap) {
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

    public boolean attemptSettlementExpansion(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap) {
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

    public boolean attemptTotoroPlacement(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap) {

        try{
            attemptTotoroPlacementRules(buildPhase, tileMap, gamePieceMap);

        }
        catch(InvalidPiecePlacementRuleException e){
            System.out.println(e.getClass());
            return false;
        }
        return true;
    }

    private void attemptTotoroPlacementRules(
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

        SettlementSizeMustBeFiveOrGreaterRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID());

        SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID(),
                buildPhase.getTypeOfPieceToPlace());


    }

    public boolean attemptTigerPlacement(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap) {

        try{
            attemptTigerPlacementRules(buildPhase, tileMap, gamePieceMap);
        }
        catch(InvalidPiecePlacementRuleException e){
            System.out.println(e.getClass());
            return false;
        }
        return true;
    }

    private void attemptTigerPlacementRules(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap)
            throws InvalidPiecePlacementRuleException{

        GamePieceCannotBePlacedOnVolcanoRule.applyRule(
                tileMap,buildPhase.getGamePiece(),
                buildPhase.getLocationToPlacePieceOn());

        HexMustBeNextToPlayerSettlementRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID());

        HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

        SettlementSizeMustBeFiveOrGreaterRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID());

        SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID(),
                buildPhase.getTypeOfPieceToPlace());

    }

    private void updateLastPlayScoreForVillager(BuildPhase buildPhase, TileMap tileMap){
        Location loc = buildPhase.getLocationToPlacePieceOn();
        int height = tileMap.getHeightAt(loc);
        lastPlayVillagerScore += height * height;
    }

    public void setLastPlayVillagerScoreToZero(){
        lastPlayVillagerScore = 0;
    }

    public int getLastPlayScoreForVillagers(){
        return lastPlayVillagerScore;
    }
}