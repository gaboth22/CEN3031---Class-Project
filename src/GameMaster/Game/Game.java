package GameMaster.Game;

import GUI.GuiThread.GuiThread;
import GameBoard.*;
import GameBoard.Proxy.Proxy;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Steve.*;

public class Game {

    private Steve steve;
    private GameBoard gameBoard;
    private GuiThread guiThread;
    private Proxy gameBoardProxy;
    private StevePlayRequester requester;
    private TilePlacementPhase steveLastTilePlacement;
    private BuildPhase steveLastPiecePlacement;
    private PlayerID activePlayer;

    public Game(PlayerID activePlayer) {
        this.activePlayer = activePlayer;
        guiThread = new GuiThread();
        steve = new Steve();
        steve.playAs(activePlayer);
        //TODO:
        //PlayGenerator playGenerator = new PlayGenerator()
        //steve.setPlayGenerator(playGenerator);
        requester = new StevePlayRequester(steve);
        gameBoard = new GameBoardImpl();
        gameBoardProxy = new Proxy(gameBoard);
        SteveGameBoardAdapter adapter = new SteveGameBoardAdapter();
        adapter.adapt(steve, gameBoardProxy);
    }

    public void resetGameState() {
        steve = new Steve();
        steve.playAs(activePlayer);
        //TODO:
        //PlayGenerator playGenerator = new PlayGenerator()
        //steve.setPlayGenerator(playGenerator);
        requester = new StevePlayRequester(steve);
        gameBoard = new GameBoardImpl();
        gameBoardProxy = new Proxy(gameBoard);
        SteveGameBoardAdapter adapter = new SteveGameBoardAdapter();
        adapter.adapt(steve, gameBoardProxy);
    }

    public void runWithGui() {
        guiThread.start();
    }

    public void haveSteveDoTile(String tileFromServer) {
        requester.requestTilePlacement(tileFromServer);
        steveLastTilePlacement = (TilePlacementPhase) steve.getLastValidPlay();
    }

    public void haveSteveDoPiece() {
        requester.requestBuild();
        steveLastPiecePlacement = (BuildPhase) steve.getLastValidPlay();
    }

    public String getStevesPlay() {
        return StevePlayParser.parse(steveLastPiecePlacement, steveLastTilePlacement);
    }

    public void enforceOtherPlayersPlay(String playFromServer) {
        TilePlacementPhase  tpPhase = ServerPlayParser.getServerTilePlacement(playFromServer);
        BuildPhase bPhase = ServerPlayParser.getServerPiecePlacement(playFromServer);
        try {
            gameBoard.doTilePlacementPhase(tpPhase);
            gameBoard.doBuildPhase(bPhase);
        }
        catch(Exception e) {
        }
    }
}
