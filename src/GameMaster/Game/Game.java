package GameMaster.Game;

import Debug.*;
import GUI.GuiThread.GuiThread;
import GUI.PhaseToGuiAdapter.PhaseToGuiAdapter;
import GameBoard.*;
import GameBoard.GameBoardStateBuilder.*;
import GameMaster.ServerComm.Parsers.ServerPlayParser;
import GameMaster.ServerComm.Parsers.StevePlayParser;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Receiver.Receiver;
import Sender.Sender;
import Sender.SenderData.SenderData;
import Steve.*;
import Steve.PlayGeneration.PlayGenerator;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;

import java.util.Map;

public class Game extends Thread {

    private Steve steve;
    private PlayGenerator playGenerator;
    private GameBoard gameBoard;
    private volatile GuiThread guiThread;
    private PlayerID activePlayer;
    private volatile Sender stevePlaySender;
    private GameBoardStateBuilder stateBuilder;
    private volatile String[] playInfoForSteve;
    private volatile String opponentPlay;
    private boolean runningGui;
    private PhaseToGuiAdapter adapter;
    private volatile String tilePlacementStr;
    private volatile String buildPhaseStr;

    public Game() {
    }

    public Game(PlayerID activePlayer, PlayGenerator playGenerator) {
        this.activePlayer = activePlayer;
        this.playGenerator = playGenerator;

        initializeSteve(playGenerator);
        initializeGameBoard();
        initializeStateBuilder();

        playInfoForSteve = null;
        opponentPlay = null;

        stevePlaySender = new Sender();
        runningGui = false;
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
        runningGui = true;
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
        initializeGuiThread();
        guiThread.start();
        String firstTile = "1 tile v l g r j 0 0 -1 1 1 0 1 -1 -1 0";
        guiThread.updateGui(firstTile);
        adapter = new PhaseToGuiAdapter();
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
                    GameBoardState currentGameState = getCurrentGameState();
                    BiHexTileStructure tileForSteveToPlace = BiHexTileStructureBuilderFromString.getBiHexFromString(playInfoForSteve[2]);
                    steve.setTileToPlace(tileForSteveToPlace);

                    TilePlacementPhase tilePlacementPhase = steve.generateTilePlay(currentGameState);
                    BuildPhase buildPhase = steve.generateBuildPlay(currentGameState);
                    if(tilePlacementPhase == null){
                        tilePlacementPhase = steve.getSafeTilePhase(currentGameState);
                    }
                    if(buildPhase == null){
                        buildPhase = steve.getSafeBuildPhase(currentGameState);
                    }

                    if(buildPhase != null) {

                        try {
                            gameBoard.doTilePlacementPhase(tilePlacementPhase);
                            gameBoard.doBuildPhase(buildPhase);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (runningGui) {

                            tilePlacementStr = adapter.adapt(tilePlacementPhase);
                            buildPhaseStr = adapter.adapt(buildPhase);

                            guiThread.updateGui(tilePlacementStr);
                            guiThread.updateGui(buildPhaseStr);
                        }
                    }

                    sendStevePlayToGameMaster(buildPhase, tilePlacementPhase, playInfoForSteve);
                    playInfoForSteve = null;
                }
            }

            if(opponentPlay != null) {
                synchronized (this) {
                    doOpponentTilePlacementPhase();
                    doOpponentPiecePlacementPhase();
                    opponentPlay = null;
                }
            }
        }
    }

    private void sendStevePlayToGameMaster(BuildPhase steveBuildPhase,
                                           TilePlacementPhase steveTilePlacementPhase,
                                           String[] serverStuff) {

        int GAME_ID_INDEX = 0;
        int MOVE_NUMBER_INDEX = 1;

        String formattedMessageForServer;

        if(steveBuildPhase == null) {

            formattedMessageForServer = StevePlayParser.parse(
                    steveBuildPhase,
                    steveTilePlacementPhase,
                    null,
                    serverStuff);
        }

        else if(steveBuildPhase.getBuildType() == BuildType.EXPAND) {
            /*
                I know this is ugly, but upon expansion, server needs to know terrain,
                didn't see any other way around doing this.
            */
            Map<Location, Hexagon> hexMap = gameBoard.getPlacedHexagons();
            Terrain expandedOn = hexMap.get(steveBuildPhase.getLocationToPlacePieceOn()).getTerrain();

            formattedMessageForServer = StevePlayParser.parse(
                    steveBuildPhase,
                    steveTilePlacementPhase,
                    expandedOn,
                    serverStuff);
        }

        else {
            formattedMessageForServer = StevePlayParser.parse(
                    steveBuildPhase,
                    steveTilePlacementPhase,
                    null,
                    serverStuff);
        }

        stevePlaySender.publish(new SenderData() {
            String gameInfo = "GAME " + serverStuff[GAME_ID_INDEX] + " MOVE " + serverStuff[MOVE_NUMBER_INDEX] + " ";

            @Override
            public Object getData() {
                return gameInfo + formattedMessageForServer;
            }
        });
    }

    private void doOpponentPiecePlacementPhase() {
        BuildPhase build = ServerPlayParser.getServerPiecePlacement(opponentPlay);
        try{
            gameBoard.serverDoBuildPhase(build);
        }
        catch(Exception ex) {
            Debug.print(ex.getMessage(), DebugLevel.ERROR);
        }
        opponentPlay = null;
    }

    private void doOpponentTilePlacementPhase() {
        TilePlacementPhase placement = ServerPlayParser.getServerTilePlacement(opponentPlay);
        try{
            gameBoard.serverDoTilePlacementPhase(placement);
        }
        catch(Exception ex) {
            Debug.print(ex.getMessage(), DebugLevel.ERROR);
        }
    }

    private GameBoardState getCurrentGameState() {
        return stateBuilder.buildGameBoardState(gameBoard);
    }
}
