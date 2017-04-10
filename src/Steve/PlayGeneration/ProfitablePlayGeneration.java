package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.PlayGeneration.SmartTilePlacer.LevelOneSafeFoundHelper;
import Steve.PlayGeneration.SmartTilePlacer.NukingTilePlacementPhaseMaker;
import TileMap.*;
import GamePieceMap.GamePieceMap;
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

    private NukingTilePlacementPhaseMaker nukeMaker;

    public ProfitablePlayGeneration() {

        simplePlayGenerator = new SimplePlayGenerator();
        nukeMaker = new NukingTilePlacementPhaseMaker();
    }

    @Override
    public BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) {
        hexes = gameBoardState.getPlacedHexagons();

        gameState = gameBoardState;

        if(activePlayer == PlayerID.PLAYER_ONE) {
            currentPlayer = gameBoardState.getPlayerOne();
            playerSettlements = gameBoardState.getPlayerOne().getListOfSettlements();
        }

        else {
            currentPlayer = gameBoardState.getPlayerTwo();
            playerSettlements = gameBoardState.getPlayerTwo().getListOfSettlements();
        }

        pieces = gameBoardState.getGamePieceMap();


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

        buildPhase = StrategicSettlementExpansion.buildAdjacentToLargestSettlement(gameState, currentPlayer);
        if(buildPhase != null){
            return buildPhase;
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

            if(playerID == PlayerID.PLAYER_ONE)
                currentPlayer = gameBoardState.getPlayerOne();
            else
                currentPlayer = gameBoardState.getPlayerTwo();

            tilePlacementPhase = nukeMaker.getTilePlacement(gameBoardState, playerID, tileToPlace);

            if(tilePlacementPhase == null)
                tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, currentPlayer, tileToPlace);

        return tilePlacementPhase;
    }

    @Override
    public BuildPhase generateSafeBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer){
        if(activePlayer == PlayerID.PLAYER_ONE)
            currentPlayer = gameBoardState.getPlayerOne();
        else
            currentPlayer = gameBoardState.getPlayerTwo();

        return LevelOneSafeFoundHelper.LevelOneSafeFound(hexes, currentPlayer, pieces);
    }

    @Override
    public TilePlacementPhase generateSafeTilePlay(
            GameBoardState gameBoardState,
            PlayerID activePlayer,
            BiHexTileStructure tileToPlace) {

        if(activePlayer == PlayerID.PLAYER_ONE)
            currentPlayer = gameBoardState.getPlayerOne();
        else
            currentPlayer = gameBoardState.getPlayerTwo();

        return simplePlayGenerator.generateSafeTilePlay(gameBoardState, activePlayer, tileToPlace);
    }
}