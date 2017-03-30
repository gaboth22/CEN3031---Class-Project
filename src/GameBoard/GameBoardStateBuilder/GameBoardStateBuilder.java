package GameBoard.GameBoardStateBuilder;

import GameBoard.GameBoard;
import GameBoard.GameBoardState;

public interface GameBoardStateBuilder {
    GameBoardState buildGameBoardState(GameBoard board);
}
