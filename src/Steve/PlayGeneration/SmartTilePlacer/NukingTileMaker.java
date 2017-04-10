package Steve.PlayGeneration.SmartTilePlacer;

import GameBoard.GameBoardState;
import GamePieceMap.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileMap.Hexagon;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NukingTileMaker {
    private Settlement nukeableSettlement;
    private Map<Location, Hexagon> hexMap;
    private GamePieceMap pieceMap;
    private List<Settlement> otherPlayerSettlements;

    public Tile makeTile(GameBoardState state,
                         Map<Location, Hexagon> hexMap,
                         GamePieceMap pieceMap,
                         Settlement nukeableSettlement,
                         Location ofVolcano,
                         BiHexTileStructure terrains,
                         PlayerID activePlayer) {

        PlayerID otherPlayer;

        if(activePlayer == PlayerID.PLAYER_ONE) {
            otherPlayerSettlements = state.getPlayerTwo().getListOfSettlements();
            otherPlayer = PlayerID.PLAYER_TWO;
        }
        else {
            otherPlayer = PlayerID.PLAYER_ONE;
            otherPlayerSettlements = state.getPlayerOne().getListOfSettlements();
        }

        this.nukeableSettlement = nukeableSettlement;
        this.hexMap = hexMap;
        this.pieceMap = state.getGamePieceMap();

        Location[] adjacentLocationsToVolcano = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(ofVolcano);
        List<Integer> buildingIndices = PairOfLocationsAroundVolcanoListGetter.get(hexMap, adjacentLocationsToVolcano);

        Location left = null;
        Location right = null;

        for(int i = 0; i < buildingIndices.size(); i += 1) {

            if(i+1 == buildingIndices.size())
                return null;

            Location l1 = adjacentLocationsToVolcano[buildingIndices.get(i)];
            Location l2 = adjacentLocationsToVolcano[buildingIndices.get(i + 1)];

            if (    noneContainsSpecialPiece(l1, l2) &&
                    atLeastOneInSettlement(l1, l2) &&
                    belongToDifferentTiles(l1, l2) &&
                    areOnSameLevel(l1, l2, ofVolcano) &&
                    doestNotNukeOtherPlayerSizeOneSettlement(l1, l2) &&
                    doestNotNukeOtherPlayerSpecialPiece(l1, l2)) {

                left = l1;
                right = l2;
                break;
            }
        }

        Location[] locationInTile = {ofVolcano, left, right};
        Terrain[] terrainsInTile = {Terrain.VOLCANO, terrains.getLeftTerrain(), terrains.getRightTerrain()};

        return new TileImpl(Arrays.asList(terrainsInTile), Arrays.asList(locationInTile));
    }

    private boolean noneContainsSpecialPiece(Location l1, Location l2) {
        if(pieceMap.isThereAPieceAt(l1)) {
            if(pieceMap.getPieceAtLocation(l1).getPieceType() == TypeOfPiece.TOTORO ||
                pieceMap.getPieceAtLocation(l1).getPieceType() == TypeOfPiece.TIGER)

                return false;
        }

        if(pieceMap.isThereAPieceAt(l2)) {
            if(pieceMap.getPieceAtLocation(l2).getPieceType() == TypeOfPiece.TOTORO ||
                pieceMap.getPieceAtLocation(l2).getPieceType() == TypeOfPiece.TIGER)

                return false;
        }

        return true;
    }

    private boolean atLeastOneInSettlement(Location l1, Location l2) {
        return nukeableSettlement.locationIsInSettlement(l1) || nukeableSettlement.locationIsInSettlement(l2);
    }

    private boolean belongToDifferentTiles(Location l1, Location l2) {
        return LocationsBelongToTwoDifferentTiles.check(l1, l2, hexMap);
    }

    private boolean areOnSameLevel(Location l1, Location l2, Location l3) {
        return AllLocationsOnLevelOne.check(l1, l2, l3, hexMap);
    }

    private boolean doestNotNukeOtherPlayerSizeOneSettlement(Location l1, Location l2) {
        for (Settlement s : otherPlayerSettlements) {
            if(s.locationIsInSettlement(l1) && s.getNumberOfHexesInSettlement() < 2) {
                return false;
            }

            if(s.locationIsInSettlement(l2) && s.getNumberOfHexesInSettlement() < 2) {
                return false;
            }
        }

        return true;
    }

    private boolean doestNotNukeOtherPlayerSpecialPiece(Location l1, Location l2) {
        for(Settlement s : otherPlayerSettlements) {
            if(s.locationIsInSettlement(l1) &&
               (pieceMap.getPieceAtLocation(l1).getPieceType() == TypeOfPiece.TOTORO ||
                pieceMap.getPieceAtLocation(l1).getPieceType() == TypeOfPiece.TIGER) ) {

                return false;
            }

            if(s.locationIsInSettlement(l2) &&
                (pieceMap.getPieceAtLocation(l2).getPieceType() == TypeOfPiece.TOTORO ||
                 pieceMap.getPieceAtLocation(l2).getPieceType() == TypeOfPiece.TIGER) ) {

                return false;
            }
        }

        return true;
    }

}
