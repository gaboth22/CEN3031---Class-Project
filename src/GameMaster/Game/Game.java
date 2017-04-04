package GameMaster.Game;

import GUI.GuiThread.GuiThread;
import GameBoard.*;
import GameBoard.GameBoardStateBuilder.*;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Receiver.Receiver;
import Sender.Sender;
import Sender.SenderData.SenderData;
import Steve.*;
import Steve.PlayGeneration.PlayGenerator;

public class Game extends Thread {

    private Steve steve;
    private PlayGenerator playGenerator;
    private GameBoard gameBoard;
    private GuiThread guiThread;
    private PlayerID activePlayer;
    private Sender stevePlaySender;
    private GameBoardStateBuilder stateBuilder;

    private GameBoardState gameState;

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
        initializeStateBuilder();

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
    }

    private void initializeStateBuilder() {
        stateBuilder = new GameBoardStateBuilderImpl();
    }

    private void initializeGuiThread() {
        guiThread = new GuiThread();
    }

    public void resetGameState() {
        steve = null;
        gameBoard = null;

        initializeSteve(playGenerator);
        initializeGameBoard();
        initializeStateBuilder();
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

            if(playInfoForSteve != null) {
                synchronized (this) {
                    //TODO: parse gameId, move number, tile info, and request that Steve do a tile placement phase
                    TilePlacementPhase tilePlacementPhase = generateTilePhase(getCurrentGameState());
                    BuildPhase buildPhase = generateBuildPhase(getCurrentGameState());

                    playInfoForSteve = null;
                    sendStevePlayToGameMaster();
                }
            }

            if(opponentPlay != null) {
                synchronized (this) {
                    doOpponentTilePlacementPhase();
                    doOpponentPiecePlacementPhase();
                }
            }
        }
    }

    private void sendStevePlayToGameMaster() {
        String formattedMessageForServer = StevePlayParser.parse(steveBuildPhase, steveTilePlacementPhase);
        stevePlaySender.publish(new SenderData() {
            @Override
            public Object getData() {
                return formattedMessageForServer;
            }
        });
    }

    private void doOpponentPiecePlacementPhase() {
        BuildPhase build = ServerPlayParser.getServerPiecePlacement(opponentPlay);
        try{
            gameBoard.doBuildPhase(build);
        }
        catch(Exception ex) {
            //TODO: log error here
        }
        opponentPlay = null;
    }

    private void doOpponentTilePlacementPhase() {
        TilePlacementPhase placement = ServerPlayParser.getServerTilePlacement(opponentPlay);
        try{
            gameBoard.doTilePlacementPhase(placement);
        }
        catch(Exception ex) {
            //TODO: log error here
        }
    }

    private GameBoardState getCurrentGameState() {
        return stateBuilder.buildGameBoardState(gameBoard);
    }

    private TilePlacementPhase generateTilePhase(GameBoardState gameState) {
        //TODO: validate play

        return playGenerator.generateTilePlay(gameState, activePlayer, );
    }

    private BuildPhase generateBuildPhase(GameBoardState gameState) {
        //TODO: validate play

        return playGenerator.generateBuildPlay(gameState, activePlayer);
    }
}
