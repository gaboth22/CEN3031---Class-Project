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

    private TriHexTileStructure tileToPlace;

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

    public void setTileToPlace(TriHexTileStructure tileToPlace) {
        this.tileToPlace = tileToPlace;
    }

    public TilePlacementPhase generateTilePlay(GameBoardState state) {
        //TODO: generate A Tile Play

        return null;
    }

    public TilePlacementPhase getSafeTilePhase(GameBoardState state) {
        //TODO: generate A Safe Tile Play

        return null;
    }

    public BuildPhase generateBuildPlay(GameBoardState state) {
        //TODO: generate A Build Play

        return null;
    }

    public BuildPhase getSafeBuildPhase(GameBoardState state) {
        //TODO: generate A Safe Build Play

        return null;
    }

    public GameBoardState getCurrentGameBoardState() {
        return currentGameBoardState;
    }

    public Object getLastValidPlay() {
        return lastValidPlay;
    }
}