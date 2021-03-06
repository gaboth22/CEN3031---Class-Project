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

        Tile nukingTile = null;

        for(Settlement settlementOfSizeFourOrGreater : ofSizeFour) {

            List<Location> nukeableVolcanoesAroundSettlement =
                    nukeableVolcanoAroundSettlementListGetter.getList(settlementOfSizeFourOrGreater, hexMap);

            if(nukeableVolcanoesAroundSettlement.size() > 0) {

                for(Location volcano : nukeableVolcanoesAroundSettlement) {

                    nukingTile = nukingTileMaker.makeTile(
                            gameBoardState,
                            hexMap,
                            pieceMap,
                            settlementOfSizeFourOrGreater,
                            volcano,
                            terrains,
                            activePlayer);

                    if(nukingTile == null)
                        continue;
                    else
                        break;
                }

                if(nukingTile != null)
                    break;
            }
        }

        if(nukingTile == null)
            return null;

        return new TilePlacementPhase(activePlayer, nukingTile);
    }
}
