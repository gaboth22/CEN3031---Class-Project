package GameMasterTest.GameMasterTest;

import GameMaster.*;
import GameMaster.ServerComm.ServerClient;
import GameMaster.ServerComm.ServerMessages;
import LocalTestServer.LocalTestServer;
import org.junit.*;

import java.io.IOException;
import Networking.*;

public class GameMasterTest {
    private LocalTestServer testServer;
    private ServerClient serverClient;
    private GameTestDouble gameOne;
    private GameTestDouble gameTwo;
    private GameMaster gameMaster;

    @Before
    public void initializeInstances() {
        try {
            testServer = new LocalTestServer(PortNumber.FOR_TESTS);
            serverClient = new ServerClient("127.0.0.1", PortNumber.FOR_TESTS);
            testServer.handleClient();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        gameOne = new GameTestDouble();
        gameTwo = new GameTestDouble();
        gameMaster = new GameMaster(serverClient, gameOne, gameTwo);
        gameMaster.start();
    }

    @After
    public void cleanUp() {
        testServer.close();
    }


    @Test
    public void gameMasterShouldMoveToWaitingForChallengeOnceTheWelcomeMessageIsReceived() {
        givenTheServerHasSentTheWelcomeMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.WAITING_FOR_CHALLENGE);
    }

    private void givenTheServerHasSentTheWelcomeMessage() {
        testServer.sendMessageToClient(ServerMessages.WELCOME_MESSAGE);
    }

    private void thenTheGameMasterPhaseStateShouldBe(GamePhaseState expected) {
        while(gameMaster.getCurrentGamePhaseState() != expected) {}
        Assert.assertEquals(expected, gameMaster.getCurrentGamePhaseState());
    }

    @Test
    public void gameMasterShouldMoveToWaitingForRoundCountOnceThePlayerIdMessageIsReceived() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerSendsThePlayerIdMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.WAITING_FOR_ROUND_COUNT);
    }

    private void givenTheServerSendsThePlayerIdMessage() {
        testServer.sendMessageToClient(ServerMessages.PID_MESSAGE + " 23");
    }

    @Test
    public void gameMasterShouldHaveRetrievedPlayerIdFromServer() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerSendsThePlayerIdMessage();
        thenTheGameMasterPlayerIdShouldBe("23");
    }

    private void thenTheGameMasterPlayerIdShouldBe(String expectedPid) {
        while(gameMaster.getOurPidFromServer() == null) {}
        Assert.assertEquals(expectedPid, gameMaster.getOurPidFromServer());
    }

    @Test
    public void gameMasterShouldGetRoundCount() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerSendsThePlayerIdMessage();
        givenTheServerSendTheRoundCountMessage();
        thenTheGameMasterRoundCountShouldBe(19);
    }

    private void givenTheServerSendTheRoundCountMessage() {
        testServer.sendMessageToClient(ServerMessages.ROUND_COUNT_MESSAGE + " 1 OF 19");
    }

    private void thenTheGameMasterRoundCountShouldBe(int expectedRoundCount) {
        while(gameMaster.getRoundCount() == 0) {}
        Assert.assertEquals(expectedRoundCount, gameMaster.getRoundCount());
    }

    @Test
    public void gameMasterShouldMoveToInRound() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerSendsThePlayerIdMessage();
        givenTheServerSendTheRoundCountMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.IN_ROUND);
    }
}