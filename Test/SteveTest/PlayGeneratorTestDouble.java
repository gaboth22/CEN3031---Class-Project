package SteveTest;

import GameBoard.*;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Steve.PlayGeneration.PlayGenerator;
import Steve.StevePlayType;
import Steve.BiHexTileStructure;

public class PlayGeneratorTestDouble implements PlayGenerator {

    private Object play;
    private boolean wasPlayRequested;
    private StevePlayType requestedPlayType;

    public PlayGeneratorTestDouble() {
        wasPlayRequested = false;
    }

    public void setPlayToReturn(Object play) {
        this.play = play;
    }

    public boolean wasPlayRequested() {
        return wasPlayRequested;
    }

    public StevePlayType getRequestedPlayType() {
        return requestedPlayType;
    }

    @Override
    public TilePlacementPhase generateTilePlay(GameBoardState gameBoardState, PlayerID activePlayer, BiHexTileStructure tileToPlace) {
        return null;
    }

    @Override
    public BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) {
        return null;
    }

    @Override
    public TilePlacementPhase generateSafeTilePlay(GameBoardState gameBoardState,
                                            PlayerID activePlayer,
                                            BiHexTileStructure tileToPlace) {
        return null;
    }

    @Override
    public BuildPhase generateSafeBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) {
        return null;
    }
}
