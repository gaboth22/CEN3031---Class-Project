package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.BuildPhase.*;
import Player.PlayerID;
import Steve.*;

public interface PlayGenerator {
    TilePlacementPhase generateSafeTilePlay(GameBoardState gameBoardState,
                                             PlayerID activePlayer,
                                             BiHexTileStructure tileToPlace);

    BuildPhase generateSafeBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer);

    TilePlacementPhase generateTilePlay(GameBoardState gameBoardState,
                                        PlayerID activePlayer,
                                        BiHexTileStructure tileToPlace);

    BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer);
}
