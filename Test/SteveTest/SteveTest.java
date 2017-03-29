//package SteveTest;
//
//import GameBoard.GameBoardState;
//import GameBoard.GameBoardStateSenderData;
//import GameBoard.PhasePublisherData.BuildPhaseSenderData;
//import GameBoard.PhasePublisherData.TilePlacementPhaseSenderData;
//import GameBoard.PlayAck.PlayAck;
//import GameBoard.PlayAck.PlayAckSenderData;
//import GamePieceMap.GamePiece;
//import GamePieceMap.TypeOfPiece;
//import Location.Location;
//import Play.BuildPhase.BuildPhase;
//import Play.TilePlacementPhase.TilePlacementPhase;
//import Player.PlayerID;
//import Sender.Sender;
//import Sender.SenderData.SenderData;
//import Steve.Steve;
//import Receiver.Receiver;
//import TileBuilder.TileBuilder;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import Tile.Tile.Tile;
//
//public class SteveTest {
//    private Steve steve;
//
//    private GameBoardState gameBoardState;
//    private Receiver stevePlayRequestReceiver;
//    private Sender gameBoardAckSender;
//    private BuildPhase buildPhaseReceivedByDoBuildPhaseReceiver;
//    private Receiver gameBoardDoBuildReceiver;
//    private TilePlacementPhase tilePlacementPhaseReceivedByDoBuildReceiver;
//    private Receiver gameBoardDoTilePlacementReceiver;
//    private Sender gameBoardStateSender;
//    private Receiver gameBoardStateRequestReceiver;
//    private BuildPhase buildPhaseSentBySteve;
//    private TilePlacementPhase tilePlacementPhaseSentBySteve;
//    private TileBuilder builder;
//    private Sender generatePlayRequestSender;
//
//    private GameBoardState getGameBoardState() {
//        int p1Score = 100;
//        int p2Score = 200;
//        int turn = 1;
//
//        GameBoardState state = new GameBoardState(
//                p1Score,
//                p2Score,
//                turn,
//                null,
//                null,
//                null,
//                null);
//
//        return state;
//    }
//
//    @Before
//    public void initializeInstances() {
//
//        builder = new TileBuilder();
//
//        gameBoardDoBuildReceiver = new Receiver() {
//            @Override
//            public void callback(SenderData data) {
//                buildPhaseReceivedByDoBuildPhaseReceiver = ((BuildPhaseSenderData) data).getData();
//            }
//        };
//
//        gameBoardAckSender = new Sender();
//        gameBoardStateSender = new Sender();;
//
//        gameBoardDoTilePlacementReceiver = new Receiver() {
//            @Override
//            public void callback(SenderData data) {
//                tilePlacementPhaseReceivedByDoBuildReceiver = ((TilePlacementPhaseSenderData) data).getData();
//            }
//        };
//
//        gameBoardState = getGameBoardState();
//
//        gameBoardStateRequestReceiver = new Receiver() {
//            @Override
//            public void callback(SenderData data) {
//                GameBoardState currentGameBoardState = gameBoardState;
//                GameBoardStateSenderData gsData = new GameBoardStateSenderData(currentGameBoardState);
//                gameBoardStateSender.publish(gsData);
//            }
//        };
//
//        steve = new Steve();
//        steve.playAs(PlayerID.PLAYER_ONE);
//        stevePlayRequestReceiver = steve.getGeneratePlayRequestReceiver();
//        steve.setGameBoardAckSender(gameBoardAckSender);
//        steve.setGameBoardDoBuildReceiver(gameBoardDoBuildReceiver);
//        steve.setGameBoardDoTilePlacementReceiver(gameBoardDoTilePlacementReceiver);
//        steve.setGameBoardAckSender(gameBoardAckSender);
//        steve.setGameBoardStateRequestReceiver(gameBoardStateRequestReceiver);
//        steve.setGameBoardStateSender(gameBoardStateSender);
//
//        generatePlayRequestSender = new Sender();
//        generatePlayRequestSender.subscribe(stevePlayRequestReceiver);
//    }
//
//    @Test
//    public void steveShouldReceiveAGeneratePlayRequest() {
//        steveShouldNotHaveReceivedAnyGeneratePlayRequests();
//        givenAGeneratePlayRequestIsSentToSteve();
//        steveShouldHaveReceivedAGeneratePlayRequest();
//    }
//
//    private void steveShouldNotHaveReceivedAnyGeneratePlayRequests() {
//        Assert.assertFalse(steve.receivedGeneratePlayRequest());
//    }
//
//    private void givenAGeneratePlayRequestIsSentToSteve() {
//        //sends null since the generated play is determined by Steve
//        generatePlayRequestSender.publish(null);
//    }
//
//    private void steveShouldHaveReceivedAGeneratePlayRequest() {
//        Assert.assertTrue(steve.receivedGeneratePlayRequest());
//    }
//
//    @Test
//    public void steveShouldReceiveGameBoardState() {
//        steveGameBoardStateShouldBeNull(steve.getCurrentGameBoardState());
//        givenSteveRequestsGameBoardStateData();
//        thenTheGameBoardStateReceivedBySteveShouldBeTheOneFromThisClass();
//    }
//
//    private void steveGameBoardStateShouldBeNull(GameBoardState state) {
//        Assert.assertEquals(null, state);
//    }
//
//    private void givenSteveRequestsGameBoardStateData() {
//        steve.forceRequestGameBoardState();
//    }
//
//    private void thenTheGameBoardStateReceivedBySteveShouldBeTheOneFromThisClass() {
//        Assert.assertEquals(this.gameBoardState, steve.getCurrentGameBoardState());
//    }
//
//    @Test
//    public void gameBoardPlayReceiverShouldReceiveBuildPlayFromSteve() {
//        buildPhaseSentBySteve = givenIHaveABuildPhase();
//        givenSteveSendsAPlay(buildPhaseSentBySteve);
//        thenTheGameBoardDoBuildReceiverShouldReceiveTheBuildPlay();
//    }
//
//    private BuildPhase givenIHaveABuildPhase() {
//        GamePiece piece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
//        Location loc = new Location(0, 0);
//        return new BuildPhase(piece, loc);
//    }
//
//    private void givenSteveSendsAPlay(Object play) {
//        steve.forceSendPlayToGameBoard(play);
//    }
//
//    private void thenTheGameBoardDoBuildReceiverShouldReceiveTheBuildPlay() {
//        Assert.assertEquals(buildPhaseSentBySteve, buildPhaseReceivedByDoBuildPhaseReceiver);
//    }
//
//    @Test
//    public void  gameBoardPlayReceiverShouldReceiveTilePlacementPhaseFromSteve() {
//        tilePlacementPhaseSentBySteve = givenIHaveATilePlacementPhase();
//        givenSteveSendsAPlay(tilePlacementPhaseSentBySteve);
//        thenTheGameBoardDoBuildReceiverShouldReceiveTheTilePlacementPlay();
//
//    }
//
//    private TilePlacementPhase givenIHaveATilePlacementPhase() {
//        Tile tile = builder.getTileAtOrigin();
//        return new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
//    }
//
//    private void thenTheGameBoardDoBuildReceiverShouldReceiveTheTilePlacementPlay() {
//        Assert.assertEquals(tilePlacementPhaseSentBySteve, tilePlacementPhaseReceivedByDoBuildReceiver);
//    }
//
//    @Test
//    public void steveShouldReceiveAnAcknowledgmentMessageFromTheGameBoardAckSender() {
//        tilePlacementPhaseSentBySteve = givenIHaveATilePlacementPhase();
//        givenSteveSendsAPlay(tilePlacementPhaseSentBySteve);
//        whenTheGameBoardAckSenderSendsTheAck();
//        thenSteveShouldReceiveTheAck();
//    }
//
//    private void whenTheGameBoardAckSenderSendsTheAck() {
//        PlayAckSenderData ack = new PlayAckSenderData(PlayAck.VALID_PLAY);
//        gameBoardAckSender.publish(ack);
//    }
//
//    private void thenSteveShouldReceiveTheAck() {
//        Assert.assertTrue(steve.getLastReceivedAck() == PlayAck.VALID_PLAY);
//    }
//}