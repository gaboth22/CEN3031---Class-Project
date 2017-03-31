package GameBoard;

import Sender.SenderData.SenderData;

public class GameBoardStateSenderData implements SenderData<GameBoardState> {
    private GameBoardState gameBoardState;

    public GameBoardStateSenderData(GameBoardState gameBoardState) {
        this.gameBoardState = gameBoardState;
    }

    public GameBoardState getData() {
       return gameBoardState;
    }
}
