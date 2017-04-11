package GameMasterTest.GameMasterTest;

import GameMaster.*;
import GameMaster.ServerComm.GameClient;
import GameMaster.ServerComm.ServerClient;
import GameMaster.ServerComm.ServerMessages;
import LocalTestServer.LocalTestServer;
import org.junit.*;

import java.io.IOException;
import Networking.*;

@Ignore("Stalls")
public class GameMasterTest {
    private LocalTestServer testServer;
    private GameClient serverClient;
    private GameTestDouble gameOne;
    private GameTestDouble gameTwo;
    private GameMaster gameMaster;

    private String username;
    private String password;
    private String tournamentPassword;

    @Before
    public void initializeInstances() {
        username = "USER :D";
        password = "PASS :D";
        tournamentPassword = "TPASS :D";

        try {
            testServer = new LocalTestServer(PortNumber.FOR_TESTS);
            serverClient = new ServerClient("127.0.0.1", PortNumber.FOR_TESTS);
            testServer.handleClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameOne = new GameTestDouble();
        gameTwo = new GameTestDouble();
        gameMaster = new GameMaster(serverClient, gameOne, gameTwo);
        gameMaster.setPassword(password);
        gameMaster.setUsername(username);
        gameMaster.setTournamentPassword(tournamentPassword);
        gameMaster.start();
    }

    @After
    public void cleanUp() {
        testServer.close();
    }


    @Test
    public void uponStartTheGameMasterShouldBeWaitingForTheWelcomeMessage() {
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.WAITING_FOR_WELCOME);
    }

    private void thenTheGameMasterPhaseStateShouldBe(GamePhaseState expected) {
        while (gameMaster.getCurrentGamePhaseState() != expected) {
        }
        Assert.assertEquals(expected, gameMaster.getCurrentGamePhaseState());
    }

    @Test
    public void gameMasterShouldMoveToWaitingForTwoShallEnterAfterReceivingWelComeMessage() {
        givenTheServerHasSentTheWelcomeMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.WAITING_FOR_TWO_SHALL_ENTER);
    }

    private void givenTheServerHasSentTheWelcomeMessage() {
        testServer.sendMessageToClient(ServerMessages.WELCOME_MESSAGE);
    }

    @Test
    public void gameMasterShouldMoveToWaitingForTournamentToBegin() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerHasSentTheTwoShallEnterMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.WAITING_FOR_TOURNAMENT_TO_BEGIN_WITH_PID);
    }

    private void givenTheServerHasSentTheTwoShallEnterMessage() {
        testServer.sendMessageToClient(ServerMessages.TWO_SHALL_ENTER_MESSAGE);
    }

    @Test
    public void gameMasterShouldMoveToWaitingForChallenge() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerHasSentTheTwoShallEnterMessage();
        givenTheServerHasSentTheWaitForTournamentToBeginMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.WAITING_FOR_CHALLENGE);
        thenTheGameMasterOurPlayerIdShouldBe("Blah");
    }

    private void givenTheServerHasSentTheWaitForTournamentToBeginMessage() {
        testServer.sendMessageToClient(ServerMessages.WAIT_FOR_TOURNAMENT_PID_MESSAGE + " Blah");
    }

    private void thenTheGameMasterOurPlayerIdShouldBe(String ourExpectedPid) {
        String ourPid = null;

        while(ourPid == null) {
            ourPid = gameMaster.getOurPidFromServer();
        }

        Assert.assertEquals(ourExpectedPid, ourPid);
    }

    @Test
    public void gameMasterShouldMoveToWaitingForRoundToBeing() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerHasSentTheTwoShallEnterMessage();
        givenTheServerHasSentTheWaitForTournamentToBeginMessage();
        givenTheServerHasSentTheNewChallengeMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.WAITING_FOR_ROUND_TO_BEGIN);
        thenTheGameMasterCurrentChallengeIdShouldBe("<cid>");
        thenTheGameMasterRoundsShouldBe("<rounds>");
    }

    private void givenTheServerHasSentTheNewChallengeMessage() {
        testServer.sendMessageToClient(ServerMessages.NEW_CHALLENGE_MESSAGE + " <cid> YOU WILL PLAY <rounds> MATCH");
    }

    private void thenTheGameMasterCurrentChallengeIdShouldBe(String expectedCid) {
        String cid = null;
        while(cid == null) {
            cid = gameMaster.getCurrentChallengeId();
        }

        Assert.assertEquals(expectedCid, cid);
    }

    private void thenTheGameMasterRoundsShouldBe(String expectedRounds) {
        String rounds = null;
        while(rounds == null) {
            rounds = gameMaster.getCurrentRounds();
        }

        Assert.assertEquals(expectedRounds, rounds);
    }

    @Test
    public void gameMasterShouldMoveToInRound() {
        givenTheServerHasSentTheWelcomeMessage();
        givenTheServerHasSentTheTwoShallEnterMessage();
        givenTheServerHasSentTheWaitForTournamentToBeginMessage();
        givenTheServerHasSentTheNewChallengeMessage();
        givenTheServerHasSentTheBeginRoundMessage();
        thenTheGameMasterPhaseStateShouldBe(GamePhaseState.IN_ROUND);
    }

    private void givenTheServerHasSentTheBeginRoundMessage() {
        testServer.sendMessageToClient(ServerMessages.BEGIN_ROUND_MESSAGE + " <rid> OF <rounds>");
    }
}