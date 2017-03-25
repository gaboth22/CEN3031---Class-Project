package Play.Rule.TilePlacementRules;

import GamePieceMap.*;
import Movement.AxialMovement;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Player.PlayerID;
import TileMap.*;
import TileBuilder.*;
import Tile.Tile.*;
import Location.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CannotWipeOutSettlementRuleTest {
    private TileBuilder tileBuilder;
    private GamePieceMap gamePieceMapForNoSettlementsCase;
    private GamePieceMap gamePieceMapForOneSettlementPassCase;
    private GamePieceMap gamePieceMapForOneSettlementFailCase;
    private GamePieceMap gamePieceMapForDifferentPlayerSettlementsPassCase;
    private GamePieceMap gamePieceMapForDifferentPlayerSettlementsFailCase;
    private GamePieceMap gamePieceMapForSamePlayerSettlementsPassCase;
    private GamePieceMap gamePieceMapForSamePlayerSettlementsFailCase;
    private AxialMovement movementGenerator;
    private Location origin;
    private Tile tileToPlace;
    private GamePiece gamePiece1;
    private GamePiece gamePiece2;

    @Before
    public void setUp() throws Exception {
        movementGenerator = new AxialMovement();
        tileBuilder = new TileBuilder();
        origin = new Location(0,0);
        tileToPlace = tileBuilder.getTileWithLocations(
                                                origin,
                                                movementGenerator.upRight(origin),
                                                movementGenerator.downRight(origin));

        gamePiece1 = new GamePiece(PlayerID.PLAYER_ONE,TypeOfPiece.VILLAGER);
        gamePiece2 = new GamePiece(PlayerID.PLAYER_TWO,TypeOfPiece.VILLAGER);

        setUpNoSettlementsCase();
        setUpOneSettlementPassCase();
        setUpOneSettlementFailCase();
        setUpDifferentPlayerSettlementsPassCase();
        setUpDifferentPlayerSettlementsFailCase();
        setUpSamePlayerSettlementsPassCase();
        setUpSamePlayerSettlementsFailCase();
    }

    private void setUpNoSettlementsCase() throws Exception{
        gamePieceMapForNoSettlementsCase = new GamePieceMap();
    }

    private void setUpOneSettlementPassCase() throws Exception{
        gamePieceMapForOneSettlementPassCase = new GamePieceMap();
        gamePieceMapForOneSettlementPassCase.insertAPieceAt(movementGenerator.upRight(origin), gamePiece1);
        gamePieceMapForOneSettlementPassCase.insertAPieceAt(movementGenerator.up(origin), gamePiece1);
    }

    private void setUpOneSettlementFailCase() throws Exception{
        gamePieceMapForOneSettlementFailCase = new GamePieceMap();
        gamePieceMapForOneSettlementFailCase.insertAPieceAt(movementGenerator.upRight(origin), gamePiece1);
        gamePieceMapForOneSettlementFailCase.insertAPieceAt(movementGenerator.up(origin), gamePiece2);
    }

    private void setUpDifferentPlayerSettlementsPassCase() throws Exception {
        gamePieceMapForDifferentPlayerSettlementsPassCase = new GamePieceMap();
        gamePieceMapForDifferentPlayerSettlementsPassCase.insertAPieceAt(movementGenerator.upRight(origin), gamePiece1);
        gamePieceMapForDifferentPlayerSettlementsPassCase.insertAPieceAt(movementGenerator.up(origin), gamePiece1);
        gamePieceMapForDifferentPlayerSettlementsPassCase.insertAPieceAt(movementGenerator.downRight(origin), gamePiece2);
        gamePieceMapForDifferentPlayerSettlementsPassCase.insertAPieceAt(movementGenerator.down(origin), gamePiece2);
    }

    private void setUpDifferentPlayerSettlementsFailCase() throws Exception{
        gamePieceMapForDifferentPlayerSettlementsFailCase = new GamePieceMap();
        gamePieceMapForDifferentPlayerSettlementsFailCase.insertAPieceAt(movementGenerator.upRight(origin), gamePiece1);
        gamePieceMapForDifferentPlayerSettlementsFailCase.insertAPieceAt(movementGenerator.downRight(origin), gamePiece2);
    }

    private void setUpSamePlayerSettlementsPassCase() throws Exception{
        gamePieceMapForSamePlayerSettlementsPassCase = new GamePieceMap();
        gamePieceMapForSamePlayerSettlementsPassCase.insertAPieceAt(movementGenerator.upRight(origin), gamePiece1);
        gamePieceMapForSamePlayerSettlementsPassCase.insertAPieceAt(movementGenerator.up(origin), gamePiece1);
        gamePieceMapForSamePlayerSettlementsPassCase.insertAPieceAt(movementGenerator.downRight(origin), gamePiece1);
    }


    private void setUpSamePlayerSettlementsFailCase() throws Exception{
        gamePieceMapForSamePlayerSettlementsFailCase = new GamePieceMap();
        gamePieceMapForSamePlayerSettlementsFailCase.insertAPieceAt(movementGenerator.up(origin), gamePiece2);
        gamePieceMapForSamePlayerSettlementsFailCase.insertAPieceAt(movementGenerator.upRight(origin), gamePiece1);
        gamePieceMapForSamePlayerSettlementsFailCase.insertAPieceAt(movementGenerator.downRight(origin), gamePiece1);
    }


    @Test
    public void applyRuleNoSettlementsBelowTest() throws Exception{
        CannotWipeOutSettlementRule.applyRule(gamePieceMapForNoSettlementsCase, tileToPlace);
    }

    @Test
    public void applyRuleOneSettlementBelowPassTest() throws Exception{
        CannotWipeOutSettlementRule.applyRule(gamePieceMapForOneSettlementPassCase, tileToPlace);
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void applyRuleOneSettlementBelowFailTest() throws Exception{
        CannotWipeOutSettlementRule.applyRule(gamePieceMapForOneSettlementFailCase, tileToPlace);
    }

    @Test
    public void applyRuleDifferentPlayerSettlementsBelowPassTest() throws Exception {
        CannotWipeOutSettlementRule.applyRule(gamePieceMapForDifferentPlayerSettlementsPassCase, tileToPlace);
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void applyRuleDifferentPlayerSettlementsBelowFailTest() throws Exception {
        CannotWipeOutSettlementRule.applyRule(gamePieceMapForDifferentPlayerSettlementsFailCase, tileToPlace);
    }

    @Test
    public void applyRuleSamePlayerSettlementsBelowPassTest() throws Exception {
        CannotWipeOutSettlementRule.applyRule(gamePieceMapForSamePlayerSettlementsPassCase, tileToPlace);
    }

    @Test(expected = InvalidTilePlacementRuleException.class)
    public void applyRuleSamePlayerSettlementsBelowFailTest() throws Exception {
        CannotWipeOutSettlementRule.applyRule(gamePieceMapForSamePlayerSettlementsFailCase, tileToPlace);
    }

    @After
    public void tearDown() throws Exception {
        gamePieceMapForSamePlayerSettlementsFailCase = null;
        gamePieceMapForSamePlayerSettlementsPassCase = null;
        gamePieceMapForDifferentPlayerSettlementsFailCase = null;
        gamePieceMapForDifferentPlayerSettlementsPassCase = null;
        movementGenerator = null;
    }

}