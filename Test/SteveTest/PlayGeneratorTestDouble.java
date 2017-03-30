package SteveTest;

import GameBoard.*;
import Player.PlayerID;
import Steve.PlayGenerator;

public class PlayGeneratorTestDouble implements PlayGenerator {

    private Object play;
    private boolean wasPlayRequested;

    public PlayGeneratorTestDouble() {
        wasPlayRequested = false;
    }

    public void setPlayToReturn(Object play) {
        this.play = play;
    }

    public boolean wasPlayRequested() {
        return wasPlayRequested;
    }


    public Object generateEducatedPlay(GameBoardState state, PlayerID playingAs) {
        wasPlayRequested = true;
        return play;
    }
}
