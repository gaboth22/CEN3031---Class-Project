package GameMaster.Game;

import GUI.GuiThread.GuiThread;
import GameBoard.*;
import GameBoard.Proxy.Proxy;
import Player.PlayerID;
import Receiver.Receiver;
import Sender.Sender;
import Steve.*;

public class Game extends Thread {

    private Steve steve;
    private PlayGenerator playGenerator;
    private GameBoard gameBoard;
    private GuiThread guiThread;
    private Proxy gameBoardProxy;
    private PlayerID activePlayer;
    private Sender stevePlaySender;

    private String[] playInfoForSteve;
    private String opponentPlay;

    public Game() {
    }

    public Game(PlayerID activePlayer, PlayGenerator playGenerator) {
        this.activePlayer = activePlayer;
        this.playGenerator = playGenerator;

        initializeGuiThread();
        initializeSteve(playGenerator);
        initializeGameBoard();
        tieSteveAndGameBoardTogether();

        playInfoForSteve = null;
        opponentPlay = null;

        stevePlaySender = new Sender();
    }

    private void initializeSteve(PlayGenerator playGenerator) {
        steve = new Steve();
        steve.playAs(activePlayer);
        steve.setPlayGenerator(playGenerator);
    }

    private void initializeGameBoard() {
        gameBoard = new GameBoardImpl();
        gameBoardProxy = new Proxy(gameBoard);
    }

    private void tieSteveAndGameBoardTogether() {
        //TODO: establish link somehow
    }

    private void initializeGuiThread() {
        guiThread = new GuiThread();
    }

    public void resetGameState() {
        steve = null;
        gameBoard = null;
        gameBoardProxy = null;

        initializeSteve(playGenerator);
        initializeGameBoard();
        tieSteveAndGameBoardTogether();
    }

    public void setPlayReceiver(Receiver playReceiver) {
        stevePlaySender.subscribe(playReceiver);
    }

    public void runWithGui() {
        guiThread.start();
    }

    public void haveSteveDoPlay(String gameId, String moveNumber, String tileFromServer) {
        playInfoForSteve = new String[]{gameId, moveNumber, tileFromServer};
    }

    public void enforceOpponentPlay(String playFromServer) {
        opponentPlay = playFromServer;
    }

    @Override
    public void run() {
        while(true) {

            if(playInfoForSteve !=  null) {
                //TODO: parse gameId, move number, tile info, and request that Steve do a tile placement phase
                playInfoForSteve = null;
                //TODO: send the formatted string with the play through stevePlaySender
            }

            if(opponentPlay != null) {
                //TODO: do opponent play on game board
                opponentPlay = null;
            }
        }
    }
}
