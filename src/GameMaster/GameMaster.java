package GameMaster;

import GameMaster.Game.Game;
import GameMaster.ServerComm.Parsers.*;
import GameMaster.ServerComm.ServerClient;

import static GameMaster.ServerComm.ServerMessages.*;
import java.io.IOException;

public class GameMaster {

    private Game gameOne;
    private Game gameTwo;
    private ServerClient serverClient;
    private GamePhaseState gamePhaseState;
    private int ourPidFromServer;
    private int roundCount;

    public GameMaster(ServerClient serverClient, Game gameOne, Game gameTwo) {

        this.serverClient = serverClient;
        this.gameOne = gameOne;
        this.gameTwo = gameTwo;
        gamePhaseState = GamePhaseState.SERVER_REGISTRATION;
    }

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

                waitRoundCount();
                gamePhaseState = GamePhaseState.IN_ROUND;
            }
            else if (gamePhaseState == GamePhaseState.IN_ROUND) {
                playRounds();
                gamePhaseState = GamePhaseState.WAITING_FOR_CHALLENGE;
            }
        }
    }

    private void registerWithServer() {
        waitToReceiveWelcomeMessage();
    }

    private void waitToReceiveWelcomeMessage() {

        String messageFromServer = getStringFromServer();

        while (messageFromServer != welcomeMessage)
            messageFromServer = getStringFromServer();
    }

    private void waitToRetrievePlayerIdMessage() {

        String messageFromServer = getStringFromServer();

        while (!messageFromServer.contains(pidMessage))
            messageFromServer = getStringFromServer();

        ourPidFromServer = PlayerIdParser.getPlayerId(messageFromServer);
    }

    private void waitRoundCount() {

        String messageFromServer = getStringFromServer();

        while (!messageFromServer.contains(roundCountMessage))
            messageFromServer = getStringFromServer();

        roundCount = RoundCountParser.getRoundCount(messageFromServer);
    }

    private void playRounds() {

        for (int i = 0; i < roundCount; i++) {
            boolean isEndOfRound = false;

            while (!isEndOfRound) {

                String messageFromServer = getStringFromServer();

                if (isPlayComingFromServer(messageFromServer)) {
                    if(isNotOwnPlayBeingReportedBack(messageFromServer))
                        performOtherPlayersPlay(messageFromServer);
                }

                else if(isRequestForOurPlay(messageFromServer)) {
                    performOwnPlay(messageFromServer);
                }

                else if(isEndOfRound(messageFromServer))
                    isEndOfRound = true;
            }
        }
    }

    private void performOtherPlayersPlay(String messageFromServer) {

        String gameId = GameIdParser.getGameIdForOtherPlayersMove(messageFromServer);
        String play = OtherPlayersPlayParser.getPlay(messageFromServer);

        if (gameId == "A") {
            gameOne.enforceOtherPlayersPlay(play);
        }

        else
            gameTwo.enforceOtherPlayersPlay(play);

    }

    private void performOwnPlay(String messageFromServer) {
        String gameId = GameIdParser.getGameIdForOwnMove(messageFromServer);
        String moveNumber = MoveNumberParser.getMoveNumber(messageFromServer);
        String tileInfo = TileInformationParser.getTile(messageFromServer);

        if(gameId == "A") {
            gameOne.haveSteveDoTile(tileInfo);
            gameOne.haveSteveDoPiece();
            String moveReadyToSendToServer = OwnMoveFormatter.getFormattedMove(
                    gameOne.getStevesPlay(),
                    gameId,
                    moveNumber);

            sendStringToServer(moveReadyToSendToServer);
        }
        else {
            gameTwo.haveSteveDoTile(tileInfo);
            gameTwo.haveSteveDoPiece();
            String moveReadyToSendToServer = OwnMoveFormatter.getFormattedMove(
                    gameTwo.getStevesPlay(),
                    gameId,
                    moveNumber);

            sendStringToServer(moveReadyToSendToServer);
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
        return messageFromServer.contains(otherPlayersPlayMessage);
    }

    private boolean isRequestForOurPlay(String messageFromServer) {
        return messageFromServer.contains(requestForOurPlayMessage);
    }

    private boolean isForfeit(String messageFromServer) {
        return messageFromServer.contains(forfeitMessage);
    }

    private boolean isEndOfRound(String messageFromServer) {
        return messageFromServer.contains(endOfRoundMessage);
    }

    private boolean isNotOwnPlayBeingReportedBack(String messageFromServer) {
        int pidFromMove = PlayerIdParserFromMove.getPlayerId(messageFromServer);
        return pidFromMove != ourPidFromServer;
    }
}
