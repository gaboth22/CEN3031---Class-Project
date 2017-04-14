package GameMaster.Game;

import Debug.Debug;
import Debug.DebugLevel;
import GUI.GuiThread.GuiThread;
import GUI.PhaseToGuiAdapter.PhaseToGuiAdapter;
import GameBoard.GameBoard;
import GameBoard.GameBoardImpl;
import GameBoard.GameBoardState;
import GameBoard.GameBoardStateBuilder.GameBoardStateBuilder;
import GameBoard.GameBoardStateBuilder.GameBoardStateBuilderImpl;
import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.ServerPlayParser;
import GameMaster.ServerComm.Parsers.StevePlayParser;
import GamePieceMap.GamePieceMap;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Receiver.Receiver;
import Sender.Sender;
import Sender.SenderData.SenderData;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.BiHexTileStructureBuilderFromString;
import Steve.PlayGeneration.PlayGenerator;
import Steve.Steve;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    private volatile ConcurrentLinkedQueue<String[]> receivedMessages;
    private String gameID;

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

        receivedMessages = new ConcurrentLinkedQueue<>();
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

    private void initializeGuiThread(boolean enableInput) {
        runningGui = true;
        guiThread = new GuiThread(enableInput);
    }

    public synchronized void resetGameState(String gameID) {
        Debug.print("RESETING GAME " + gameID, DebugLevel.INFO);

        steve = null;
        gameBoard = null;
        this.gameID = gameID;

        initializeSteve(playGenerator);
        initializeGameBoard();
        initializeStateBuilder();

        if(runningGui) {
            Debug.print("RESETING GUI IN GAME " + gameID, DebugLevel.INFO);
            guiThread.clearGui();
            String firstTile = "1 tile v l g r j 0 0 -1 1 1 0 1 -1 -1 0";
            guiThread.updateGui(firstTile);
        }
    }

    public void setPlayReceiver(Receiver playReceiver) {
        stevePlaySender.subscribe(playReceiver);
    }

    public void runWithGui(boolean enableInput) {
        initializeGuiThread(enableInput);
        guiThread.start();
        String firstTile = "1 tile v l g r j 0 0 -1 1 1 0 1 -1 -1 0";
        guiThread.updateGui(firstTile);
        adapter = new PhaseToGuiAdapter();
    }

    public synchronized void haveSteveDoPlay(String gameId, String moveNumber, String tileFromServer) {

        receivedMessages.add(new String[]{gameId, moveNumber, tileFromServer});
    }

    public synchronized void enforceOpponentPlay(String playFromServer) {
        receivedMessages.add(new String[]{playFromServer});
    }

    @Override
    public void run() {
        while(true) {

            if(!receivedMessages.isEmpty() && receivedMessages.peek().length == 3) {
                playInfoForSteve = receivedMessages.remove();

                GameBoardState currentGameState = getCurrentGameState();

                if(activePlayer == PlayerID.PLAYER_ONE){
                    Debug.print("Game " + playInfoForSteve[0] +  " | Totoros: " + currentGameState.getPlayerOne().getTotoroCount()
                            + " | Tigers: " + currentGameState.getPlayerOne().getTigerCount()
                            + " | Villagers: " + currentGameState.getPlayerOne().getVillagerCount(), DebugLevel.INFO);
                }
                else{
                    Debug.print("Game " + playInfoForSteve[0] + " | Totoros: " + currentGameState.getPlayerTwo().getTotoroCount()
                            + " | Tigers: " + currentGameState.getPlayerTwo().getTigerCount()
                            + " | Villagers: " + currentGameState.getPlayerTwo().getVillagerCount(), DebugLevel.INFO);
                }


                BiHexTileStructure tileForSteveToPlace = BiHexTileStructureBuilderFromString.getBiHexFromString(playInfoForSteve[2]);
                steve.setTileToPlace(tileForSteveToPlace);

                TilePlacementPhase tilePlacementPhase = steve.generateTilePlay(currentGameState);

                if(tilePlacementPhase == null) {
                    tilePlacementPhase = steve.getSafeTilePhase(currentGameState);
                }

                try {
                    gameBoard.doTilePlacementPhase(tilePlacementPhase);
                } catch (Exception e) {
                   e.printStackTrace();
                    try {
                        tilePlacementPhase = steve.getSafeTilePhase(currentGameState);
                        gameBoard.doTilePlacementPhase(tilePlacementPhase);
                    }
                    catch (Exception ex) {
                        e.printStackTrace();
                    }
                }

                currentGameState = getCurrentGameState();

                BuildPhase buildPhase = steve.generateBuildPlay(currentGameState);

                if(buildPhase == null) {
                    buildPhase = steve.getSafeBuildPhase(currentGameState);
                }

                if(buildPhase != null) {

                    try {
                        gameBoard.doBuildPhase(buildPhase);
                    } catch (Exception e) {
                        try {
                            buildPhase = steve.getSafeBuildPhase(currentGameState);
                            gameBoard.doBuildPhase(buildPhase);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            sendStevePlayToGameMaster(null, tilePlacementPhase, playInfoForSteve);

                            if(runningGui)
                                updateGuiWithTilePlacement(tilePlacementPhase);

                            playInfoForSteve = null;
                            continue;
                        }
                        Debug.print("Expected: " + e.getClass() + " " + e.getMessage(), DebugLevel.INFO);
                        e.printStackTrace();
                    }

                    if (runningGui) {
                        updateGuiWithTilePlacement(tilePlacementPhase);
                        updateGuiWithBuildPhase(buildPhase);
                    }
                }

                sendStevePlayToGameMaster(buildPhase, tilePlacementPhase, playInfoForSteve);
                playInfoForSteve = null;
            }

            else if(!receivedMessages.isEmpty() && receivedMessages.peek().length == 1) {
                opponentPlay = receivedMessages.remove()[0];
                doOpponentTilePlacementPhase();
                doOpponentPiecePlacementPhase();
                opponentPlay = null;
            }
        }
    }

    private void updateGuiWithTilePlacement(TilePlacementPhase tilePlacementPhase) {
        tilePlacementStr = adapter.adapt(tilePlacementPhase);
        guiThread.updateGui(tilePlacementStr);
    }

    private void updateGuiWithBuildPhase(BuildPhase bPhase) {
        buildPhaseStr = adapter.adapt(bPhase);
        guiThread.updateGui(buildPhaseStr);

        if(bPhase.getBuildType() == BuildType.EXPAND) {
            List<Settlement> playerSettlements;
            String playerAsString = null;

            if(bPhase.getPlayerID() == PlayerID.PLAYER_ONE) {
                playerAsString = "1";
                playerSettlements = gameBoard.getPlayerOneSettlements();
            }
            else {
                playerAsString = "2";
                playerSettlements = gameBoard.getPlayerTwoSettlements();
            }

            Location expandedOn = bPhase.getLocationToPlacePieceOn();

            Settlement set = null;

            for(Settlement s : playerSettlements) {
                if(s.locationIsInSettlement(expandedOn)) {
                    set = s;
                    break;
                }
            }

            Set<Location> locationsInSettlement = set.getSetOfLocationsInSettlement();
            GamePieceMap pieceMap = gameBoard.getGamePieceMap();

            for(Location loc : locationsInSettlement) {
                if(pieceMap.getPieceAtLocation(loc).getPieceType() != TypeOfPiece.TOTORO &&
                    pieceMap.getPieceAtLocation(loc).getPieceType() != TypeOfPiece.TIGER &&
                    !loc.equals(expandedOn)) {

                    String toGui = playerAsString + " piece vi " + loc.getX() + " " + loc.getY();
                    guiThread.updateGui(toGui);
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
        PlayerID opponentID = getOpponentId();
        GameBoardState gameBoardState = getCurrentGameState();
        BuildPhase build = ServerPlayParser.getServerPiecePlacement(opponentPlay, gameBoardState, opponentID);
        try {

            gameBoard.serverDoBuildPhase(build);
            if(runningGui)
               updateGuiWithBuildPhase(build);
        }
        catch(Exception ex) {
           Debug.print(ex.getMessage(), DebugLevel.ERROR);
        }
        opponentPlay = null;
    }

    private void doOpponentTilePlacementPhase() {
        PlayerID opponentID = getOpponentId();
        TilePlacementPhase placement = ServerPlayParser.getServerTilePlacement(opponentPlay, getCurrentGameState(), opponentID);
        try{
            gameBoard.serverDoTilePlacementPhase(placement);
            if(runningGui)
                updateGuiWithTilePlacement(placement);
        }
        catch(Exception ex) {
            Debug.print(ex.getMessage(), DebugLevel.ERROR);
        }
    }

    private PlayerID getOpponentId() {
        if(activePlayer == PlayerID.PLAYER_ONE) {
            return PlayerID.PLAYER_TWO;
        }
        else {
            return PlayerID.PLAYER_ONE;
        }
    }

    private GameBoardState getCurrentGameState() {
        return stateBuilder.buildGameBoardState(gameBoard);
    }
}
