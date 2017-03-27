package GameBoard;

import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import GamePieceMap.GamePieceMap;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
import TileMap.*;
import java.util.Map;

public class GameBoardImpl implements GameBoard {
    private GamePieceMap gamePieceMap;
    private TileMap tileMap;
    private int turnNumber;
    private final int FIRST_TURN = 1;

    private TilePlacementHelper tilePlacementHelper;
    private NukeTileHelper nukeTileHelper;
    private BuildPhaseHelper buildPhaseHelper;

    public GameBoardImpl() {
        this.gamePieceMap = new GamePieceMap();
        this.tileMap = new HexagonMap();
        this.turnNumber = FIRST_TURN;
        this.tilePlacementHelper = new TilePlacementHelper();
        this.nukeTileHelper = new NukeTileHelper();
        this.buildPhaseHelper = new BuildPhaseHelper();
    }

    @Override
    public void doTilePlacementPhase(TilePlacementPhase tilePlacementPhase) throws Exception {
        if(tilePlacementHelper.attemptFirstTilePlacementOrSimpleTilePlacement(tilePlacementPhase, tileMap, turnNumber, FIRST_TURN)) {
            tilePlacementHelper.insertTile(tilePlacementPhase, tileMap);
            incrementTurnNumber();
            return;
        }

        else if(nukeTileHelper.attemptNuke(tilePlacementPhase, tileMap)) {
            nukeTileHelper.removeNukedVillagersFromGamePieceMap();
            nukeTileHelper.updateTileMapWithNewlyInsertedTile();
            return;
        }

        throw new TilePlacementPhaseException();
    }

    private void incrementTurnNumber() { this.turnNumber++; }

    @Override
    public int getCurrentTurn() {
        return turnNumber;
    }

    @Override
    public Map<Location, Hexagon> getGameBoardHexagons() {
        return tileMap.getAllHexagons();
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
        }

        else if(buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TIGER ||
                buildPhase.getTypeOfPieceToPlace() == TypeOfPiece.TOTORO) {
            buildPhaseHelper.typeOfPieceToPlaceIsTigerOrTotoro(buildPhase, tileMap, gamePieceMap);
        }
    }
}
