package Play.Rule.PiecePlacementRules;

import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;

public class MustHaveTigersRule
{
    public static void applyRule(Player currentPlayer)
            throws InvalidPiecePlacementRuleException {
        int tigercount = currentPlayer.getTigers();
        if (tigercount > 0)
        {
            return;
        }
        else
        {
            String errorMessage = "Active player does not have a tiger for placement";
            throw new InvalidPiecePlacementRuleException(errorMessage);
        }

    }
}