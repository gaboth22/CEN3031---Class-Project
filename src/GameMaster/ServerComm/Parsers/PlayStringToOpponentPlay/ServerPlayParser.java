package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameBoard.GameBoardState;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Tile.Tile.Tile;

public class ServerPlayParser {
    public static TilePlacementPhase getServerTilePlacement(String playFromServer, PlayerID playerID) {

        Tile tileToPlace = StringPlayToTileParser.getTileFromPlay(playFromServer);
        TilePlacementPhase opponentPlacementPhase = new TilePlacementPhase(playerID, tileToPlace);
        return opponentPlacementPhase;
    }

    public static BuildPhase getServerPiecePlacement(String playFromServer, GameBoardState gameBoardState, PlayerID playerID) {
        //TODO: parse and return valid build phase
        return null;
    }
}
