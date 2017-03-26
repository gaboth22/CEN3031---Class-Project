package Play.Rule.PiecePlacementRules;

import GamePieceMap.*;
import Location.Location;
import Movement.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.PlayerID;
import org.junit.Before;
import org.junit.Test;

public class SettlementSizeMustBeFiveOrGreaterRuleTest {
    private GamePieceMap pieceMap;
    private Location origin;
    private Movement movement;
    private PlayerID activePlayer;

    @Before
    public void initializeInstances() {
        pieceMap = new GamePieceMap();
        origin = new Location(0, 0);
        movement = new AxialMovement();
        activePlayer = PlayerID.PLAYER_TWO;
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldFailSinceSettlementSizeIsFour() throws Exception {
        givenIHaveASettlementOfSize(4);
        whenICheckTheRule();
    }

    private void givenIHaveASettlementOfSize(int size) throws Exception {
        GamePiece[] pieces = new GamePiece[size];
        Location[] locations = new Location[size];

        for(int i = 0; i < pieces.length; i++) {
            pieces[i] = new GamePiece(activePlayer, TypeOfPiece.VILLAGER);
        }

        locations[0] = origin;
        for(int i = 1; i < locations.length; i++) {
            locations[i] = movement.up(locations[i-1]);
        }

        for(int i = 0; i < pieces.length; i++) {
            pieceMap.insertAPieceAt(locations[i], pieces[i]);
        }
    }

    private void whenICheckTheRule() throws InvalidPiecePlacementRuleException {
        SettlementSizeMustBeFiveOrGreaterRule.applyRule(pieceMap, movement.down(origin), activePlayer);
    }

    @Test
    public void theRuleShouldPassSinceTheSettlementIsSizeFive() throws Exception {
        givenIHaveASettlementOfSize(5);
        whenICheckTheRule();
    }

}
