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

    private GameBoardState gameState = null;
    private Player currentPlayer = null;
    private Map<Location,Hexagon> hexes = null;
    private GamePieceMap pieces = null;
    private List<Settlement> playerSettlements = null;

    public ProfitablePlayGeneration(GameBoardState gameBoardState, PlayerID playerNum) {
        gameState = gameBoardState;
        if (playerNum == PlayerID.PLAYER_ONE) {
            currentPlayer = gameState.getPlayerOne();
        }
        else {
            currentPlayer = gameState.getPlayerTwo();
        }
        hexes = gameBoardState.getPlacedHexagons();
        pieces = gameState.getGamePieceMap();
        playerSettlements = currentPlayer.getListOfSettlements();
    }

    public BuildPhase chooseBuildAction(Player player) {
        BuildPhase nextMove = null;
        if (player.getTigerCount() > 0) {
            nextMove = TigerLocationHelper.pickTigerLocation(hexes, playerSettlements, pieces);
            if (nextMove != null) {
                return nextMove;
            }
        }
        if (player.getTotoroCount() > 0) {
            //nextMove = TotoroLocationHelper.pickTotoroLocation(hexes, playerSettlements, pieces, currentPlayer.getID());
        }
        if (ExpansionHelper.canExpand(currentPlayer)) {
            //nextMove = ExpansionHelper.expansionChoice(hexes, currentPlayer, pieces);
        }
        if (currentPlayer.getVillagerCount() > 0) {
            nextMove = FoundSettlementHelper.pickLocationForNewSettlement(gameState, currentPlayer.getID());
            if (nextMove != null) {
                return nextMove;
            }
        }
        return null;
    }
}
