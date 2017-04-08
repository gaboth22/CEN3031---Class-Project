package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.*;
import GamePieceMap.GamePieceMap;

import java.util.*;

public class ProfitablePlayGeneration {

    private GameBoardState gameState = null;
    private Player currentPlayer = null;
    private Map<Location,Hexagon> hexes = null;
    private GamePieceMap pieces = null;
    private List<Settlement> playerSettlements = null;

    public ProfitablePlayGeneration(GameBoardState gameBoardState, Player player) {
        gameState = gameBoardState;
        currentPlayer = player;
        hexes = gameBoardState.getPlacedHexagons();
        pieces = gameState.getGamePieceMap();
        playerSettlements = currentPlayer.getListOfSettlements();
    }

    public void chooseBuildAction(Player player) throws Exception {
        if (player.getTotoroCount() > 0) {
            TotoroLocationHelper.pickTotoroLocation(hexes, playerSettlements, pieces, player.getID());
        }
        if (player.getTigerCount() > 0) {
            TigerLocationHelper.pickTigerLocation(hexes, playerSettlements, pieces);
        }
        if (ExpansionHelper.canExpand(currentPlayer)) {
            ExpansionHelper.expansionChoice(hexes, currentPlayer, pieces);
        }
        if (currentPlayer.getVillagerCount() > 0) {
            //strategic villager placement?
        }
    }
}