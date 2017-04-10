package Steve.PlayGeneration.SmartTilePlacer;

import GameBoard.GameBoardState;
import GamePieceMap.GamePieceMap;
import Location.Location;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Tile.Tile.Tile;
import TileMap.Hexagon;
import java.util.*;

public class NukingTilePlacementPhaseMaker {
    private OppositePlayerGetter oppositePlayerGetter;
    private SizeFourOrGreaterSettlementListGetter sizeFourOrGreaterSettlementListGetter;
    private NukeableVolcanoAroundSettlementListGetter nukeableVolcanoAroundSettlementListGetter;
    private NukingTileMaker nukingTileMaker;
    private Map<Location, Hexagon> hexMap;
    private GameBoardState gameBoardState;
    private BiHexTileStructure terrains;
    private PlayerID activePlayer;
    private GamePieceMap pieceMap;

    public NukingTilePlacementPhaseMaker() {
        oppositePlayerGetter = new OppositePlayerGetter();
        sizeFourOrGreaterSettlementListGetter = new SizeFourOrGreaterSettlementListGetter();
        nukeableVolcanoAroundSettlementListGetter = new NukeableVolcanoAroundSettlementListGetter();
        nukingTileMaker = new NukingTileMaker();
    }

    public TilePlacementPhase getTilePlacement(
            GameBoardState gameBoardState,
            PlayerID activePlayer,
            BiHexTileStructure tileToPlace) {


        hexMap = gameBoardState.getPlacedHexagons();
        pieceMap = gameBoardState.getGamePieceMap();
        this.activePlayer = activePlayer;
        this.gameBoardState = gameBoardState;
        this.terrains = tileToPlace;
        Player otherPlayer = oppositePlayerGetter.getOtherPlayerFromGameBoardState(activePlayer, gameBoardState);
        List<Settlement> otherPlayerSettlements = otherPlayer.getListOfSettlements();
        List<Settlement> ofSizeFour = sizeFourOrGreaterSettlementListGetter.getSettlementsOfSizeFourAndGreaterOnly(otherPlayerSettlements);

        if(ofSizeFour.size() == 0)
            return null;

        TilePlacementPhase nukingTilePlacement = getNukingTilePlacement(ofSizeFour);
        if(nukingTilePlacement == null)
            return null;

        nukingTilePlacement.setTilePlacementType(TilePlacementType.NUKE);

        return nukingTilePlacement;
    }

    private TilePlacementPhase getNukingTilePlacement(List<Settlement> ofSizeFour) {
        Settlement nukeableSettlement = null;
        Location ofVolcano = null;

        for(Settlement s : ofSizeFour) {

            List<Location> nukeableVolcanoesAroundSettlement =
                    nukeableVolcanoAroundSettlementListGetter.getList(s, hexMap);

            if(nukeableVolcanoesAroundSettlement.size() > 0) {
                nukeableSettlement = s;
                ofVolcano = nukeableVolcanoesAroundSettlement.get(0);
                break;
            }
        }

        if(nukeableSettlement == null)
            return null;

        Tile nukingTile = nukingTileMaker.makeTile(
                gameBoardState,
                hexMap,
                pieceMap,
                nukeableSettlement,
                ofVolcano,
                terrains,
                activePlayer);

        return new TilePlacementPhase(activePlayer, nukingTile);
    }
}
