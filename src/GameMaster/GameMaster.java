package GameMaster;

import GameMaster.Game.Game;
import GameMaster.ServerComm.Parsers.*;
import GameMaster.ServerComm.ServerClient;
import Receiver.Receiver;
import Sender.SenderData.SenderData;

import static GameMaster.ServerComm.ServerMessages.*;
import java.io.IOException;

public class GameMaster extends Thread {

    private Receiver gameOneStevePlayReceiver;
    private Receiver gameTwoStevePlayReceiver;
    private String gameOneStevePlay;
    private String gameTwoStevePlay;
    private Game gameOne;
    private Game gameTwo;
    private ServerClient serverClient;
    private GamePhaseState gamePhaseState;
    private String ourPidFromServer;
    private int roundCount;

    public GameMaster(ServerClient serverClient, Game gameOne, Game gameTwo) {
        ourPidFromServer = null;
        roundCount = 0;

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

        this.serverClient = serverClient;
        this.gameOne = gameOne;
        this.gameTwo = gameTwo;
        gamePhaseState = GamePhaseState.SERVER_REGISTRATION;

        this.gameOne.start();
        this.gameTwo.start();
    }

    public GamePhaseState getCurrentGamePhaseState() {
        return gamePhaseState;
    }

    public String getOurPidFromServer() {
        return ourPidFromServer;
    }

    public int getRoundCount() {
        return roundCount;
    }

    @Override
    public void run() {

        while(true) {

            if (gamePhaseState == GamePhaseState.SERVER_REGISTRATION) {

                registerWithServer();
                gamePhaseState = GamePhaseState.WAITING_FOR_CHALLENGE;
            }
            else if (gamePhaseState == GamePhaseState.WAITING_FOR_CHALLENGE) {

                waitToRetrievePlayerIdMessage();
                gamePhaseState = GamePhaseState.WAITING_FOR_ROUND_COUNT;
            }
            else if (gamePhaseState == GamePhaseState.WAITING_FOR_ROUND_COUNT) {

                waitForRoundCount();
                gamePhaseState = GamePhaseState.IN_ROUND;
            }
            else if (gamePhaseState == GamePhaseState.IN_ROUND) {
                gameOne.resetGameState();
                gameTwo.resetGameState();
                playRounds();
                gamePhaseState = GamePhaseState.WAITING_FOR_CHALLENGE;
            }
        }
    }

    private void registerWithServer() {
        waitToReceiveWelcomeMessage();
    }

    private void waitToReceiveWelcomeMessage() {

        while (true) {
            String messageFromServer = getStringFromServer();
            if(messageFromServer.equals(WELCOME_MESSAGE)) {
                break;
            }
        }
    }

    private void waitToRetrievePlayerIdMessage() {

        String messageFromServer;

        while (true) {
            messageFromServer = getStringFromServer();
            if(messageFromServer.contains(PID_MESSAGE))
                break;
        }

        ourPidFromServer = PlayerIdParser.getPlayerId(messageFromServer);
    }

    private void waitForRoundCount() {

        String messageFromServer;

        while (true) {
            messageFromServer = getStringFromServer();
            if(messageFromServer.contains(ROUND_COUNT_MESSAGE))
                break;
        }

        roundCount = RoundCountParser.getRoundCount(messageFromServer);
    }

    private void playRounds() {

        for (int i = 0; i < roundCount; i++) {
            boolean isEndOfRound = false;
            int forfeitCount = 0;

            while (!isEndOfRound && forfeitCount < 2) {

                String messageFromServer = getStringFromServer();

                if(isEndOfRound(messageFromServer))
                    isEndOfRound = true;

                if(isForfeit(messageFromServer))
                    forfeitCount++;

                if (isPlayComingFromServer(messageFromServer)) {
                    if(isNotOwnPlayBeingReportedBack(messageFromServer))
                        performOtherPlayersPlay(messageFromServer);
                }

                else if(isRequestForOurPlay(messageFromServer)) {
                    performOwnPlay(messageFromServer);
                }
            }
        }
    }

    private void performOtherPlayersPlay(String messageFromServer) {

        String gameId = GameIdParser.getGameIdForOtherPlayersMove(messageFromServer);
        String play = OtherPlayersPlayParser.getPlay(messageFromServer);

        if (gameId == "A")
            gameOne.enforceOpponentPlay(play);

        else
            gameTwo.enforceOpponentPlay(play);

    }

    private void performOwnPlay(String messageFromServer) {
        String gameId = GameIdParser.getGameIdForOwnMove(messageFromServer);
        String moveNumber = MoveNumberParser.getMoveNumber(messageFromServer);
        String tileInfo = TileInformationParser.getTile(messageFromServer);

        if(gameId == "A") {
            gameOne.haveSteveDoPlay(gameId, moveNumber, tileInfo);
        }
        else {
            gameTwo.haveSteveDoPlay(gameId, moveNumber, tileInfo);
        }
    }

    private String getStringFromServer() {
        String messageFromServer = null;

        try {
            messageFromServer = serverClient.receiveData();
        }

        catch(IOException e){
            e.printStackTrace();
        }

        return messageFromServer;
    }

    private void sendStringToServer(String toServer) {
        try {
            serverClient.sendDataToServer(toServer);
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
        return messageFromServer.contains(FORFEIT_MESSAGE);
    }

    private boolean isEndOfRound(String messageFromServer) {
        return messageFromServer.contains(END_OF_ROUND_MESSAGE);
    }

    private boolean isNotOwnPlayBeingReportedBack(String messageFromServer) {
        String pidFromMove = PlayerIdParserFromServerMove.getPlayerId(messageFromServer);
        return !pidFromMove.equals(ourPidFromServer);
    }
}
