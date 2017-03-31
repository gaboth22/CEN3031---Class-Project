package GameBoardTest;

import GameBoard.GameBoard;
import GameBoard.PhasePublisherData.BuildPhaseSenderData;
import GameBoard.PhasePublisherData.TilePlacementPhaseSenderData;
import GameBoard.PlayAck.PlayAck;
import GameBoard.PlayAck.PlayAckSenderData;
import GameBoard.Proxy.Proxy;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Receiver.Receiver;
import Sender.Sender;
import Sender.SenderData.SenderData;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProxyTest {
    private Proxy gameBoardProxy;
    private GameBoard board;

    private Receiver proxyDoBuildPhaseRequestReceiver;
    private Receiver proxyDoTilePlacementRequestReceiver;

    private Sender doBuildPhaseRequestSender;
    private Sender doTilePlacementPhaseRequestSender;

    private Receiver ackReceiver;
    private Sender ackSender;

    private boolean ackSucceeded;
    private TileBuilder builder;

    @Before
    public void initializeInstances() {
        builder = new TileBuilder();
        board = new GameBoardTestDouble();
        gameBoardProxy = new Proxy(board);

        doBuildPhaseRequestSender = new Sender();
        doTilePlacementPhaseRequestSender = new Sender();

        proxyDoBuildPhaseRequestReceiver = gameBoardProxy.getDoBuildPhaseRequestReceiver();
        proxyDoTilePlacementRequestReceiver = gameBoardProxy.getDoTilePlacePhaseRequestReceiver();

        doBuildPhaseRequestSender.subscribe(proxyDoBuildPhaseRequestReceiver);
        doTilePlacementPhaseRequestSender.subscribe(proxyDoTilePlacementRequestReceiver);

        ackReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                PlayAck ack = ((PlayAckSenderData) data).getData();
                if(ack == PlayAck.INVALID_PLAY)
                    ackSucceeded = false;
                else
                    ackSucceeded = true;
            }
        };

        ackSender = gameBoardProxy.getGameBoardAckSender();
        ackSender.subscribe(ackReceiver);
    }

    @Test
    public void theProxyShouldReturnAFailureAckAfterAttemptingFailingBuildPhase() {
        givenTheGameBoardBuildPhaseIsKnownToFail();
        whenTheProxyIsRequestedToDoABuildPhase();
        thenTheAckShouldBeFailure();

    }

    private void givenTheGameBoardBuildPhaseIsKnownToFail() {
        ((GameBoardTestDouble) board).failBuildPhase();
    }

    private void whenTheProxyIsRequestedToDoABuildPhase() {
        GamePiece piece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        Location loc = new Location(0, 0);
        BuildPhase play = new BuildPhase(piece, loc);
        BuildPhaseSenderData buildPhaseData = new BuildPhaseSenderData(play);

        doBuildPhaseRequestSender.publish(buildPhaseData);
    }

    private void thenTheAckShouldBeFailure() {
        Assert.assertFalse(ackSucceeded);
    }

    @Test
    public void theProxyShouldReturnASuccessfulAckAfterAttemptingAValidBuildPhase() {
        whenTheProxyIsRequestedToDoABuildPhase();
        thenTheAckShouldBeSucess();
    }

    private void thenTheAckShouldBeSucess() {
        Assert.assertTrue(ackSucceeded);
    }

    @Test
    public void theProxyShouldReturnAFailureAckAfterAttemptingFailingTilePlacement() {
        givenTheGameBoardTilePlacementPhaseIsKnownToFail();
        whenTheProxyIsRequestedToDoATilePlacementPhase();
        thenTheAckShouldBeFailure();
    }

    private void givenTheGameBoardTilePlacementPhaseIsKnownToFail() {
        ((GameBoardTestDouble) board).failTilePlacementPhase();
    }

    private void whenTheProxyIsRequestedToDoATilePlacementPhase() {
        Tile tile = builder.getTileAtOrigin();
        TilePlacementPhase play = new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
        TilePlacementPhaseSenderData tpPhaseData = new TilePlacementPhaseSenderData(play);

        doTilePlacementPhaseRequestSender.publish(tpPhaseData);
    }

    @Test
    public void theProxyShouldReturnASuccessAckAfterAttemptingAValidTilePlacement() {
        whenTheProxyIsRequestedToDoATilePlacementPhase();
        thenTheAckShouldBeSucess();
    }
}
