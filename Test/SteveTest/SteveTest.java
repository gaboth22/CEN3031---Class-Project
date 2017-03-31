package SteveTest;

import GameBoard.GameBoardState;
import GameBoard.GameBoardStateSenderData;
import GameBoard.PhasePublisherData.BuildPhaseSenderData;
import GameBoard.PhasePublisherData.TilePlacementPhaseSenderData;
import GameBoard.PlayAck.PlayAck;
import GameBoard.PlayAck.PlayAckSenderData;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Sender.Sender;
import Sender.SenderData.SenderData;
import Steve.Steve;
import Receiver.Receiver;
import TileBuilder.TileBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Tile.Tile.Tile;

public class SteveTest {

    private GameBoardState gameBoardState;
    private TileBuilder builder;

    private Steve steve;
    private PlayGeneratorTestDouble playGeneratorTestDouble;

    private Receiver stevesGeneratePlayRequestReceiver;
    private Receiver stevesGameBoardAckReceiver;
    private Receiver stevesGameBoardStateReceiver;

    private Sender stevesGetGameBoardStateRequestSender;
    private Sender stevesDoBuildPhaseRequestSender;
    private Sender stevesDoTilePlacementPhaseRequestSender;

    private Receiver getGameBoardStateRequestReceiver;

    private Sender generatePlayRequestSender;
    private Sender gameBoardStateSender;
    private Sender ackSender;

    private Receiver doBuildPhaseRequestFromSteveReceiver;
    private Receiver doTilePlacementPhaseRequestFromSteveReceiver;

    private Object phaseReceivedFromSteve;

