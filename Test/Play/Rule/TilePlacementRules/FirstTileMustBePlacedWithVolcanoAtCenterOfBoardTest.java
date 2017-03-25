package Play.Rule.TilePlacementRules;

import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import TileBuilder.TileBuilder;
import TileMap.*;
import Tile.Tile.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FirstTileMustBePlacedWithVolcanoAtCenterOfBoardTest {
    private TileMap tileMap = new HexagonMap();
    private TileBuilder builder = new TileBuilder();

    @Before
    public void setUp() throws Exception {
        tileMap = new HexagonMap();
    }

    @After
    public void tearDown() throws Exception {
        tileMap = null;
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void theRuleShouldFailBecauseThereIsATileOnBoardAlreadyAtCenter() throws Exception {
        givenTheGameBoardHasATileAtTheCenter();
        Tile tileToPlace = builder.getTileAtOrigin();
        FirstTileMustBePlacedWithVolcanoAtCenterOfBoard.applyRule(tileMap, tileToPlace);
    }

    private void givenTheGameBoardHasATileAtTheCenter() throws Exception {
        Tile atOrigin = builder.getTileAtOrigin();
        tileMap.insertTile(atOrigin);
    }

    @Test
    public void theRuleShouldPassBecauseThereIsNotATileOnBoardAtCenter() throws Exception {
        Tile atOrigin = builder.getTileAtOrigin();
        FirstTileMustBePlacedWithVolcanoAtCenterOfBoard.applyRule(tileMap, atOrigin);
    }
 }