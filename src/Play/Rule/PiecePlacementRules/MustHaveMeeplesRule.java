package Play.Rule.PiecePlacementRules;

import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;

public class MustHaveMeeplesRule
{
    public static void applyRule(Player currentPlayer)
            throws InvalidPiecePlacementRuleException {
        int meeplecount = currentPlayer.getMeeples();
        if (meeplecount > 0)
        {
            return;
        }
        else
        {
            String errorMessage = "Active player does not have enough meeples for placement";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }

    }
}