    @Before
    public void initializeInstances() {

        /*
            Dummy GameBoard to just have an instance of it
         */
        gameBoardState = new GameBoardState(
                0,
                0,
                0,
                null,
                null,
                null,
                null);

        builder = new TileBuilder();

        steve = new Steve();
        steve.playAs(PlayerID.PLAYER_ONE);

        stevesGeneratePlayRequestReceiver = steve.getGeneratePlayRequestReceiver();
        stevesGameBoardAckReceiver = steve.getGameBoardAckReceiver();
        stevesGameBoardStateReceiver = steve.getGameBoardStateReceiver();

        stevesGetGameBoardStateRequestSender = steve.getGetGameBoardStateRequestSender();
        stevesDoBuildPhaseRequestSender = steve.getDoBuildPhaseRequestSender();
        stevesDoTilePlacementPhaseRequestSender = steve.getDoTilePlacementPhaseRequestSender();

        generatePlayRequestSender = new Sender();

        playGeneratorTestDouble = new PlayGeneratorTestDouble();

        gameBoardStateSender = new Sender();
        gameBoardStateSender.subscribe(stevesGameBoardStateReceiver);

        ackSender = new Sender();
        ackSender.subscribe(stevesGameBoardAckReceiver);

        getGameBoardStateRequestReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                GameBoardStateSenderData stateData = new GameBoardStateSenderData(gameBoardState);
                gameBoardStateSender.publish(stateData);
            }
        };

        stevesGetGameBoardStateRequestSender.subscribe(getGameBoardStateRequestReceiver);

        doBuildPhaseRequestFromSteveReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                phaseReceivedFromSteve = ((BuildPhaseSenderData) data).getData();
            }
        };

        stevesDoBuildPhaseRequestSender.subscribe(doBuildPhaseRequestFromSteveReceiver);

        doTilePlacementPhaseRequestFromSteveReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                phaseReceivedFromSteve = ((TilePlacementPhaseSenderData) data).getData();
            }
        };

        stevesDoTilePlacementPhaseRequestSender.subscribe(doTilePlacementPhaseRequestFromSteveReceiver);
    }

    @Test
    public void steveShouldReceiveAGeneratePlayRequest() {
        givenStevesGeneratePlayRequestReceiverHasBeenSubscribedToTheSender();
        givenTheGeneratorWillGenerateATilePlacementPhase();
        whenStevesPlayGeneratorIsSet();
        whenTheGeneratePlayRequestSenderSendsARequestToSteve();
        thenSteveShouldGetAPlayFromThePlayGenerator();
    }

    private void givenStevesGeneratePlayRequestReceiverHasBeenSubscribedToTheSender() {
        generatePlayRequestSender.subscribe(stevesGeneratePlayRequestReceiver);
    }

    private void whenTheGeneratePlayRequestSenderSendsARequestToSteve() {
       /*
            The body of the request is irrelevant,
            as this is only to make Steve generate a play.
        */
        generatePlayRequestSender.publish(null);
    }

    private void givenTheGeneratorWillGenerateATilePlacementPhase() {
        Tile tile = builder.getTileAtOrigin();
        TilePlacementPhase play = new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
        playGeneratorTestDouble.setPlayToReturn(play);
    }

    private void whenStevesPlayGeneratorIsSet() {
        steve.setPlayGenerator(playGeneratorTestDouble);
    }


    private void thenSteveShouldGetAPlayFromThePlayGenerator() {
        Assert.assertTrue(playGeneratorTestDouble.wasPlayRequested());
    }

    @Test
    public void steveShouldReceiveGameBoardState() {
        stevesGameBoardStateShouldBeNull();
        givenStevesGeneratePlayRequestReceiverHasBeenSubscribedToTheSender();
        givenTheGeneratorWillGenerateATilePlacementPhase();
        whenStevesPlayGeneratorIsSet();
        whenTheGeneratePlayRequestSenderSendsARequestToSteve();
        thenSteveShouldHaveReceivedGameBoardState();
    }

    private void stevesGameBoardStateShouldBeNull() {
        Assert.assertEquals(null, steve.getCurrentGameBoardState());
    }

    private void thenSteveShouldHaveReceivedGameBoardState() {
        Assert.assertFalse(steve.getCurrentGameBoardState() == null);
    }

    @Test
    public void steveShouldNotAttemptToGenerateAnotherPlayBecauseAckIsSuccessful() {
        givenTheGeneratorWillGenerateATilePlacementPhase();
        whenStevesPlayGeneratorIsSet();
        givenTheAckSenderSendsAckToSteve(PlayAck.VALID_PLAY);
        thenSteveShouldNotEvenRequestAPlay();
    }

    private void thenSteveShouldNotEvenRequestAPlay() {
        Assert.assertFalse(playGeneratorTestDouble.wasPlayRequested());
    }

    @Test
    public void steveShouldAttemptToGenerateAnotherPlayAgainIfAckWasInvalid() {
        givenTheGeneratorWillGenerateATilePlacementPhase();
        whenStevesPlayGeneratorIsSet();
        givenTheAckSenderSendsAckToSteve(PlayAck.INVALID_PLAY);
        thenSteveShouldGetAPlayFromThePlayGenerator();
    }

    private void givenTheAckSenderSendsAckToSteve(PlayAck ack) {
        PlayAckSenderData ackData = new PlayAckSenderData(ack);
        ackSender.publish(ackData);
    }

    @Test
    public void steveShouldSendATilePlacementPhase() {
        givenStevesGeneratePlayRequestReceiverHasBeenSubscribedToTheSender();
        givenTheGeneratorWillGenerateATilePlacementPhase();
        whenStevesPlayGeneratorIsSet();
        whenTheGeneratePlayRequestSenderSendsARequestToSteve();
        thenTheReceivedPlayShouldBeATilePlacementPhase();
    }

    private void thenTheReceivedPlayShouldBeATilePlacementPhase() {
        Assert.assertTrue(phaseReceivedFromSteve instanceof TilePlacementPhase);
    }

    @Test
    public void steveShouldSendABuildPhase() {
        givenStevesGeneratePlayRequestReceiverHasBeenSubscribedToTheSender();
        givenTheGeneratorWillGenerateABuildPhase();
        whenStevesPlayGeneratorIsSet();
        whenTheGeneratePlayRequestSenderSendsARequestToSteve();
        thenTheReceivedPlayShouldBeABuildPhase();
    }

    private void givenTheGeneratorWillGenerateABuildPhase() {

        GamePiece piece = new GamePiece(PlayerID.PLAYER_ONE,
                TypeOfPiece.VILLAGER);
        BuildPhase play = new BuildPhase(piece, new Location(0, 0));
        playGeneratorTestDouble.setPlayToReturn(play);
    }

    private void thenTheReceivedPlayShouldBeABuildPhase() {
        Assert.assertTrue(phaseReceivedFromSteve instanceof BuildPhase);
    }
}