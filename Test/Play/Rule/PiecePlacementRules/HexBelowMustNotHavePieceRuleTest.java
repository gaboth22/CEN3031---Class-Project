package Play.Rule.PiecePlacementRules;

import GamePieceMap.*;
import Location.Location;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;
import TestExceptions.MethodCalledException;
import org.junit.Before;
import org.junit.Test;

public class HexBelowMustNotHavePieceRuleTest {
    private GamePieceMap pieceMap;
    private GamePiece piece;

    @Before
    public void initializeInstances() {
        pieceMap = new GamePieceMap();
        piece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void theRuleShouldFailSinceThereIsAPieceAlreadyAtThatLocation() throws Exception {
        Location loc = new Location(0, 0);
        givenIHaveAPieceAtLocation(loc);
        whenITryToPutAPieceAtTheSameLocation(loc);
    }

    private void givenIHaveAPieceAtLocation(Location loc) throws LocationNotEmptyException {
        pieceMap.insertAPieceAt(loc, piece);
    }

    private void whenITryToPutAPieceAtTheSameLocation(Location sameLocation) throws InvalidPiecePlacementRuleException {
        HexBelowMustNotHavePieceRule.applyRule(pieceMap, sameLocation);
    }

    @Test(expected = MethodCalledException.class)
    public void theRuleShouldPassSinceTheHexAtThatLocationIsNotOccupied() throws Exception {
        givenIHaveAPieceAtLocation(new Location(0, 0));
        whenITryToPutAPieceAtLocation(new Location( 0, 1));
        thenIShouldMakeDownHereBecauseTheRulePassed();
    }

    private void whenITryToPutAPieceAtLocation(Location loc) throws InvalidPiecePlacementRuleException {
        HexBelowMustNotHavePieceRule.applyRule(pieceMap, loc);
    }

    private void thenIShouldMakeDownHereBecauseTheRulePassed() throws MethodCalledException {
        throw new MethodCalledException();
    }

}
