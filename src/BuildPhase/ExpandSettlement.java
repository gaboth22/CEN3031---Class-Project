package BuildPhase;

import GameBoard.*;
import Player.PlayerID;

public class ExpandSettlement extends BuildPhase {
    private Settlement settlementToExpand;
    private PlayerID ownerId;
    private GameBoard gameBoard;

    public ExpandSettlement(Settlement settlementToExpand, PlayerID ownerId) {
        this.settlementToExpand = settlementToExpand;
        this.ownerId = ownerId;
    }

    public void build() {
        gameBoard.appendMeeplesToSettlement(this.settlementToExpand, this.ownerId);
    }
}