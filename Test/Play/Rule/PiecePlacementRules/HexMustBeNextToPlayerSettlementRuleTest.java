package Play.Rule.PiecePlacementRules;

import GamePieceMap.*;
import Location.Location;
import Movement.*;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;
import TestExceptions.MethodCalledException;
import org.junit.Before;
import org.junit.Test;

public class HexMustBeNextToPlayerSettlementRuleTest {
    private GamePieceMap pieceMap;
    private PlayerID actingPlayer;
    private PlayerID otherPlayer;
    private Movement movement;

    @Before
    public void initializeInstances() {
        actingPlayer = PlayerID.PLAYER_ONE;
        otherPlayer = PlayerID.PLAYER_TWO;
        pieceMap = new GamePieceMap();
        movement = new AxialMovement();
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldNotPassSinceThereAreNoSettlementsAtAll() throws InvalidPiecePlacementRuleException {
        givenThereAreNoPiecesInThePieceMap();
        Location location = new Location(1, -1);
        whenICheckForSettlementsAdjacentTo(location);
    }

    private void givenThereAreNoPiecesInThePieceMap() {
        pieceMap = new GamePieceMap();
    }

    private void whenICheckForSettlementsAdjacentTo(Location loc) throws InvalidPiecePlacementRuleException{
        HexMustBeNextToPlayerSettlementRule.applyRule(pieceMap, loc, actingPlayer);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldNotPassSinceAdjacentSettlementsBelongToAnotherPlayer() throws Exception {
        Location origin = new Location(0, 0);
        givenAllPiecesBelongToTheOtherPlayerAndPiecesAreAllAround(origin);
        whenICheckForSettlementsAdjacentTo(origin);
    }

    private void givenAllPiecesBelongToTheOtherPlayerAndPiecesAreAllAround(Location loc)
            throws LocationNotEmptyException {

        Location[] adjLocations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(loc);
        GamePiece gamePiece = new GamePiece(otherPlayer, TypeOfPiece.VILLAGER);
        for(int i = 0; i < adjLocations.length; i++) {
            pieceMap.insertAPieceAt(adjLocations[i], gamePiece);
        }
    }

    @Test(expected = MethodCalledException.class)
    public void ruleShouldShouldPassSinceThereIsOneAdjacentSettlementBelongingToTheActingPlayer()
            throws Exception {

        Location origin = new Location(0, 0);
        givenThereIsOneSettlementBelongingToTheActingPlayerAdjacentTo(origin);
        thenIShouldMakeItDownHereSinceTheRulePassed();
    }

    private void givenThereIsOneSettlementBelongingToTheActingPlayerAdjacentTo(Location loc)
            throws LocationNotEmptyException {
        pieceMap.insertAPieceAt(movement.up(loc), new GamePiece(actingPlayer, TypeOfPiece.VILLAGER));
    }

    private void thenIShouldMakeItDownHereSinceTheRulePassed() throws MethodCalledException {
        throw new MethodCalledException();
    }
}
