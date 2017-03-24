package Play.Rule.TilePlacementRules;


import GamePieceMap.*;
import Movement.AxialMovement;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import TileMap.*;
import TileBuilder.*;
import Tile.Tile.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CannotWipeOutSettlementRuleTest {
    private TileBuilder tileBuilder;
    private GamePieceMap gamePieceMap;
    private TileMap tileMapForNoSettlementsCase;
    private TileMap tileMapForSettlementsCase;
    private AxialMovement movementGenerator;
    private Tile origin;
    private Tile placeOriginOnOrigin;
    private Tile tileToFail;
    private Tile tileToPass;

    @Before
    public void setUp() throws Exception {
        tileBuilder = new TileBuilder();
        gamePieceMap = new GamePieceMap();
        tileMapForNoSettlementsCase = new HexagonMap();
        tileMapForSettlementsCase = new HexagonMap();
        movementGenerator = new AxialMovement();
        origin = tileBuilder.getTileAtOrigin();
        tileMapForNoSettlementsCase.insertTile(origin);
       // tileToPlace = tileBuilder.getTileAtOrigin();
       ///  tileToPass
    }

    @Test
    public void applyRuleNoSettlementsBelowTest() throws Exception{
        CannotWipeOutSettlementRule.applyRule(tileMapForNoSettlementsCase, gamePieceMap, origin);
    }

    @Test
    public void applyRulePassTest() throws Exception {
        CannotWipeOutSettlementRule.applyRule(tileMapForSettlementsCase, gamePieceMap, tileToPass);
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void applyRuleThrowsTest() throws Exception {
        CannotWipeOutSettlementRule.applyRule(tileMapForSettlementsCase, gamePieceMap, tileToFail);
    }

    @After
    public void tearDown() throws Exception {
        tileBuilder = null;
        gamePieceMap = null;
        tileMapForNoSettlementsCase = null;
        tileMapForSettlementsCase = null;
        movementGenerator = null;
    }

}