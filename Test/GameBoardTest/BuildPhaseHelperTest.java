package GameBoardTest;

import GamePieceMap.*;
import Play.BuildPhase.BuildPhase;
import Player.PlayerID;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.Before;
import org.junit.Test;
import Tile.Tile.*;
import Location.*;
import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import GameBoard.*;

public class BuildPhaseHelperTest {
    private PlayerID firstPlayer;
    private TileMap tileMap;
    private GamePieceMap gamePieceMap;
    private BuildPhase buildPhase;
    private TileBuilder tileBuilder;
    private BuildPhaseHelper buildPhaseHelper;

    @Before
    public void initializeInstances() {
        firstPlayer = PlayerID.PLAYER_ONE;
        tileMap = new HexagonMap();
        gamePieceMap = new GamePieceMap();
        buildPhaseHelper = new BuildPhaseHelper();
        tileBuilder = new TileBuilder();
    }

    @Test
    public void settlementFoundationAttemptSucceeds()
            throws InvalidTileLocationException, LocationOccupiedException {

        createAndPlaceTileAtOrigin();

        GamePiece firstVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);
        Location newSettlementLocation = new Location(0, 1);
        buildPhase = new BuildPhase(firstVillage, newSettlementLocation);

        assertTrue(buildPhaseHelper.attemptSettlementFoundation(buildPhase, tileMap, gamePieceMap));
    }

    @Test
    public void settlementFoundationFailsForAttemptingToFindOnAVolcano()
            throws InvalidTileLocationException, LocationOccupiedException {

        createAndPlaceTileAtOrigin();

        GamePiece firstVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);
        Location settlementLocationOnTopOfVolcano = new Location(0, 0);
        buildPhase = new BuildPhase(firstVillage, settlementLocationOnTopOfVolcano);

        assertFalse(buildPhaseHelper.attemptSettlementFoundation(buildPhase, tileMap, gamePieceMap));
    }

    @Test
    public void settlementExpansionAttemptSucceeds()
            throws InvalidTileLocationException, LocationOccupiedException, LocationNotEmptyException {

        createAndPlaceTileAtOrigin();

        GamePiece firstVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);
        GamePiece expansionVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);

        Location firstVillageLocation = new Location(1, 0);
        Location expansionLocation = new Location(0, 1);

        gamePieceMap.insertAPieceAt(firstVillageLocation, firstVillage);
        buildPhase = new BuildPhase(expansionVillage, expansionLocation);

        assertTrue(buildPhaseHelper.attemptSettlementExpansion(buildPhase, tileMap, gamePieceMap));
    }

    @Test
    public void settlementExpansionFailsForBeingFirstSettlement()
            throws InvalidTileLocationException, LocationOccupiedException {

        createAndPlaceTileAtOrigin();

        GamePiece firstVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);
        Location firstVillageLocation = new Location(1, 0);
        buildPhase = new BuildPhase(firstVillage, firstVillageLocation);

        assertFalse(buildPhaseHelper.attemptSettlementExpansion(buildPhase, tileMap, gamePieceMap));
    }

    @Test
    public void totoroPlacementAttemptSucceeds()
            throws InvalidTileLocationException, LocationOccupiedException, LocationNotEmptyException {

        createAndPlaceTileAtOrigin();

        Location originTileLoc2 = new Location(0,1);
        Location originTileLoc3 = new Location(1,0);

        Location adjTileLoc1 = new Location(0,2);
        Location adjTileLoc2 = new Location(1,2);
        Location adjTileLoc3 = new Location(1,1);

        Tile adjacentTile = tileBuilder.getTileWithLocations(adjTileLoc1, adjTileLoc2, adjTileLoc3);
        tileMap.insertTile(adjacentTile);

        Location northTileLoc1 = new Location(0,3);
        Location northTileLoc2 = new Location(4,4);
        Location northTileLoc3 = new Location(1,3);

        Tile northernMostTile = tileBuilder.getTileWithLocations(northTileLoc1, northTileLoc2, northTileLoc3);
        tileMap.insertTile(northernMostTile);

        GamePiece standardVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);
        gamePieceMap.insertAPieceAt(originTileLoc2, standardVillage);
        gamePieceMap.insertAPieceAt(originTileLoc3, standardVillage);
        gamePieceMap.insertAPieceAt(adjTileLoc3, standardVillage);
        gamePieceMap.insertAPieceAt(adjTileLoc2, standardVillage);
        gamePieceMap.insertAPieceAt(northTileLoc3, standardVillage);

        GamePiece totoroToPlace = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);
        Location totoroPlacementLocation = new Location(0, 4);
        buildPhase = new BuildPhase(totoroToPlace, totoroPlacementLocation);

        assertTrue(buildPhaseHelper.attemptTotoroPlacement(buildPhase, tileMap, gamePieceMap));
    }

    @Test
    public void totoroPlacementAttemptFailsForLackOfSettlementSize()
            throws InvalidTileLocationException, LocationOccupiedException {

        createAndPlaceTileAtOrigin();

        GamePiece totoroToPlace = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);
        Location totoroPlacementLocation = new Location(1, 0);
        buildPhase = new BuildPhase(totoroToPlace, totoroPlacementLocation);

        assertFalse(buildPhaseHelper.attemptTotoroPlacement(buildPhase, tileMap, gamePieceMap));
    }

    @Test
    public void tigerPlacementAttemptSucceeds()
            throws InvalidTileLocationException, LocationOccupiedException, LocationNotEmptyException {

        createAndPlaceTileAtOrigin();

        Location level1Tile11 = new Location(1,1);
        Location level1Tile12 = new Location(0,2);
        Location level1Tile13 = new Location(1,2);

        Tile level1Tile1 = tileBuilder.getTileWithLocations(level1Tile11, level1Tile12, level1Tile13);
        tileMap.insertTile(level1Tile1);

        Location level1Tile21 = new Location(0,3);
        Location level1Tile22 = new Location(0,4);
        Location level1Tile23 = new Location(1,3);

        Tile level1Tile2 = tileBuilder.getTileWithLocations(level1Tile21, level1Tile22, level1Tile23);
        tileMap.insertTile(level1Tile2);

        Location level2Tile11 = new Location(1,1);
        Location level2Tile12 = new Location(0,1);
        Location level2Tile13 = new Location(1,0);

        Tile level2Tile1 = tileBuilder.getTileWithLocations(level2Tile11, level2Tile12, level2Tile13);
        tileMap.insertTile(level2Tile1);

        Location level2Tile21 = new Location(0,3);
        Location level2Tile22 = new Location(0,2);
        Location level2Tile23 = new Location(1,2);

        Tile level2Tile2 = tileBuilder.getTileWithLocations(level2Tile21, level2Tile22, level2Tile23);
        tileMap.insertTile(level2Tile2);

        Location level3Tile11 = new Location(1,1);
        Location level3Tile12 = new Location(0,2);
        Location level3Tile13 = new Location(1,2);

        Tile level3Tile1 = tileBuilder.getTileWithLocations(level3Tile11, level3Tile12, level3Tile13);
        tileMap.insertTile(level3Tile1);

        GamePiece firstVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);
        GamePiece tigerToPlace = new GamePiece(firstPlayer, TypeOfPiece.TIGER);

        gamePieceMap.insertAPieceAt(level3Tile12, firstVillage);
        buildPhase = new BuildPhase(tigerToPlace, level3Tile13);

        assertTrue(buildPhaseHelper.attemptTigerPlacement(buildPhase, tileMap, gamePieceMap));
    }

    @Test
    public void tigerPlacementAttemptFailsWithAdjacentSettlementButTileIsFirstLevel()
            throws InvalidTileLocationException, LocationOccupiedException, LocationNotEmptyException {

        createAndPlaceTileAtOrigin();

        Location settlementLocation = new Location(0,1);
        Location tigerLocation = new Location(1, 0);

        GamePiece firstVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);
        GamePiece tigerToPlace = new GamePiece(firstPlayer, TypeOfPiece.TIGER);

        gamePieceMap.insertAPieceAt(settlementLocation, firstVillage);
        buildPhase = new BuildPhase(tigerToPlace, tigerLocation);

        assertFalse(buildPhaseHelper.attemptTigerPlacement(buildPhase, tileMap, gamePieceMap));
    }

    private void createAndPlaceTileAtOrigin()
            throws InvalidTileLocationException, LocationOccupiedException {

        Tile initialTile = tileBuilder.getTileAtOrigin();
        tileMap.insertTile(initialTile);
    }
}