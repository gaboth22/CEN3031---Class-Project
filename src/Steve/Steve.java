package Steve;

import GameBoard.GameBoardState;
import GameBoard.GameBoardStateSenderData;
import GameBoard.PhasePublisherData.BuildPhaseSenderData;
import GameBoard.PhasePublisherData.TilePlacementPhaseSenderData;
import GameBoard.PlayAck.PlayAck;
import GameBoard.PlayAck.PlayAckSenderData;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Sender.Sender;
import Sender.SenderData.SenderData;
import Receiver.Receiver;

public class Steve {
    private PlayerID playingAs;
    private Receiver generatePlayRequestReceiver;
    private Receiver gameBoardAckReceiver;
    private Sender getGameBoardStateRequestSender;
    private Receiver gameBoardStateReceiver;
    private Sender doBuildPlaySender;
    private Sender doTilePlacementPlaySender;
    private GameBoardState currentGameBoardState;
    private boolean receivedGeneratePlayRequest;
    private PlayAck lastReceivedAck;

    public Steve() {
        receivedGeneratePlayRequest = false;

        gameBoardAckReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                lastReceivedAck = ((PlayAckSenderData) data).getData();
                evaluateAck(lastReceivedAck);
            }
        };

        generatePlayRequestReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                receivedGeneratePlayRequest = true;
                generatePlay();
            }
        };

        gameBoardStateReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                currentGameBoardState = ((GameBoardStateSenderData) data).getData();
            }
        };

        doBuildPlaySender = new Sender();
        doTilePlacementPlaySender = new Sender();
        getGameBoardStateRequestSender = new Sender();
        currentGameBoardState = null;
    }

    private void evaluateAck(PlayAck ack) {
        if(ack == PlayAck.VALID_PLAY)
            return;
        else
            generatePlay();
    }

    private void generatePlay() {
        sendGetGameBoardStateRequest();
        while(currentGameBoardState == null) {}
        Object play = PlayGenerator.generateEducatedPlay(currentGameBoardState, playingAs);
        sendPlayToGameBoard(play);
        resetCurrentGameBoardStateToNull();
    }

    private void resetCurrentGameBoardStateToNull() {
        currentGameBoardState = null;
    }

    private void sendPlayToGameBoard(Object play) {
        if(play instanceof BuildPhase) {
            sendBuildPlayToGameBoard((BuildPhase) play);
        }

        else if(play instanceof TilePlacementPhase) {
            sendTilePlacementPlayToGameBoard((TilePlacementPhase) play);
        }
    }

    private void sendGetGameBoardStateRequest() {
        /*
            publishes null since the GameBoard doesn't care who
            requests GameBoardState, it'll just publish it.
        */
        getGameBoardStateRequestSender.publish(null);
    }

    private void sendBuildPlayToGameBoard(BuildPhase play) {
        BuildPhaseSenderData playData = new BuildPhaseSenderData(play);
        doBuildPlaySender.publish(playData);
    }

    private void sendTilePlacementPlayToGameBoard(TilePlacementPhase play) {
        TilePlacementPhaseSenderData playData = new TilePlacementPhaseSenderData(play);
        doTilePlacementPlaySender.publish(playData);
    }

    public Receiver getGeneratePlayRequestReceiver() {
        return generatePlayRequestReceiver;
    }

    public void playAs(PlayerID player) {
        playingAs = player;
    }

    public void setGameBoardStateSender(Sender pub) {
        pub.subscribe(gameBoardStateReceiver);
    }

    public void setGameBoardAckSender(Sender pub) {
        pub.subscribe(gameBoardAckReceiver);
    }

    public void setGameBoardDoBuildReceiver(Receiver sub) {
        doBuildPlaySender.subscribe(sub);
    }

    public void setGameBoardDoTilePlacementReceiver(Receiver sub) {
        doTilePlacementPlaySender.subscribe(sub);
    }

    public void setGameBoardStateRequestReceiver(Receiver sub) {
        getGameBoardStateRequestSender.subscribe(sub);
    }

    public GameBoardState getCurrentGameBoardState() {
        return currentGameBoardState;
    }

    public void forceRequestGameBoardState() {
        sendGetGameBoardStateRequest();
    }

    public void forceSendPlayToGameBoard(Object play) {
        sendPlayToGameBoard(play);
    }

    public boolean receivedGeneratePlayRequest() {
        return receivedGeneratePlayRequest;
    }

    public PlayAck getLastReceivedAck() {
        return lastReceivedAck;
    }
}