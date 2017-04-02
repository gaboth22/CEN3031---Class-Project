package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Play.Rule.PiecePlacementRules.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;
import TileMap.*;

public class BuildPhaseHelper {
    private int lastPlayVillagerScore = 0;
    private int lastPlayVillagersUsed = 0;
    private SettlementExpansionHelper expansionHelper;

    public void setSettlementExpansionHelper(SettlementExpansionHelper expansionHelper){
        this.expansionHelper = expansionHelper;
    }

    public void insertVillager(BuildPhase buildPhase, GamePieceMap gamePieceMap, TileMap tileMap) throws Exception{
        gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(), buildPhase.getGamePiece());
        updateLastPlayScoreForVillager(buildPhase.getLocationToPlacePieceOn(), tileMap);
        updateLastPlayVillagersUsed(buildPhase.getLocationToPlacePieceOn(), tileMap);
    }

    public boolean attemptSettlementFoundation(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap,
            Player activePlayer) {
        try {
            attemptSettlementFoundationRules(buildPhase, tileMap, gamePieceMap, activePlayer);
        }
        catch(InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }
        return true;
    }

    public void insertSpecialPiece(BuildPhase buildPhase, GamePieceMap gamePieceMap) throws Exception {
        gamePieceMap.insertAPieceAt(buildPhase.getLocationToPlacePieceOn(), buildPhase.getGamePiece());
    }

    public void expandSettlement(BuildPhase buildPhase, TileMap tileMap, GamePieceMap gamePieceMap) throws BuildPhaseException {
        setSettlementExpansionHelper(new SettlementExpansionHelperImpl(buildPhase, tileMap, gamePieceMap));

        expansionHelper.expandSettlement();
        Location[] locationsExpandedTo = expansionHelper.getArrayOfLocationsExpandedTo();
        System.out.println(locationsExpandedTo);
        for(int i = 0; i < locationsExpandedTo.length; i++){
            updateLastPlayScoreForVillager(locationsExpandedTo[i], tileMap);
            updateLastPlayVillagersUsed(locationsExpandedTo[i], tileMap);
        }
    }

    public boolean attemptSettlementExpansion(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap,
            Player activePlayer) {

        try {
            attemptSettlementExpansionRules(buildPhase, tileMap, gamePieceMap, activePlayer);
        }
        catch(InvalidPiecePlacementRuleException e) {
            System.out.println(e.getClass());
            return false;
        }
        return true;
    }

    private void updateLastPlayScoreForVillager(Location locationVillagerPlacedOn, TileMap tileMap){
        int height = tileMap.getHeightAt(locationVillagerPlacedOn);
        lastPlayVillagerScore += height * height;
    }

    private void updateLastPlayVillagersUsed(Location locationVillagerPlacedOn, TileMap tileMap){
        int height = tileMap.getHeightAt(locationVillagerPlacedOn);
        lastPlayVillagersUsed += height;
    }

    private void attemptSettlementFoundationRules(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap,
            Player activePlayer)
            throws InvalidPiecePlacementRuleException {

        FoundationMustBeLevelOneRule.applyRule(tileMap, buildPhase.getLocationToPlacePieceOn());

        PlayerMustHavePieceRule.applyRule(activePlayer, gamePieceMap, buildPhase, tileMap);

        GamePieceCannotBePlacedOnVolcanoRule.applyRule(
                tileMap,
                buildPhase.getGamePiece(),
                buildPhase.getLocationToPlacePieceOn());

        HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());
    }

    private void attemptSettlementExpansionRules(
            BuildPhase buildPhase,
            TileMap tileMap,
            GamePieceMap gamePieceMap,
            Player activePlayer)
            throws InvalidPiecePlacementRuleException {

        PlayerMustHavePieceRule.applyRule(activePlayer, gamePieceMap, buildPhase, tileMap);

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
            GamePieceMap gamePieceMap,
            Player activePlayer) {

        try{
            attemptTotoroPlacementRules(buildPhase, tileMap, gamePieceMap, activePlayer);
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
            GamePieceMap gamePieceMap,
            Player activePlayer)
            throws InvalidPiecePlacementRuleException{

        PlayerMustHavePieceRule.applyRule(activePlayer, gamePieceMap, buildPhase, tileMap);

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
            GamePieceMap gamePieceMap,
            Player activePlayer) {

        try{
            attemptTigerPlacementRules(buildPhase, tileMap, gamePieceMap, activePlayer);
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
            GamePieceMap gamePieceMap,
            Player activePlayer)
            throws InvalidPiecePlacementRuleException{

        PlayerMustHavePieceRule.applyRule(activePlayer, gamePieceMap, buildPhase, tileMap);

        GamePieceCannotBePlacedOnVolcanoRule.applyRule(
                tileMap,buildPhase.getGamePiece(),
                buildPhase.getLocationToPlacePieceOn());

        HexMustBeNextToPlayerSettlementRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID());

        HexBelowMustNotHavePieceRule.applyRule(gamePieceMap, buildPhase.getLocationToPlacePieceOn());

        HexHeightMustBeThreeOrHigherRule.applyRule(tileMap, buildPhase.getLocationToPlacePieceOn());

        SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(
                gamePieceMap,
                buildPhase.getLocationToPlacePieceOn(),
                buildPhase.getPlayerID(),
                buildPhase.getTypeOfPieceToPlace());
    }

    public void setLastPlayVillagerScoreToZero(){
        lastPlayVillagerScore = 0;
    }

    public void setLastPlayVillagersUsedToZero(){
        lastPlayVillagersUsed = 0;
    }

    public int getLastPlayScoreForVillagers(){
        return lastPlayVillagerScore;
    }

    public int getLastPlayVillagersUsed(){
        return lastPlayVillagersUsed;
    }
}