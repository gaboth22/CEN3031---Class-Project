package Steve.PlayGeneration.NukeTileSetter;

import GameBoard.GameBoardState;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Player.*;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.PlayGeneration.SmartTilePlacer.AdjacentLocationsToSettlementGetter;
import Steve.PlayGeneration.SmartTilePlacer.OppositePlayerGetter;
import Location.Location;
import Steve.PlayGeneration.SortSettlementArrayHelper;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileMap.Hexagon;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class SettingNukeTileForNextTurnTileMaker {

    private OppositePlayerGetter oppositePlayerGetter;
    private AdjacentLocationsToSettlementGetter adjacentLocationsToSettlementGetter;
    private Map<Location, Hexagon> hexMap;
    private Terrain leftTerrain;
    private Terrain rightTerrain;

    public SettingNukeTileForNextTurnTileMaker() {
        oppositePlayerGetter = new OppositePlayerGetter();
        adjacentLocationsToSettlementGetter = new AdjacentLocationsToSettlementGetter();
    }

    public Tile getTile(
            GameBoardState gameBoardState,
            PlayerID activePlayer,
            BiHexTileStructure tileToPlace) {

        hexMap = gameBoardState.getPlacedHexagons();

        Player otherPlayer = oppositePlayerGetter.getOtherPlayerFromGameBoardState(activePlayer, gameBoardState);
        List<Settlement> otherPlayerSettlements = otherPlayer.getListOfSettlements();

        Settlement[] sortedSettlements = SortSettlementArrayHelper.convertListToSettlementAndSort(otherPlayerSettlements);

        Location left = null;
        Location right = null;

        leftTerrain = tileToPlace.getLeftTerrain();
        rightTerrain = tileToPlace.getRightTerrain();


        for(Settlement s : sortedSettlements) {

            if(settlementSizeIsWithinRange(s)) {

                Tile tilePlace = findTileThatCanNukeThisSettlement(s);

                if(tilePlace != null)
                    return tilePlace;
            }
        }

        return null;
    }

    private Tile findTileThatCanNukeThisSettlement(Settlement settlement) {

        Set<Location> locationsInSettlements = settlement.getSetOfLocationsInSettlement();

        for(Location loc : locationsInSettlements) {

            if(hexMap.containsKey(loc) && hexMap.get(loc).getHeight() == 1) {

                Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(loc);

                Tile toPlace = attemptTileCreationFromAdjacents(adjacents);
                if(toPlace != null)
                    return toPlace;
            }
        }
        return null;
    }

    private Tile attemptTileCreationFromAdjacents(Location[] adjacents) {

        for(Location l : adjacents) {

            Tile tileCreated = findTileFromLocation(l);

            if(tileCreated != null) {

                return tileCreated;
            }
        }

        return null;
    }

    private Tile findTileFromLocation(Location l) {
        Location[] adjacentToEmptySpot = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(l);

        int indexOfAvailable = getIndexOfAvailable(adjacentToEmptySpot);

        if(indexOfAvailable != -1) {
            Location left = adjacentToEmptySpot[indexOfAvailable];
            Location right = adjacentToEmptySpot[indexOfAvailable + 1];
            return createTile(l, left, right);
        }
        return null;
    }

    private int getIndexOfAvailable(Location[] adjacents) {
        for (int i = 1; i < adjacents.length; i++) {
            if (!hexMap.containsKey(adjacents[i]) && !hexMap.containsKey(adjacents[i - 1])) {
                return i - 1;
            }
        }
        return -1;
    }

    private Tile createTile(Location toPutTileOn, Location ofLeftTerrain, Location ofRightTerrain) {

        Location[] locationsOfTile = new Location[]{toPutTileOn, ofLeftTerrain, ofRightTerrain};
        Terrain[] terrainsOfTile = new Terrain[]{Terrain.VOLCANO, leftTerrain, rightTerrain};

        return new TileImpl(Arrays.asList(terrainsOfTile), Arrays.asList(locationsOfTile));
    }

    private boolean settlementSizeIsWithinRange(Settlement s) {
        return s.getNumberOfHexesInSettlement() >= 4 && s.getNumberOfHexesInSettlement() <= 6;
    }
}
