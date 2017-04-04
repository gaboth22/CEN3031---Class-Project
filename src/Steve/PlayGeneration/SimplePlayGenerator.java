package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Steve.TriHexTileStructure;

public class SimplePlayGenerator implements PlayGenerator {

    @Override
    public TilePlacementPhase generateTilePlay(GameBoardState gameBoardState, PlayerID activePlayer, TriHexTileStructure tileToPlace) {
        //TODO: generate TilePlacement

        return null;
    }

    @Override
    public BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) {
        //TODO: generate PiecePlacement

        return null;
    }
}
