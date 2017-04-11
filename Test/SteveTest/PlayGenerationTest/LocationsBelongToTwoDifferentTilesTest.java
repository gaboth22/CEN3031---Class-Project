package SteveTest.PlayGenerationTest;

import GameBoard.*;
import Location.Location;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Steve.PlayGeneration.SmartTilePlacer.LocationsBelongToTwoDifferentTiles;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

public class LocationsBelongToTwoDifferentTilesTest {
    private GameBoard board;
    private Map<Location, Hexagon> hexMap;

    @Before
    public void initializeInstances() {
        board = new GameBoardImpl();
    }

    @Test
    public void locationsShouldNotBePartOfTheSameTile() throws Exception {
        TilePlacementPhase addTileNextToStartingTile = givenIHaveATilePlacementPhaseThatPutsATileOnTopOfTheStartingTile();
        board.doTilePlacementPhase(addTileNextToStartingTile);
        hexMap = board.getPlacedHexagons();

        Location inStartingTile = new Location(1,0);
        Location inOtherTile = new Location( 0, 1);

        Assert.assertTrue(LocationsBelongToTwoDifferentTiles.check(inOtherTile, inStartingTile, hexMap));
    }

    private TilePlacementPhase givenIHaveATilePlacementPhaseThatPutsATileOnTopOfTheStartingTile() {
        Tile tile = new Tile() {
            @Override
            public Location[] getArrayOfTerrainLocations() {
                return new Location[]{
                        new Location(-1, 2),
                        new Location(0, 2),
                        new Location(0, 1)
                };
            }

            @Override
            public Terrain[] getArrayOfTerrains() {
                return new Terrain[]{
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
    public void locationsShouldBePartOfTheSameTile() throws Exception {

        TilePlacementPhase addTileNextToStartingTile = givenIHaveATilePlacementPhaseThatPutsATileOnTopOfTheStartingTile();
        board.doTilePlacementPhase(addTileNextToStartingTile);
        hexMap = board.getPlacedHexagons();

        Location inStartingTile = new Location(1,0);
        Location againInStartingTile = new Location( 1, -1);

        Assert.assertFalse(LocationsBelongToTwoDifferentTiles.check(againInStartingTile, inStartingTile, hexMap));

        Location alsoInStartingTile = new Location(0, 0);
        Location yetAgainInStartingTile = new Location(-1, 1);

        Assert.assertFalse(LocationsBelongToTwoDifferentTiles.check(alsoInStartingTile, yetAgainInStartingTile, hexMap));

        Location againInsideOfStartingTile = new Location(-1, 0);

        Assert.assertFalse(LocationsBelongToTwoDifferentTiles.check(inStartingTile, againInsideOfStartingTile, hexMap));
    }
}
