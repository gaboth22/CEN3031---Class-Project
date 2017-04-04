package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.BuildPhase.*;
import Player.PlayerID;
import Steve.*;

public interface PlayGenerator {
    TilePlacementPhase generateTilePlay(GameBoardState gameBoardState, PlayerID activePlayer, TriHexTileStructure tileToPlace);
    BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer);
}
