package Steve.PlayGeneration.SmartTilePlacer;

import GameBoard.GameBoardState;
import Player.*;

public class OppositePlayerGetter {
    public Player getOtherPlayerFromGameBoardState(PlayerID activePlayer,
                                              GameBoardState state) {

        if(activePlayer == PlayerID.PLAYER_ONE)
            return state.getPlayerTwo();
        else
            return state.getPlayerOne();
    }
}
