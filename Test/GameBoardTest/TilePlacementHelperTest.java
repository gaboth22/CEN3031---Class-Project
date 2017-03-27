package GameBoardTest;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import GameBoard.TilePlacementHelper;
import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class TilePlacementHelperTest {
    private PlayerID firstPlayer;
    private TilePlacementPhase tilePlacementPhase;
    private Tile tileToPlace;
    private TileBuilder tileBuilder;
    private TilePlacementHelper tilePlacementHelper;
    private TileMap tileMap;

    @Before
    public void initializeInstances() {
        firstPlayer = PlayerID.PLAYER_ONE;
        tileBuilder = new TileBuilder();
        tileToPlace = tileBuilder.getTileAtOrigin();
        tilePlacementPhase = new TilePlacementPhase(firstPlayer, tileToPlace);
        tilePlacementHelper = new TilePlacementHelper();
        tileMap = new HexagonMap();
    }

    @After
    public void cleanUp() {
        firstPlayer = null;
        tileBuilder = null;
        tileToPlace = null;
        tilePlacementPhase = null;
        tilePlacementHelper = null;
        tileMap = null;
    }

    @Test
    public void attemptFirstTilePlacementIsNotFirstTurnTest() throws InvalidTilePlacementRuleException {
        tilePlacementHelper.attemptFirstTilePlacement(
                tilePlacementPhase,
                tileMap,
                2,
                1);
    }

    @Test
    public void firstTilePlaceShouldNotBePerformedSinceNotFirstTurn() throws InvalidTilePlacementRuleException {
        assertFalse(tilePlacementHelper.attemptFirstTilePlacement(
                tilePlacementPhase,
                tileMap,
                2,
                1));
    }

    @Test
    public void firstTilePlaceShouldSucceedSinceFirstTurn() throws InvalidTilePlacementRuleException {
        assertTrue(tilePlacementHelper.attemptFirstTilePlacement(
                tilePlacementPhase,
                tileMap,
                1,
                1));
    }

    @Test
    public void simplePlacementShouldSucceedSincePlacementAdjacentToOtherTile() throws Exception{
        Tile atOrigin = tileBuilder.getTileAtOrigin();
        Tile adjacent = tileBuilder.getAdjacentTile(atOrigin);
        tilePlacementPhase = new TilePlacementPhase(firstPlayer, adjacent);
        givenThereIsATileAtTheOrigin();
        Assert.assertTrue(tilePlacementHelper.attemptSimpleTilePlacement(tilePlacementPhase, tileMap));
    }

    @Test
    public void simpleTilePlacementShoulFailSincePlacingOnTopOfOtherTile() throws Exception {
        givenThereIsATileAtTheOrigin();
        assertFalse(tilePlacementHelper.attemptSimpleTilePlacement(tilePlacementPhase, tileMap));
    }

    private void givenThereIsATileAtTheOrigin() throws Exception {
        Tile atOrigin = tileBuilder.getTileAtOrigin();
        tileMap.insertTile(atOrigin);
    }

}