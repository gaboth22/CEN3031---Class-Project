package Steve.PlayGeneration.NukeTileSetter;

import GameBoard.GameBoardState;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Steve.BiHexTileStructure;
import Tile.Tile.Tile;

public class SettingTileForNextTurnTilePlacementPhaseMaker {

    private SettingNukeTileForNextTurnTileMaker tileMaker;

    public SettingTileForNextTurnTilePlacementPhaseMaker() {

        tileMaker= new SettingNukeTileForNextTurnTileMaker();
    }

    public TilePlacementPhase getTilePlacement(
            GameBoardState gameBoardState,
            PlayerID activePlayer,
            BiHexTileStructure tileToPlace) {

        Tile forPhase = tileMaker.getTile(gameBoardState, activePlayer, tileToPlace);

        if(forPhase == null)
            return null;

        TilePlacementPhase toReturn = new TilePlacementPhase(activePlayer, forPhase);
        toReturn.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        return toReturn;
    }
}
