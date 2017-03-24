package Play.Rule.PiecePlacementRules;

import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;
import Player.PlayerID;
import org.junit.Before;
import org.junit.Test;

public class MustHaveTotorosRuleTest {
    private Player testPlayerSuccess;
    private PlayerID  PIDSuccess;
    private Player testPlayerFailure;
    private PlayerID PIDFailure;

    @Before
    public void initializeInstances(){
        PIDSuccess = PlayerID.PLAYER_ONE;
        PIDFailure = PlayerID.PLAYER_TWO;
        testPlayerSuccess = new Player(PIDSuccess);
        testPlayerFailure = new Player(PIDFailure);
        for(int i = 0; i < 3; i++)
        {
            testPlayerFailure.decrimentTotoros();
        }
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void testPlayerFailureShouldFail() throws InvalidPiecePlacementRuleException{
        MustHaveTotorosRule.applyRule(testPlayerFailure);
    }

    @Test
    public void testPlayerSuccessShouldPass() throws InvalidPiecePlacementRuleException{
        MustHaveTotorosRule.applyRule(testPlayerSuccess);
    }
}
