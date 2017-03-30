package Steve;

import GameBoard.GameBoardState;
import Player.PlayerID;

public interface PlayGenerator {
    Object generateEducatedPlay(GameBoardState gameBoardState, PlayerID activePlayer);
}
