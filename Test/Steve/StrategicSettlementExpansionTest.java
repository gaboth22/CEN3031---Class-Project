package Steve;

import GameBoard.GameBoardImpl;
import GameBoard.GameBoardState;
import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import GamePieceMap.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.PlayGeneration.StrategicSettlementExpansion;
import static org.junit.Assert.assertEquals;

import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.Hexagon;
import TileMap.TileMap;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;

public class StrategicSettlementExpansionTest {
    private GameBoardImpl gameBoard;
    private GameBoardState gameBoardState;
    private BuildPhase buildPhase;
    private TileBuilder tileBuilder;

    @Before
    public void initializeInstances() {
        gameBoard = new GameBoardImpl();
        setUpGameBoard();
        tileBuilder = new TileBuilder();
    }

    @Test
    public void shouldReturnLocationAdjacentToOnlySettlement() throws Exception {

        Location locationToPlaceSettlement = new Location(1, 0);
        buildPhase = doBuildPhaseAtLocation(locationToPlaceSettlement);

        setUpGameBoard();
        buildPhase = StrategicSettlementExpansion.buildAdjacentToLargestSettlement(gameBoardState, gameBoardState.getPlayerOne());

        Location expansionLocation = new Location(1, -1);
        assertEquals(expansionLocation, buildPhase.getLocationToPlacePieceOn());
    }

    @Test
    public void shouldReturnLocationAdjacentToLargestSettlement() throws Exception {
        Location firstSettlement = new Location(1, 0);
        buildPhase = doBuildPhaseAtLocation(firstSettlement);

        Location largerSettlement1 = new Location(-1, 1);
        Location largerSettlement2 = new Location(-1, 0);
        buildPhase = doBuildPhaseAtLocation(largerSettlement1);
        buildPhase = doBuildPhaseAtLocation(largerSettlement2);

        setUpGameBoard();

        Location tile11 = new Location(0, 2);
        Location tile12 = new Location(-1, 2);
        Location tile13 = new Location(0, 1);

        Tile adjacentTile = tileBuilder.getTileWithLocations(tile11, tile12, tile13);
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, adjacentTile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);

        setUpGameBoard();

        buildPhase = StrategicSettlementExpansion.buildAdjacentToLargestSettlement(gameBoardState, gameBoardState.getPlayerOne());

        Location locationAdjacentToLargestSettlement = new Location(-1, 2);
        assertEquals(locationAdjacentToLargestSettlement, buildPhase.getLocationToPlacePieceOn());
    }

    private void setUpGameBoard() {
        TileMap tileMap = ((GameBoardImpl) gameBoard).getTileMap();
        gameBoardState = new GameBoardState(gameBoard.getPlayer(PlayerID.PLAYER_ONE),
                gameBoard.getPlayer(PlayerID.PLAYER_TWO),
                1,
                gameBoard.getPlacedHexagons(),
                gameBoard.getGamePieceMap(),
                gameBoard.getPlaceableLocations(),
                gameBoard.getNukeableVolcanoLocations(),
                tileMap);
    }

    private BuildPhase doBuildPhaseAtLocation(Location location) throws Exception {
        GamePiece standardVillage = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase;
        buildPhase = new BuildPhase(standardVillage, location);
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);

        return buildPhase;
    }
}
