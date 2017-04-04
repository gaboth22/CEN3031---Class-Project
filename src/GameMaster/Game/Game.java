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

    private long timeAtStartOfTurn;
    private final static long TURN_LENGTH = 1500;
    private final static long SAFETY_NET = 500;

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
                    timeAtStartOfTurn = System.currentTimeMillis();

                    long timeDue = timeAtStartOfTurn + TURN_LENGTH - SAFETY_NET;
                    TilePlacementPhase tilePlacementPhase = generateTilePhase(getCurrentGameState(), timeDue);
                    BuildPhase buildPhase = generateBuildPhase(getCurrentGameState(), timeDue);

                    playInfoForSteve = null;
                    sendStevePlayToGameMaster(buildPhase, tilePlacementPhase);
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

    private void sendStevePlayToGameMaster(BuildPhase steveBuildPhase, TilePlacementPhase steveTilePlacementPhase) {
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

    private TilePlacementPhase generateTilePhase(GameBoardState gameState, long timeDue) {

        giveSteveTileToPlace();

        TilePlacementPhase optimalMove = searchForOptimalMove(gameState, timeDue);

        if(optimalMove != null) {
            return optimalMove;
        }

        return getSimpleMove(gameState);
    }

    private void giveSteveTileToPlace() {
        //TODO: convert server string to Tile
        TriHexTileStructure triHex = null;
        steve.setTileToPlace(triHex);
    }

    private TilePlacementPhase searchForOptimalMove(GameBoardState state, long timeDue) {
        while(System.currentTimeMillis() < timeDue) {
            try {
                TilePlacementPhase tilePhase = steve.generateTilePlay(state);
                gameBoard.doTilePlacementPhase(tilePhase);
                return tilePhase;
            }
            catch(Exception ex) {
            }
        } //on timeout get safe play
        return null;
    }

    private TilePlacementPhase getSimpleMove(GameBoardState state) {
        TilePlacementPhase simpleMove = steve.getSafeTilePhase(gameState);
        try{
            gameBoard.doTilePlacementPhase(simpleMove);
        }
        catch(Exception ex) {
            //TODO: forfeit?
        }
        return simpleMove;
    }

    private BuildPhase generateBuildPhase(GameBoardState gameState, long timeDue) {
        BuildPhase optimalMove = searchForOptimalBuild(gameState, timeDue);

        if(optimalMove != null) {
            return optimalMove;
        }

        return getSimpleBuild(gameState);
    }

    private BuildPhase searchForOptimalBuild(GameBoardState gameState, long timeDue) {
        while(System.currentTimeMillis() < timeDue) {
            try {
                BuildPhase buildPhase = steve.generateBuildPlay(gameState);
                gameBoard.doBuildPhase(buildPhase);
                return buildPhase;
            }
            catch(Exception ex) {
            }
        } //on timeout get safe play
        return null;
    }

    private BuildPhase getSimpleBuild(GameBoardState state) {
        BuildPhase simpleBuild = steve.getSafeBuildPhase(gameState);
        try{
            gameBoard.doBuildPhase(simpleBuild);
        }
        catch(Exception ex) {
            //TODO: forfeit?
        }
        return simpleBuild;
    }
}
