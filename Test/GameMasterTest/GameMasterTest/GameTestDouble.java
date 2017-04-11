package GameMasterTest.GameMasterTest;

import GameMaster.Game.Game;
import Receiver.Receiver;
import Sender.Sender;
import Sender.SenderData.SenderData;

public class GameTestDouble extends Game {

    private Sender stevePlaySender;
    private String[] playInfoForSteve;
    private String opponentPlay;

    public boolean wasRunCalled;
    public boolean wasResetGameStateCalled;

    public GameTestDouble() {
        stevePlaySender = new Sender();
        wasRunCalled = false;
        wasResetGameStateCalled = false;
    }

    @Override
    public void setPlayReceiver(Receiver playReceiver) {
        stevePlaySender.subscribe(playReceiver);
    }

    @Override
    public void haveSteveDoPlay(String gameId, String moveNumber, String tileFromServer) {
        playInfoForSteve = new String[]{gameId, moveNumber, tileFromServer};
    }

    @Override
    public void enforceOpponentPlay(String playFromServer) {
        opponentPlay = playFromServer;
    }

    @Override
    public void run() {
        wasRunCalled = true;
    }

    @Override
    public void resetGameState(String gameID) {
    wasResetGameStateCalled = true;
    }

    public String[] getPlayInfoForSteve() {
        return playInfoForSteve;
    }

    public String getOpponentPlay() {
        return opponentPlay;
    }

    public void sendSteveFormattedPlay(String formattedPlay) {
        stevePlaySender.publish(new SenderData() {
            @Override
            public Object getData() {
                return formattedPlay;
            }
        });
    }
}
