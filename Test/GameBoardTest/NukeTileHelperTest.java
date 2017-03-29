package GameBoardTest;

import GamePieceMap.GamePieceMap;
import Play.TilePlacementPhase.TilePlacementPhase;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.Before;
import Player.*;
import Tile.Tile.Tile;
import GameBoard.*;
import org.junit.Test;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import Location.*;
import GamePieceMap.*;

public class NukeTileHelperTest {
    private PlayerID firstPlayer;
    private TilePlacementPhase tilePlacementPhase;
    private TileMap tileMap;
    private GamePieceMap gamePieceMap;
    private Tile tileToPlace;
    private TileBuilder tileBuilder;
    private NukeTileHelper nukeTileHelper;

    @Before
    public void initializeInstances() {
        firstPlayer = PlayerID.PLAYER_ONE;
        tileBuilder = new TileBuilder();
        tileToPlace = tileBuilder.getTileAtOrigin();
        tilePlacementPhase = new TilePlacementPhase(firstPlayer, tileToPlace);
        nukeTileHelper = new NukeTileHelper();
        tileMap = new HexagonMap();
        gamePieceMap = new GamePieceMap();
    }

    @Test
    public void nukeFailsForPlacingSameTileOnTopOfItself() throws InvalidTileLocationException, LocationOccupiedException {
        tileMap.insertTile(tileToPlace);
        assertFalse(nukeTileHelper.attemptNuke(tilePlacementPhase, tileMap, gamePieceMap));
    }

    @Test
    public void nukeTileAttemptSucceeds() throws InvalidTileLocationException,
            LocationOccupiedException, LocationNotEmptyException {
        Location originTileLocation1 = new Location(0,0);
        Location originTileLocation2 = new Location(0,1);
        Location originTileLocation3 = new Location(1,0);

        Tile originTile = tileBuilder.getTileWithLocations(originTileLocation1, originTileLocation2, originTileLocation3);
        tileMap.insertTile(originTile);

        Location adjTileLocation1 = new Location(1,1);
        Location adjTileLocation2 = new Location(2,1);
        Location adjTileLocation3 = new Location(2,0);

        Tile adjacentTile = tileBuilder.getTileWithLocations(adjTileLocation1, adjTileLocation2, adjTileLocation3);
        tileMap.insertTile(adjacentTile);

        Location nukeTileLocation1 = new Location(1,1 );
        Location nukeTileLocation2 = new Location(0,1);
        Location nukeTileLocation3 = new Location(1, 0);

        Tile nukingTile = tileBuilder.getTileWithLocations(nukeTileLocation1, nukeTileLocation2, nukeTileLocation3);
        TilePlacementPhase nukePhase = new TilePlacementPhase(firstPlayer, nukingTile);

        assertTrue(nukeTileHelper.attemptNuke(nukePhase, tileMap, gamePieceMap));
    }
}