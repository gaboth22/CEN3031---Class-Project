package Play.Rule.PiecePlacementRules;

import GamePieceMap.*;
import Location.Location;
import Movement.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;
import org.junit.Before;
import org.junit.Test;

public class SettlementMustNotAlreadyHaveSpecialPieceRuleTest {
    private GamePieceMap pieceMap;
    private Location origin;
    private Movement movement;
    private PlayerID activePlayer;

    @Before
    public void initializeInstances() {
        pieceMap = new GamePieceMap();
        origin = new Location(0, 0);
        movement = new AxialMovement();
        activePlayer = PlayerID.PLAYER_ONE;
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldFailSinceThereIsAlreadyATigerPlaygroundInSettlement() throws Exception {
        givenIHaveASettlementWithATigerPlayground();
        whenICheckTheRuleWithPiece(TypeOfPiece.TIGER);
    }

    private void givenIHaveASettlementWithATigerPlayground() throws Exception {
        GamePiece villager = new GamePiece(activePlayer, TypeOfPiece.VILLAGER);
        GamePiece tiger = new GamePiece(activePlayer, TypeOfPiece.TIGER);
        pieceMap.insertAPieceAt(origin, villager);
        pieceMap.insertAPieceAt(movement.up(origin), tiger);
    }

    private void whenICheckTheRuleWithPiece(TypeOfPiece piece) throws InvalidPiecePlacementRuleException {
        SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(pieceMap,
                                                        movement.down(origin),
                                                        activePlayer,
                                                        piece);
    }

    @Test
    public void ruleShouldPassSinceSettlementHasNoTigerPlayground() throws Exception {
        givenIHaveASettlementOfSizeOne();
        whenICheckTheRuleWithPiece(TypeOfPiece.TIGER);
    }

    private void givenIHaveASettlementOfSizeOne() throws Exception {
        GamePiece villager = new GamePiece(activePlayer, TypeOfPiece.VILLAGER);
        pieceMap.insertAPieceAt(origin, villager);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldFailSinceSettlementAlreadyHasTotoro() throws Exception {
        givenIHaveASettlementWithATotoroSanctuary();
        whenICheckTheRuleWithPiece(TypeOfPiece.TOTORO);
    }

    private void givenIHaveASettlementWithATotoroSanctuary() throws Exception {
        GamePiece villager = new GamePiece(activePlayer, TypeOfPiece.VILLAGER);
        GamePiece totoro = new GamePiece(activePlayer, TypeOfPiece.TOTORO);
        pieceMap.insertAPieceAt(origin, villager);
        pieceMap.insertAPieceAt(movement.up(origin), totoro);
    }

    @Test
    public void ruleShouldPassSinceSettlementHasNoTotoro() throws Exception {
        givenIHaveASettlementOfSizeOne();
        whenICheckTheRuleWithPiece(TypeOfPiece.TOTORO);
    }

    @Test
    public void ruleShouldPassWhenThereIsAnAdjacentSettlementThatDoesNotHaveSpecialPiece() throws Exception {
        givenIHaveASettlementWithATotoroSanctuary();
        givenIHaveAnotherSettlementOnePositionAwayFromIt();
        whenICheckTheRuleWithPieceAtLocation(TypeOfPiece.TOTORO, movement.upRight(origin));
    }

    private void givenIHaveAnotherSettlementOnePositionAwayFromIt() throws Exception {
        GamePiece villager = new GamePiece(activePlayer, TypeOfPiece.VILLAGER);
        Location locToSettleOn = movement.upRight(origin);
        locToSettleOn = movement.downRight(locToSettleOn);

        pieceMap.insertAPieceAt(locToSettleOn, villager);
    }

    private void whenICheckTheRuleWithPieceAtLocation(TypeOfPiece piece, Location loc) throws Exception {
        SettlementMustNotAlreadyHaveSpecialPieceRule.applyRule(pieceMap,
                loc,
                activePlayer,
                piece);
    }
}
