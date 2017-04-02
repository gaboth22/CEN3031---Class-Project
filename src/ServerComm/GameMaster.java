package ServerComm;

import Game.Game;

import java.io.IOException;

public class GameMaster {
    private Game gameOne;
    private Game gameTwo;
    private ServerClient serverClient;
    private GamePhaseState gamePhaseState;
    private int pidFromServer;
    private String welcomeMessage = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!";
    private String pidMessage = "WAIT FOR THE TOURNAMENT TO BEGIN ";

    public GameMaster(ServerClient serverClient, Game gameOne, Game gameTwo) {
        this.serverClient = serverClient;
        this.gameOne = gameOne;
        this.gameTwo = gameTwo;
        gamePhaseState = GamePhaseState.SERVER_REGISTRATION;
    }

    public void run() {
        if(gamePhaseState == GamePhaseState.SERVER_REGISTRATION) {
            registerWithServer();
            gamePhaseState = GamePhaseState.WAITING_FOR_CHALLENGE;
        }
    }

    private void registerWithServer() {
        waitToReceiveWelcomeMessage();

    }

    private void waitToReceiveWelcomeMessage() {
        try {
            String messageFromServer = serverClient.receiveData();
            while (messageFromServer != welcomeMessage) {
                messageFromServer = serverClient.receiveData();
            }
        }
        catch(IOException e){
                e.printStackTrace();
        }
    }
}
