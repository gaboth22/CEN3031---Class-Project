package BuildPhase;

import GameBoard.GameBoard;

public abstract class BuildPhase {
    private GameBoard gameBoard;

    abstract void build();

    void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}