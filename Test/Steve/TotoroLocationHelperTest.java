package Steve;

import GameBoard.GameBoardImpl;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Steve.PlayGeneration.TotoroLocationHelper;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import Location.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TotoroLocationHelperTest {
    private GameBoardImpl gameBoard;
    private TileBuilder tileBuilder;
    private BuildPhase buildPhase;
    private PlayerID firstPlayer;

    @Before
    public void initializeInstances() {
        gameBoard = new GameBoardImpl();
        tileBuilder = new TileBuilder();
        firstPlayer = PlayerID.PLAYER_ONE;
    }

    @Test
    public void shouldReturnOnlyLocationToPlaceTotoro() throws Exception {
        createSettlementOfSizeFive();

        buildPhase = TotoroLocationHelper.pickTotoroLocation(gameBoard.getPlacedHexagons(),
                                                            gameBoard.getPlayerOneSettlements(),
                                                            gameBoard.getGamePieceMap(),
                                                            firstPlayer);

        assertEquals(buildPhase.getLocationToPlacePieceOn(), new Location(-1, 1));
    }

    @Test
    public void shouldReturnNullBecauseTotoroCantBePlacedDueToSettlementSizeOne() throws Exception {
        Location settlement1 = new Location(1, 0);
        findSettlementAtLocation(settlement1);

        buildPhase = TotoroLocationHelper.pickTotoroLocation(gameBoard.getPlacedHexagons(),
                                                            gameBoard.getPlayerOneSettlements(),
                                                            gameBoard.getGamePieceMap(),
                                                            firstPlayer);

        assertTrue(buildPhase == null);
    }

    private void createSettlementOfSizeFive() throws Exception {
        createAndPlaceTileForSettlementOfSizeFive();

        Location settlement1 = new Location(1, 0);
        Location settlement2 = new Location(1, -1);
        Location settlement3 = new Location(0, -1);
        Location settlement4 = new Location(-1, -1);
        Location settlement5 = new Location(-1, 0);

        findSettlementAtLocation(settlement1);
        findSettlementAtLocation(settlement2);
        findSettlementAtLocation(settlement3);
        findSettlementAtLocation(settlement4);
        findSettlementAtLocation(settlement5);
    }

    private void createAndPlaceTileForSettlementOfSizeFive() throws Exception {
        Location tile11 = new Location(0, -2);
        Location tile12 = new Location(0, -1);
        Location tile13 = new Location(-1, -1);

        placeTileAtLocation(tile11, tile12, tile13);
    }

    private void placeTileAtLocation(Location l1, Location l2, Location l3) throws Exception {
        Tile adjacentTile = tileBuilder.getTileWithLocations(l1, l2, l3);
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, adjacentTile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void findSettlementAtLocation(Location location) throws Exception {
        GamePiece standardVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);

        buildPhase = new BuildPhase(standardVillage, location);
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);
    }
}