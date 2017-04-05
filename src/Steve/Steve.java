package Steve;

import GameBoard.GameBoardState;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Steve.PlayGeneration.PlayGenerator;

public class Steve {

    private PlayerID playingAs;

    private PlayGenerator playGenerator;

    private GameBoardState currentGameBoardState;
    private StevePlayType requestedPlayType;

    private BiHexTileStructure tileToPlace;

    private Object lastValidPlay;

    public Steve() {

        currentGameBoardState = null;
        lastValidPlay = null;

    }

    public void playAs(PlayerID player) {
        playingAs = player;
    }

    public void setPlayGenerator(PlayGenerator generator) {
        this.playGenerator = generator;
    }

    public void setTileToPlace(BiHexTileStructure tileToPlace) {
        this.tileToPlace = tileToPlace;
    }

    public TilePlacementPhase generateTilePlay(GameBoardState state) {
        //TODO: generate a tile play
        return null;
    }

    public TilePlacementPhase getSafeTilePhase(GameBoardState state) {
        return playGenerator.generateSafeTilePlay(state, playingAs, tileToPlace);
    }

    public BuildPhase generateBuildPlay(GameBoardState state) {
        //TODO: generate A Build Play
        return null;
    }

    public BuildPhase getSafeBuildPhase(GameBoardState state) {
        return playGenerator.generateSafeBuildPlay(state, playingAs);
    }

    public GameBoardState getCurrentGameBoardState() {
        return currentGameBoardState;
    }

    public Object getLastValidPlay() {
        return lastValidPlay;
    }
}