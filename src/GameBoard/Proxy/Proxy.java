package GameBoard.Proxy;

import Debug.*;
import GameBoard.*;
import GameBoard.GameBoardStateBuilder.GameBoardStateBuilder;
import GameBoard.PhasePublisherData.BuildPhaseSenderData;
import GameBoard.PhasePublisherData.TilePlacementPhaseSenderData;
import GameBoard.PlayAck.PlayAck;
import GameBoard.PlayAck.PlayAckSenderData;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Receiver.Receiver;
import Sender.Sender;
import Sender.SenderData.SenderData;

public class Proxy {
    private GameBoard board;
    private GameBoardStateBuilder stateBuilder;
    private Sender gameBoardStateSender;
    private Sender gameBoardAckSender;
    private Receiver gameBoardStateRequestReceiver;
    private Receiver doBuildPhaseRequestReceiver;
    private Receiver doTilePlacePhaseRequestReceiver;

    public Proxy(GameBoard board) {
        this.board = board;
        gameBoardStateSender = new Sender();
        gameBoardAckSender = new Sender();

        gameBoardStateRequestReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                GameBoardState state = buildGameBoardState();
                GameBoardStateSenderData stateData = new GameBoardStateSenderData(state);
                gameBoardStateSender.publish(stateData);
            }
        };

        doBuildPhaseRequestReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                BuildPhase phase = ((BuildPhaseSenderData) data).getData();
                boolean buildSucceeded = doBuildPhase(phase);

                if(buildSucceeded) {
                    PlayAckSenderData ack = new PlayAckSenderData(PlayAck.VALID_PLAY);
                    gameBoardAckSender.publish(ack);
                }
                else {
                    PlayAckSenderData ack = new PlayAckSenderData(PlayAck.INVALID_PLAY);
                    gameBoardAckSender.publish(ack);
                }
            }
        };

        doTilePlacePhaseRequestReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                TilePlacementPhase phase = ((TilePlacementPhaseSenderData) data).getData();
                boolean tilePlacementSucceeded = doTilePlacement(phase);

                if(tilePlacementSucceeded) {
                    PlayAckSenderData ack = new PlayAckSenderData(PlayAck.VALID_PLAY);
                    gameBoardAckSender.publish(ack);
                }
                else {
                    PlayAckSenderData ack = new PlayAckSenderData(PlayAck.INVALID_PLAY);
                    gameBoardAckSender.publish(ack);
                }
            }
        };
    }

    private boolean doBuildPhase(BuildPhase phase) {
        try {
            board.doBuildPhase(phase);
        }
        catch(Exception e) {
            Debug.print(e.getMessage(), DebugLevel.ERROR);
            return false;
        }

        return true;
    }

    private boolean doTilePlacement(TilePlacementPhase phase) {
        try {
            board.doTilePlacementPhase(phase);
        }
        catch(Exception e) {
            Debug.print(e.getMessage(), DebugLevel.ERROR);
            return false;
        }

        return true;
    }

    public void setGameBoardStateBuilder(GameBoardStateBuilder builder) {
        stateBuilder = builder;
    }

    private GameBoardState buildGameBoardState() {
        return stateBuilder.buildGameBoardState(board);
    }

    public Sender getGameBoardStateSender() {
        return gameBoardStateSender;
    }

    public Sender getGameBoardAckSender() {
        return gameBoardAckSender;
    }

    public Receiver getGameBoardStateRequestReceiver() {
        return gameBoardStateRequestReceiver;
    }

    public Receiver getDoBuildPhaseRequestReceiver() {
        return  doBuildPhaseRequestReceiver;
    }

    public Receiver getDoTilePlacePhaseRequestReceiver() {
        return doTilePlacePhaseRequestReceiver;
    }
}
