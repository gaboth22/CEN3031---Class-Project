package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.*;
import GamePieceMap.GamePieceMap;
import Play.BuildPhase.BuildPhase;

import java.util.*;

public class ProfitablePlayGeneration {

    static public BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) throws Exception{
        GameBoardState gameState = gameBoardState;
        Player currentPlayer;
        if (activePlayer == PlayerID.PLAYER_ONE) {
            currentPlayer = gameState.getPlayerOne();
        }
        else {
            currentPlayer = gameState.getPlayerTwo();
        }
        Map <Location, Hexagon> hexes = gameBoardState.getPlacedHexagons();
        GamePieceMap pieces = gameState.getGamePieceMap();
        List<Settlement> playerSettlements = currentPlayer.getListOfSettlements();
        BuildPhase nextMove = null;
        if (currentPlayer.getTigerCount() > 0) {
            nextMove = TigerLocationHelper.pickTigerLocation(hexes, playerSettlements, pieces);
            if (nextMove != null) {
                return nextMove;
            }
        }
        if (currentPlayer.getTotoroCount() > 0) {
            nextMove = TotoroLocationHelper.pickTotoroLocation(hexes, playerSettlements, pieces, activePlayer);
            if (nextMove != null) {
                return nextMove;
            }
        }
        if (ExpansionHelper.canExpand(currentPlayer)) {
            //nextMove = ExpansionHelper.expansionChoice(hexes, currentPlayer, pieces);
        }
        if (currentPlayer.getVillagerCount() > 0) {
            nextMove = FoundSettlementHelper.pickLocationForNewSettlement(gameState, activePlayer);
            if (nextMove != null) {
                return nextMove;
            }
        }
        return null;
    }
}