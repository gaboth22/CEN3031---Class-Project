package Play.Rule.PiecePlacementRules;

import GamePieceMap.TypeOfPiece;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;

public class PlayerMustHavePieceRule
{
    public static void applyRule(Player player, TypeOfPiece pieceToCheck)
            throws InvalidPiecePlacementRuleException {

        if(pieceToCheck == TypeOfPiece.VILLAGER) {
            if(!(player.getVillagerCount() > 0)) {
                String errorMessage = "" + player.getID() + " does not have enough villagers";
                throw new InvalidPiecePlacementRuleException(errorMessage);
            }
        }

        else if(pieceToCheck == TypeOfPiece.TIGER) {
            if(!(player.getTigerCount() > 0)) {
                String errorMessage = "" + player.getID() + " does not have enough tigers";
                throw new InvalidPiecePlacementRuleException(errorMessage);
            }
        }

        else if(pieceToCheck == TypeOfPiece.TOTORO) {
            if (!(player.getTotoroCount() > 0)) {
                String errorMessage = "" + player.getID() + " does not have enough totoro";
                throw new InvalidPiecePlacementRuleException(errorMessage);
            }
        }
    }
}
