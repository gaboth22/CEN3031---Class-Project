package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import TileMap.*;
import GamePieceMap.GamePieceMap;
import Play.BuildPhase.BuildPhase;

import java.util.*;

public class ProfitablePlayGeneration implements PlayGenerator {

    private GameBoardState gameState = null;
    private Player currentPlayer = null;
    private Map<Location,Hexagon> hexes = null;
    private GamePieceMap pieces = null;
    private List<Settlement> playerSettlements = null;

    private BuildPhase buildPhase = null;
    private TilePlacementPhase tilePlacementPhase = null;

    private SimplePlayGenerator simplePlayGenerator;

    public ProfitablePlayGeneration(GameBoardState gameBoardState, Player player) {
        gameState = gameBoardState;
        currentPlayer = player;
        hexes = gameBoardState.getPlacedHexagons();
        pieces = gameState.getGamePieceMap();
        playerSettlements = currentPlayer.getListOfSettlements();
        simplePlayGenerator = new SimplePlayGenerator();
    }

    @Override
    public BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) {

        if (currentPlayer.getTotoroCount() > 0) {
            buildPhase = TotoroLocationHelper.pickTotoroLocation(hexes, playerSettlements, pieces, activePlayer);
            if (buildPhase != null) {
                return buildPhase;
            }
        }
        if (currentPlayer.getTigerCount() > 0) {
            buildPhase = TigerLocationHelper.pickTigerLocation(hexes, playerSettlements, pieces);
            if (buildPhase != null) {
                return buildPhase;
            }
        }

        if (ExpansionHelper.canExpand(currentPlayer)) {
            buildPhase = ExpansionHelper.expansionChoice(hexes, currentPlayer, pieces);
            if(buildPhase != null){
                return buildPhase;
            }
        }
        if (currentPlayer.getVillagerCount() > 0) {
            buildPhase = FoundSettlementHelper.pickLocationForNewSettlement(gameState, activePlayer);
            if (buildPhase != null) {
                return buildPhase;
            }
        }
        return null;
    }

    @Override
    public TilePlacementPhase generateTilePlay(
            GameBoardState gameBoardState,
            PlayerID playerID,
            BiHexTileStructure tileToPlace){

        //TODO: generate multiple tile placement phases and return most profitable
        return null;
    }


    @Override
    public BuildPhase generateSafeBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer){

        return simplePlayGenerator.generateSafeBuildPlay(gameBoardState, activePlayer);
    }

    @Override
    public TilePlacementPhase generateSafeTilePlay(
            GameBoardState gameBoardState,
            PlayerID activePlayer,
            BiHexTileStructure tileToPlace) {

        return simplePlayGenerator.generateSafeTilePlay(gameBoardState, activePlayer, tileToPlace);
    }
}