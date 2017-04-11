package GameMaster;

import Debug.*;
import GameMaster.Game.Game;
import GameMaster.ServerComm.GameClient;
import GameMaster.ServerComm.Parsers.*;
import Receiver.Receiver;
import Sender.SenderData.SenderData;

import static GameMaster.ServerComm.ServerMessages.*;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class GameMaster extends Thread {

    private Receiver gameOneStevePlayReceiver;
    private Receiver gameTwoStevePlayReceiver;
    private String gameOneStevePlay;
    private String gameTwoStevePlay;
    private Game gameOne;
    private Game gameTwo;
    private GameClient gameClient;
    private GamePhaseState gamePhaseState;

    private String currentChallengeId;
    private String ourPidFromServer;
    private String currentRoundId;
    private String currentRounds;

    /*
        Need to be set from main
     */
    private String tournamentPassword;
    private String username;
    private String password;

    private Map<String, Integer> gameIdMap;
    private int gameIdSeed;
    private static final int GAME_ONE = 0;
    private static final int GAME_TWO = 1;

    public GameMaster(GameClient gameClient, Game gameOne, Game gameTwo) {
        currentChallengeId = null;
        ourPidFromServer = null;
        currentRounds = null;
        currentRoundId = null;

        gameIdMap = new HashMap<>();
        gameIdSeed = 0;

        gameOneStevePlayReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                gameOneStevePlay = (String) data.getData();
                sendStringToServer(gameOneStevePlay);
            }
        };

        gameTwoStevePlayReceiver = new Receiver() {
            @Override
            public void callback(SenderData data) {
                gameTwoStevePlay = (String) data.getData();
                sendStringToServer(gameTwoStevePlay);
            }
        };

        gameOneStevePlay = null;
        gameTwoStevePlay = null;

        this.gameClient = gameClient;
        this.gameOne = gameOne;
        this.gameTwo = gameTwo;

        gamePhaseState = GamePhaseState.WAITING_FOR_WELCOME;

        gameOne.setPlayReceiver(this.gameOneStevePlayReceiver);
        gameTwo.setPlayReceiver(this.gameTwoStevePlayReceiver);

        this.gameOne.start();
        this.gameTwo.start();
    }

    public void setTournamentPassword(String tournamentPassword) {
        this.tournamentPassword = tournamentPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GamePhaseState getCurrentGamePhaseState() {
        return gamePhaseState;
    }

    public String getOurPidFromServer() {
        return ourPidFromServer;
    }

    public String getCurrentRounds() {
        return currentRounds;
    }

    public String getCurrentRoundId() {
        return currentRoundId;
    }

    public String getCurrentChallengeId() {
        return currentChallengeId;
    }

    @Override
    public void run() {

        while(true) {

            if(gamePhaseState == GamePhaseState.WAITING_FOR_WELCOME) {
                waitForWelcome();
                sendEnterMessage();
                gamePhaseState = GamePhaseState.WAITING_FOR_TWO_SHALL_ENTER;
            }

            else if(gamePhaseState == GamePhaseState.WAITING_FOR_TWO_SHALL_ENTER) {
                waitForTwoShallEnterMessage();
                sendIAmMessage();
                gamePhaseState = GamePhaseState.WAITING_FOR_TOURNAMENT_TO_BEGIN_WITH_PID;
            }

            else if(gamePhaseState == GamePhaseState.WAITING_FOR_TOURNAMENT_TO_BEGIN_WITH_PID) {
                waitForTournamentToBeginWithPidMessage();
                gamePhaseState = GamePhaseState.WAITING_FOR_CHALLENGE;
            }

            else if (gamePhaseState == GamePhaseState.WAITING_FOR_CHALLENGE) {
                waitForNewChallengeMessage();
                gamePhaseState = GamePhaseState.WAITING_FOR_ROUND_TO_BEGIN;
            }

            else if (gamePhaseState == GamePhaseState.WAITING_FOR_ROUND_TO_BEGIN) {
                waitForRoundToBegin();
                gamePhaseState = GamePhaseState.IN_ROUND;
            }

            else if (gamePhaseState == GamePhaseState.IN_ROUND) {
                gameOne.resetGameState("" + GAME_ONE);
                gameTwo.resetGameState("" + GAME_TWO);

                playRounds();

                if(currentRoundId.equals(currentRounds))
                    gamePhaseState = GamePhaseState.WAITING_FOR_CHALLENGE;
                else
                    gamePhaseState = GamePhaseState.WAITING_FOR_ROUND_TO_BEGIN;
            }
        }
    }

    private void waitForWelcome() {
        String messageFromServer;

        while(true) {
            messageFromServer = getStringFromServer();
            if(messageFromServer.equals(WELCOME_MESSAGE))
                break;
        }
    }

    private void sendEnterMessage() {
        String messageToServer = ENTER_MESSAGE + " " + tournamentPassword;
        sendStringToServer(messageToServer);
    }

    private void waitForTwoShallEnterMessage() {
        String messageFromServer;

        while(true) {
            messageFromServer = getStringFromServer();
            if(messageFromServer.equals(TWO_SHALL_ENTER_MESSAGE))
                break;
        }
    }

    private void sendIAmMessage() {
        String messageToServer = I_AM_MESSAGE + " " + username + " " + password;
        sendStringToServer(messageToServer);
    }

    private void waitForTournamentToBeginWithPidMessage() {
        String messageFromServer;

        while(true) {
            messageFromServer = getStringFromServer();
            if(messageFromServer.contains(WAIT_FOR_TOURNAMENT_PID_MESSAGE))
                break;
        }

        ourPidFromServer = PlayerIdParser.getPlayerId(messageFromServer);
    }

    private void waitForNewChallengeMessage() {
        String messageFromServer;

        while(true) {
            messageFromServer = getStringFromServer();
            if(messageFromServer.contains(NEW_CHALLENGE_MESSAGE))
                break;
        }

        Debug.print("New challenge received", DebugLevel.INFO);
        currentChallengeId = ChallengeIdParser.getChallengeId(messageFromServer);
        currentRounds = RoundCountParser.getRoundCount(messageFromServer);
    }

    private void waitForRoundToBegin() {
        String messageFromServer;

        while(true) {
            messageFromServer = getStringFromServer();
            if(messageFromServer.contains(BEGIN_ROUND_MESSAGE))
                break;
        }

        currentRoundId = RoundIdParser.getRoundId(messageFromServer);
    }

    private void playRounds() {

        boolean isEndOfRound = false;
        int forfeitCount = 0;
        Debug.print("Beginning rounds", DebugLevel.INFO);

        while (!isEndOfRound && forfeitCount < 2) {

            String messageFromServer = getStringFromServer();

            if(isGameOver(messageFromServer) || isLostMessage(messageFromServer))
                continue;

            if(isEndOfRound(messageFromServer))
                isEndOfRound = true;

            if(isForfeit(messageFromServer)) {
                forfeitCount++;
                continue;
            }

            if(isRequestForOurPlay(messageFromServer)) {
                performOwnPlay(messageFromServer);
            }

            else if (isPlayComingFromServer(messageFromServer)) {
                if(isNotOwnPlayBeingReportedBack(messageFromServer))
                    performOtherPlayersPlay(messageFromServer);
            }
        }
    }

    private void performOtherPlayersPlay(String messageFromServer) {

        String gameId = GameIdParser.getGameIdForOtherPlayersMove(messageFromServer);
        String play = OtherPlayersPlayParser.getPlay(messageFromServer);

        int localGameId = getRightGameId(gameId);

        if (localGameId == GAME_ONE)
            gameOne.enforceOpponentPlay(play);

        else if(localGameId == GAME_TWO)
            gameTwo.enforceOpponentPlay(play);

        else
            throw new InvalidParameterException("What the heck happened that we got more than two game IDs?");

    }

    private void performOwnPlay(String messageFromServer) {

        String gameId = GameIdParser.getGameIdForOwnMove(messageFromServer);
        String moveNumber = MoveNumberParser.getMoveNumber(messageFromServer);
        String tileInfo = TileInformationParser.getTile(messageFromServer);

        int localGameId = getRightGameId(gameId);

        if(localGameId == GAME_ONE) {
            gameOne.haveSteveDoPlay(gameId, moveNumber, tileInfo);
        }

        else if(localGameId == GAME_TWO) {
            gameTwo.haveSteveDoPlay(gameId, moveNumber, tileInfo);
        }

        else
            throw new InvalidParameterException("What the heck happened that we got more than two game IDs?");
    }

    private int getRightGameId(String gameId) {
        if(!gameIdMap.containsKey(gameId))
            gameIdMap.put(gameId, gameIdSeed++);

        return gameIdMap.get(gameId);
    }

    private String getStringFromServer() {
        String messageFromServer = null;

        try {
            messageFromServer = gameClient.receiveData();
        }

        catch(IOException e){
            e.printStackTrace();
        }

        Debug.print("FROM SERVER: " + messageFromServer, DebugLevel.INFO);
        return messageFromServer;
    }

    private void sendStringToServer(String toServer) {
        try {
            gameClient.sendDataToServer(toServer);
            Debug.print("TO SERVER: " + toServer, DebugLevel.INFO);
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    private boolean isPlayComingFromServer(String messageFromServer) {
        return messageFromServer.contains(OTHER_PLAYERS_PLAY_MESSAGE);
    }

    private boolean isRequestForOurPlay(String messageFromServer) {
        return messageFromServer.contains(REQUEST_FOR_OUR_PLAY_MESSAGE);
    }

    private boolean isForfeit(String messageFromServer) {
        if(messageFromServer.contains(FORFEIT_MESSAGE))
            return true;
        else
            return false;
    }

    private boolean isEndOfRound(String messageFromServer) {
        return messageFromServer.contains(END_OF_ROUND_MESSAGE);
    }

    private boolean isNotOwnPlayBeingReportedBack(String messageFromServer) {
        String pidFromMove = PlayerIdParserFromServerMove.getPlayerId(messageFromServer);
        return !pidFromMove.equals(ourPidFromServer);
    }

    private boolean isGameOver(String message) {
        return message.contains(GAME_OVER_MESSAGE);
    }

    private boolean isLostMessage(String message) {
        return message.contains(LOST_MESSAGE);
    }
}
