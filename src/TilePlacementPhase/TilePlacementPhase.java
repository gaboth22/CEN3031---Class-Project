package TilePlacementPhase;

import GameBoard.GameBoard;

public abstract class TilePlacementPhase {
    private GameBoard gameBoard;

    abstract void place();

    void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
