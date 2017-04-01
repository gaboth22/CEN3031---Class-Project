package Steve;

import Debug.*;
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

import java.security.InvalidParameterException;

public class Steve {

    private PlayerID playingAs;

    private PlayGenerator playGenerator;

    private Receiver generatePlayRequestReceiver;
    private Receiver gameBoardAckReceiver;
    private Receiver gameBoardStateReceiver;

    private Sender getGameBoardStateRequestSender;
    private Sender doBuildPhaseRequestSender;
    private Sender doTilePlacementPhaseRequestSender;

    private GameBoardState currentGameBoardState;
    private StevePlayType requestedPlayType;

    private Object lastValidPlay;

    public Steve() {

        doBuildPhaseRequestSender = new Sender();
        doTilePlacementPhaseRequestSender = new Sender();
        getGameBoardStateRequestSender = new Sender();
        currentGameBoardState = null;
        lastValidPlay = null;

        gameBoardAckReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                evaluateAck(((PlayAckSenderData) data).getData());
            }
        };

        generatePlayRequestReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                requestedPlayType = ((StevePlayTypeSenderData) data).getData();
                generatePlay();
            }
        };

        gameBoardStateReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                currentGameBoardState = ((GameBoardStateSenderData) data).getData();
            }
        };
    }


    public void playAs(PlayerID player) {
        playingAs = player;
    }

    public void setPlayGenerator(PlayGenerator generator) {
        this.playGenerator = generator;
    }

    private void evaluateAck(PlayAck ack) {
        if(ack == PlayAck.VALID_PLAY)
            return;
        else
            generatePlay();
    }

    private void generatePlay() {
        sendGetGameBoardStateRequest();
        lastValidPlay = playGenerator.generateEducatedPlay(currentGameBoardState, playingAs, requestedPlayType);
        sendPlayToGameBoard(lastValidPlay);
    }

    private void sendPlayToGameBoard(Object play) {
        if(play instanceof BuildPhase) {
            sendBuildPlayToGameBoard((BuildPhase) play);
        }

        else if(play instanceof TilePlacementPhase) {
            sendTilePlacementPlayToGameBoard((TilePlacementPhase) play);
        }
        else {
            Debug.print("Invalid play object passed to Steve", DebugLevel.ERROR);
            throw new InvalidParameterException("Invalid play object passed to Steve");
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
        doBuildPhaseRequestSender.publish(playData);
    }

    private void sendTilePlacementPlayToGameBoard(TilePlacementPhase play) {
        TilePlacementPhaseSenderData playData = new TilePlacementPhaseSenderData(play);
        doTilePlacementPhaseRequestSender.publish(playData);
    }

    public Receiver getGeneratePlayRequestReceiver() {
        return generatePlayRequestReceiver;
    }

    public Receiver getGameBoardAckReceiver() {
        return gameBoardAckReceiver;
    }

    public Receiver getGameBoardStateReceiver() {
        return gameBoardStateReceiver;
    }

    public Sender getGetGameBoardStateRequestSender() {
        return getGameBoardStateRequestSender;
    }

    public Sender getDoBuildPhaseRequestSender() {
        return doBuildPhaseRequestSender;
    }

    public Sender getDoTilePlacementPhaseRequestSender() {
        return doTilePlacementPhaseRequestSender;
    }

    public GameBoardState getCurrentGameBoardState() {
        return currentGameBoardState;
    }

    public Object getLastValidPlay() {
        return lastValidPlay;
    }
}