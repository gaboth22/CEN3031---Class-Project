package GameBoardTest;

import GamePieceMap.GamePieceMap;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.Before;
import org.junit.Test;
import GameBoard.TilePlacementHelper;
import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class TilePlacementHelperTest {
    private GamePieceMap gamePieceMap;
    private PlayerID firstPlayer;
    private TilePlacementPhase tilePlacementPhase;
    private Tile tileToPlace;
    private Tile secondTile;
    private TileBuilder tileBuilder;
    private TilePlacementHelper tilePlacementHelper;
    private TileMap tileMap;

    @Before
    public void initializeInstances() {
        gamePieceMap = new GamePieceMap();
        firstPlayer = PlayerID.PLAYER_ONE;
        tileBuilder = new TileBuilder();
        tileToPlace = tileBuilder.getTileAtOrigin();
        tilePlacementPhase = new TilePlacementPhase(firstPlayer, tileToPlace);
        tilePlacementHelper = new TilePlacementHelper();
        tileMap = new HexagonMap();
    }

    @Test
    public void attemptFirstTilePlacementIsNotFirstTurnTest() throws Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException {
        tilePlacementHelper.attemptFirstTilePlacement(tilePlacementPhase, tileMap, 2, 1);
    }

    @Test
    public void attemptFirstTilePlacementIsFirstTurnTest() {
        assertTrue(tilePlacementHelper.attemptFirstTilePlacement(tilePlacementPhase, tileMap, 1, 1));
    }

    @Test
    public void attemptSimpleTilePlacementTest() {
        assertFalse(tilePlacementHelper.attemptSimpleTilePlacement(tilePlacementPhase, tileMap));
    }
}