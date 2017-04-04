package Steve;

import Debug.*;
import GameBoard.GameBoardState;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;

import java.security.InvalidParameterException;

public class Steve {

    private PlayerID playingAs;

    private PlayGenerator playGenerator;

    private GameBoardState currentGameBoardState;
    private StevePlayType requestedPlayType;

    private Object lastValidPlay;

    public Steve() {

        currentGameBoardState = null;
        lastValidPlay = null;

    }


    public void playAs(PlayerID player) {
        playingAs = player;
    }

    public void setPlayGenerator(PlayGenerator generator) {
        this.playGenerator = generator;
    }

    private void generatePlay() {
        //TODO: generate A Play
        sendPlayToGameBoard(lastValidPlay);
    }

    private void sendPlayToGameBoard(Object play) {
        if(play instanceof BuildPhase) {

        }

        else if(play instanceof TilePlacementPhase) {

        }
        else {
            Debug.print("Invalid play object passed to Steve", DebugLevel.ERROR);
            throw new InvalidParameterException("Invalid play object passed to Steve");
        }
    }

    public GameBoardState getCurrentGameBoardState() {
        return currentGameBoardState;
    }

    public Object getLastValidPlay() {
        return lastValidPlay;
    }
}