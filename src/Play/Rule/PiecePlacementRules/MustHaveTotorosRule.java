package Play.Rule.PiecePlacementRules;

import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;

public class MustHaveTotorosRule
{
    public static void applyRule(Player currentPlayer)
            throws InvalidPiecePlacementRuleException {
        int totorocount = currentPlayer.getTotoros();
        if (totorocount > 0)
        {
            return;
        }
        else
        {
            String errorMessage = "Active player does not have a Totoro for placement";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }

    }
}
