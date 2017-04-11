package SteveTest.PlayGenerationTest;

import GameBoard.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Steve.PlayGeneration.SmartTilePlacer.PairOfLocationsAroundVolcanoListGetter;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class PairOfLocationsAroundVolcanoListGetterTest {
    private GameBoard board;
    private Map<Location, Hexagon> hexMap;

    @Before
    public void initializeInstances() {
        board = new GameBoardImpl();
    }

    @Test
    public void shouldReturnTheIndicesThatCorrespondToTwoConsecutiveTilesInArrayOfAllAdjacentLocations() {
        Location[] allAdjacentLocations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(new Location(0,0));
        hexMap = board.getPlacedHexagons();
        List<Integer> adjacentToOriginThatHaveTwoConsecutiveHexes = PairOfLocationsAroundVolcanoListGetter.get(hexMap, allAdjacentLocations);

        //These belong to the starting tile, and since this is the only tile in the map, these are the hexes that have terrains on them
        Assert.assertEquals(4, adjacentToOriginThatHaveTwoConsecutiveHexes.size());
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(0).equals(1));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(1).equals(2));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(2).equals(4));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(3).equals(5));
    }

    @Test
    public void shouldReturnTheRightIndicesWhenThereIsAnotherTileAroundIt() throws Exception {
        TilePlacementPhase addTileNextToStartingTile = givenIHaveATilePlacementPhaseThatPutsATileOnTopOfTheStartingTile();
        board.doTilePlacementPhase(addTileNextToStartingTile);
        hexMap = board.getPlacedHexagons();

        Location[] allAdjacentLocations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(new Location(0,0));
        List<Integer> adjacentToOriginThatHaveTwoConsecutiveHexes = PairOfLocationsAroundVolcanoListGetter.get(hexMap, allAdjacentLocations);

        Assert.assertEquals(8, adjacentToOriginThatHaveTwoConsecutiveHexes.size());
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(0).equals(0));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(1).equals(1));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(2).equals(1));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(3).equals(2));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(4).equals(4));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(5).equals(5));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(6).equals(5));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(7).equals(0));
    }

    private TilePlacementPhase givenIHaveATilePlacementPhaseThatPutsATileOnTopOfTheStartingTile() {
        Tile tile = new Tile() {
            @Override
            public Location[] getArrayOfTerrainLocations() {
                return new Location[]{
                        new Location(-1,2),
                        new Location(0,2),
                        new Location(0, 1)
                };
            }

            @Override
            public Terrain[] getArrayOfTerrains() {
                return new Terrain[] {
                        Terrain.VOLCANO,
                        Terrain.GRASSLANDS,
                        Terrain.LAKE
                };
            }
        };

        TilePlacementPhase phase = new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
        phase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        return phase;
    }

    @Test
    public void shouldReturnAllPairsAroundTheOrigin() throws Exception {

        TilePlacementPhase addTileOnTopOfStartingTile = givenIHaveATilePlacementPhaseThatPutsATileOnTopOfTheStartingTile();
        TilePlacementPhase addTileBelowStartingTile =  givenIHaveATilePlacementPhaseThatPutsATileUnderTheStartingTile();
        board.doTilePlacementPhase(addTileOnTopOfStartingTile);
        board.doTilePlacementPhase(addTileBelowStartingTile);
        hexMap = board.getPlacedHexagons();

        Location[] allAdjacentLocations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(new Location(0,0));
        List<Integer> adjacentToOriginThatHaveTwoConsecutiveHexes = PairOfLocationsAroundVolcanoListGetter.get(hexMap, allAdjacentLocations);

        Assert.assertEquals(12, adjacentToOriginThatHaveTwoConsecutiveHexes.size());
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(0).equals(0));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(1).equals(1));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(2).equals(1));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(3).equals(2));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(4).equals(2));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(5).equals(3));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(6).equals(3));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(7).equals(4));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(8).equals(4));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(9).equals(5));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(10).equals(5));
        Assert.assertTrue(adjacentToOriginThatHaveTwoConsecutiveHexes.get(11).equals(0));
    }

    private TilePlacementPhase givenIHaveATilePlacementPhaseThatPutsATileUnderTheStartingTile() {
        Tile tile = new Tile() {
            @Override
            public Location[] getArrayOfTerrainLocations() {
                return new Location[]{
                        new Location(1,-2),
                        new Location(0,-2),
                        new Location(0, -1)
                };
            }

            @Override
            public Terrain[] getArrayOfTerrains() {
                return new Terrain[] {
                        Terrain.VOLCANO,
                        Terrain.GRASSLANDS,
                        Terrain.LAKE
                };
            }
        };

        TilePlacementPhase phase = new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
        phase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        return phase;
    }
}
