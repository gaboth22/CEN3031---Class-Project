package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.*;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import Terrain.Terrain.*;
import TileMap.Hexagon;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategicTilePlacement {

    public static TilePlacementPhase makeAStrategicTilePlacement(GameBoardState gameBoardState,
                                                                 Player currentPlayer,
                                                                 BiHexTileStructure terrains) {

        Map<Location, Hexagon> hexagonMap = gameBoardState.getPlacedHexagons();
        List<Settlement> listOfSettlements = currentPlayer.getListOfSettlements();

        Settlement[] currentPlayerSettlements;
        currentPlayerSettlements = SortSettlementArrayHelper.convertListToSettlementAndSort(listOfSettlements);

        if(currentPlayerSettlements.length == 0) {
            return null;
        }

        else {
            Location leftHex = null, rightHex = null, firstHex = null;
            boolean foundHexLocations = false;

            for(int i = 0; i < currentPlayerSettlements.length; i++) {
                Settlement biggestSettlement = currentPlayerSettlements[i];

                if(biggestSettlement.hasTotoroSanctuary())
                    continue;

                Set<Location> locationsInSettlement = biggestSettlement.getSetOfLocationsInSettlement();

                for(Location l:locationsInSettlement) {
                    Location adjacentLocs[] = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(l);

                    for(int j = 1; j < adjacentLocs.length; j++) {
                        if(!hexagonMap.containsKey(adjacentLocs[j-1]) && !hexagonMap.containsKey(adjacentLocs[j])) {
                            Location hex1[] = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(adjacentLocs[j-1]);
                            Location hex2[] = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(adjacentLocs[j]);

                            for(int k = 0; k < hex1.length; k++) {
                                for(int m = 0; m < hex2.length; m++) {
                                    if(hex1[k].equals(hex2[m]) && !hexagonMap.containsKey(hex1[k])) {
                                        leftHex = adjacentLocs[j];
                                        rightHex = adjacentLocs[j-1];
                                        firstHex = hex1[k];

                                        foundHexLocations = true;
                                        break;
                                    }
                                }
                                if(foundHexLocations)
                                    break;
                            }
                        }
                        if(foundHexLocations)
                            break;
                    }
                    if(foundHexLocations)
                        break;
                }
                if(foundHexLocations)
                    break;
            }

            if(foundHexLocations) {
                Location[] tileLocation = new Location[]{firstHex, leftHex, rightHex};
                Terrain[] tileTerrains = getTerrainsOfTileBeingPlaced(terrains);

                return createSuccessfulTilePlacement(tileTerrains, tileLocation, currentPlayer);
            }
        }

        return null;
    }

    private static Terrain[] getTerrainsOfTileBeingPlaced(BiHexTileStructure terrains) {
        Terrain leftTerrain = terrains.getLeftTerrain();
        Terrain rightTerrain = terrains.getRightTerrain();

        return new Terrain[]{Terrain.VOLCANO, leftTerrain, rightTerrain};
    }

    private static TilePlacementPhase createSuccessfulTilePlacement(Terrain[] terrainsOfTile, Location[] locationsOfTile, Player player) {
        Tile tileToPlace = new TileImpl(Arrays.asList(terrainsOfTile), Arrays.asList(locationsOfTile));
        TilePlacementPhase strategicTilePlacement = new TilePlacementPhase(player.getID(), tileToPlace);
        strategicTilePlacement.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        return strategicTilePlacement;
    }
}