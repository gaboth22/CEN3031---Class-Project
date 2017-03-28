package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import GamePieceMap.GamePieceMap;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
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

        if(tilePlacementHelper.attemptFirstTilePlacementOrSimpleTilePlacement(
            tilePlacementPhase,
            tileMap,
            turnNumber,
            FIRST_TURN)) {

            tilePlacementHelper.insertTile(tilePlacementPhase, tileMap);
            incrementTurnNumber();
        }

        else if(nukeTileHelper.attemptNuke(tilePlacementPhase, tileMap, gamePieceMap)) {

            nukeTileHelper.removeNukedVillagersFromGamePieceMap(tilePlacementPhase, gamePieceMap);
            nukeTileHelper.updateTileMapWithNewlyInsertedTile(tilePlacementPhase, tileMap);
        }

        else {
            throw new TilePlacementPhaseException();
        }
    }

    private void incrementTurnNumber() {
        this.turnNumber++;
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

    @Override
    public void doBuildPhase(BuildPhase buildPhase) throws Exception {

        if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.VILLAGER) {
            buildPhaseHelper.typeOfPieceToPlaceIsVillager(buildPhase, tileMap, gamePieceMap);
            updateScoreWhenVillagerPlaced(buildPhase.getPlayerID());
        }

        else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER ||
                buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
            buildPhaseHelper.typeOfPieceToPlaceIsTigerOrTotoro(buildPhase, tileMap, gamePieceMap);
            updateScoreWhenTigerOrTotoroPlaced(buildPhase.getTypeOfPieceToPlace(), buildPhase.getPlayerID());
        }
    }
}
