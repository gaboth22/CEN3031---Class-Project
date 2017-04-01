package SteveTest;

import GameBoard.*;
import Player.PlayerID;
import Steve.PlayGenerator;
import Steve.StevePlayType;

public class PlayGeneratorTestDouble implements PlayGenerator {

    private Object play;
    private boolean wasPlayRequested;
    private StevePlayType requestedPlayType;

    public PlayGeneratorTestDouble() {
        wasPlayRequested = false;
    }

    public void setPlayToReturn(Object play) {
        this.play = play;
    }

    public boolean wasPlayRequested() {
        return wasPlayRequested;
    }

    public StevePlayType getRequestedPlayType() {
        return requestedPlayType;
    }

    public Object generateEducatedPlay(GameBoardState state, PlayerID playingAs, StevePlayType requestedPlayType) {
        this.wasPlayRequested = true;
        this.requestedPlayType = requestedPlayType;
        return this.play;
    }
}
