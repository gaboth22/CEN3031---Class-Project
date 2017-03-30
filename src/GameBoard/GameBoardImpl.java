package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import GamePieceMap.GamePieceMap;
import Play.BuildPhase.BuildPhaseException;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import TileMap.*;

import java.util.HashMap;
import java.util.Map;

public class GameBoardImpl implements GameBoard {
    private static final int totoroScore = 200;
    private static final int tigerScore = 75;

    private GamePieceMap gamePieceMap;
    private TileMap tileMap;
    private int turnNumber;
    private final int FIRST_TURN = 1;

    private TilePlacementHelper tilePlacementHelper;
    private NukeTileHelper nukeTileHelper;
    private BuildPhaseHelper buildPhaseHelper;

    int playerOneScore;
    int playerTwoScore;

    public GameBoardImpl() {
        this.gamePieceMap = new GamePieceMap();
        this.tileMap = new HexagonMap();
        this.turnNumber = FIRST_TURN;
        this.tilePlacementHelper = new TilePlacementHelper();
        this.nukeTileHelper = new NukeTileHelper();
        this.buildPhaseHelper = new BuildPhaseHelper();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
    }

    @Override
    public void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception {
        if (tilePlacementPhase.getTilePlacementType() == TilePlacementType.FIRST_PLACEMENT) {
            if (tilePlacementHelper.attemptFirstTilePlacement(
                    tilePlacementPhase,
                    tileMap)) {

                tilePlacementHelper.insertTile(tilePlacementPhase, tileMap);
                incrementTurnNumber();
            }
            else throw new TilePlacementPhaseException("First placement failed");
        }
        else if (tilePlacementPhase.getTilePlacementType() == TilePlacementType.SIMPLE_PLACEMENT) {
            if (tilePlacementHelper.attemptSimpleTilePlacement(
                    tilePlacementPhase,
                    tileMap)) {

                tilePlacementHelper.insertTile(tilePlacementPhase, tileMap);
                incrementTurnNumber();
            }
            else throw new TilePlacementPhaseException("Simple placement failed");
        }
        else if (tilePlacementPhase.getTilePlacementType() == TilePlacementType.NUKE) {
            if (nukeTileHelper.attemptNuke(
                    tilePlacementPhase,
                    tileMap,
                    gamePieceMap)) {

                nukeTileHelper.updateTileMapWithNewlyInsertedTile(tilePlacementPhase, tileMap);
                nukeTileHelper.removeNukedVillagersFromGamePieceMap(tilePlacementPhase, gamePieceMap);
                incrementTurnNumber();
            }
            else throw new TilePlacementPhaseException("Nuking failed");
        }
    }

    @Override
    public void doBuildPhase(BuildPhase buildPhase) throws Exception {
        if(buildPhase.getBuildType() == BuildType.FOUND){
            if(buildPhaseHelper.attemptSettlementFoundation(
                    buildPhase,
                    tileMap,
                    gamePieceMap)){

                buildPhaseHelper.insertVillager(buildPhase, gamePieceMap, tileMap);
                updateScoreWhenVillagerPlaced(buildPhase.getPlayerID());
            }
            else throw new BuildPhaseException("Settlement foundation failed");
        }
        else if(buildPhase.getBuildType() == BuildType.EXPAND) {
            if(buildPhaseHelper.attemptSettlementExpansion(
                    buildPhase,
                    tileMap,
                    gamePieceMap)){

                buildPhaseHelper.expandSettlement(buildPhase, tileMap, gamePieceMap);
                updateScoreWhenVillagerPlaced(buildPhase.getPlayerID());
            }
            else throw new BuildPhaseException("Settlement expansion failed");
        }
        else if(buildPhase.getBuildType() == BuildType.PLACE_TOTORO) {
            if(buildPhaseHelper.attemptTotoroPlacement(
                    buildPhase,
                    tileMap,
                    gamePieceMap)){

                buildPhaseHelper.insertSpecialPiece(buildPhase, gamePieceMap);
                updateScoreWhenTigerOrTotoroPlaced(buildPhase.getTypeOfPieceToPlace(), buildPhase.getPlayerID());

            }
            else throw new BuildPhaseException("Totoro placement failed");
        }
        else if(buildPhase.getBuildType() == BuildType.PLACE_TIGER){
            if(buildPhaseHelper.attemptTigerPlacement(
                    buildPhase,
                    tileMap,
                    gamePieceMap)) {

                buildPhaseHelper.insertSpecialPiece(buildPhase, gamePieceMap);
                updateScoreWhenTigerOrTotoroPlaced(buildPhase.getTypeOfPieceToPlace(), buildPhase.getPlayerID());
            }
            else throw new BuildPhaseException("Tiger placement failed");
        }
    }

    private void updateScoreWhenVillagerPlaced(PlayerID playerID){
        if(playerID == PlayerID.PLAYER_ONE){
            this.playerOneScore += buildPhaseHelper.getLastPlayScoreForVillagers();
        }
        else{
            this.playerTwoScore += buildPhaseHelper.getLastPlayScoreForVillagers();
        }
        buildPhaseHelper.setLastPlayVillagerScoreToZero();
    }

    private void updateScoreWhenTigerOrTotoroPlaced(TypeOfPiece type, PlayerID playerID){
        if(playerID == PlayerID.PLAYER_ONE){
            if(type == TypeOfPiece.TIGER){
                this.playerOneScore += tigerScore;
            }
            else{
                this.playerOneScore += totoroScore;
            }
        }
        else{
            if(type == TypeOfPiece.TIGER){
                this.playerTwoScore += tigerScore;
            }
            else{
                this.playerTwoScore += totoroScore;
            }
        }
    }

    private void incrementTurnNumber() {
        this.turnNumber++;
    }

    @Override
    public int getCurrentTurn() {
        return turnNumber;
    }

    @Override
    public Map<Location, Hexagon> getGameBoardHexagons() {
        return new HashMap<Location, Hexagon>(tileMap.getAllHexagons());
    }

    @Override
    public boolean hasTileAt(Location[] locationsInTile) {
        for(int i = 0; i < locationsInTile.length; i++) {
            if(!tileMap.hasHexagonAt(locationsInTile[i]))
                return false;
        }

        return true;
    }
}
