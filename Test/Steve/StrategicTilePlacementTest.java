package Steve;

import GameBoard.GameBoardImpl;
import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.*;
import Steve.PlayGeneration.StrategicTilePlacement;
import Terrain.Terrain.Terrain;
import Location.*;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StrategicTilePlacementTest {
    private GameBoardImpl gameBoard;
    private GameBoardState gameBoardState;
    private BiHexTileStructure terrains;
    private TilePlacementPhase tilePlacementPhase;
    private TileBuilder tileBuilder;

    @Before
    public void initializeInstances() {
        gameBoard = new GameBoardImpl();
        gameBoardState = new GameBoardState(gameBoard.getPlayer(PlayerID.PLAYER_ONE),
                                            gameBoard.getPlayer(PlayerID.PLAYER_TWO),
                                            1,
                                            gameBoard.getPlacedHexagons(),
                                            gameBoard.getGamePieceMap(),
                                            gameBoard.getPlaceableLocations(),
                                            gameBoard.getNukeableVolcanoLocations());

        Terrain[] terrain = new Terrain[]{Terrain.GRASSLANDS, Terrain.JUNGLE};
        terrains = new BiHexTileStructure(terrain);
        tileBuilder = new TileBuilder();
    }

    @Test
    public void shouldReturnATilePlacementAdjacentToOnlySettlement() throws Exception {
        Location locationToPlaceSettlement = new Location(1, 0);
        findSettlementAtLocation(locationToPlaceSettlement);

        Player playerOne = gameBoard.getPlayer(PlayerID.PLAYER_ONE);
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, playerOne, terrains);
        Location[] tileShouldBe = new Location[]{new Location(2, 1),  new Location(2,0), new Location(1,1)};

        assertTrue(Arrays.equals(tileShouldBe, tilePlacementPhase.getTileToPlace().getArrayOfTerrainLocations()));
    }

    @Test
    public void shouldReturnTilePlacementAdjacentToBiggerSettlement() throws Exception {
        setpUpSettlementOneAndSettlementTwo();

        Player playerOne = gameBoard.getPlayer(PlayerID.PLAYER_ONE);
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, playerOne, terrains);
        Location[] tileShouldBe = new Location[]{new Location(0,-2), new Location(-1,-1), new Location(0,-1)};

        assertTrue(Arrays.equals(tileShouldBe, tilePlacementPhase.getTileToPlace().getArrayOfTerrainLocations()));
    }

    @Test
    public void checkForNewTilePlacementOrientationToBiggerSettlement() throws Exception {
        setpUpSettlementOneAndSettlementTwo();

        Location tile11 = new Location(0,-2);
        Location tile12 = new Location(-1,-1);
        Location tile13 = new Location(0,-1);
        placeTileAtLocation(tile11, tile12, tile13);

        setUpGameBoard();
        Player playerOne = gameBoard.getPlayer(PlayerID.PLAYER_ONE);
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, playerOne, terrains);
        Location[] tileShouldBe = new Location[]{new Location(-3,1), new Location(-2,1), new Location(-2,0)};

        assertTrue(Arrays.equals(tileShouldBe, tilePlacementPhase.getTileToPlace().getArrayOfTerrainLocations()));
    }

    @Test
    public void placesTileNearSmallerSettlementSinceNoSpaceAroundLarger() throws Exception {
        setpUpSettlementOneAndSettlementTwo();

        Location tile11 = new Location(0,-2);
        Location tile12 = new Location(-1,-1);
        Location tile13 = new Location(0,-1);
        placeTileAtLocation(tile11, tile12, tile13);

        Location tile21 = new Location(-3,1);
        Location tile22 = new Location(-2,1);
        Location tile23 = new Location(-2,0);
        placeTileAtLocation(tile21, tile22, tile23);

        Location tile31 = new Location(0,2);
        Location tile32 = new Location(0,1);
        Location tile33 = new Location(-1,2);
        placeTileAtLocation(tile31, tile32, tile33);

        setUpGameBoard();
        Player playerOne = gameBoard.getPlayer(PlayerID.PLAYER_ONE);
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, playerOne, terrains);
        Location[] tileShouldBe = new Location[]{new Location(2,1), new Location(2,0), new Location(1,1)};

        assertTrue(Arrays.equals(tileShouldBe, tilePlacementPhase.getTileToPlace().getArrayOfTerrainLocations()));
    }

    @Test
    public void shouldReturnNullDueToBothSettlementsHavingTilesPlacedAroundThem() throws Exception {
        setpUpSettlementOneAndSettlementTwo();

        Location tile11 = new Location(0,-2);
        Location tile12 = new Location(-1,-1);
        Location tile13 = new Location(0,-1);
        placeTileAtLocation(tile11, tile12, tile13);

        Location tile21 = new Location(-3,1);
        Location tile22 = new Location(-2,1);
        Location tile23 = new Location(-2,0);
        placeTileAtLocation(tile21, tile22, tile23);

        Location tile31 = new Location(0,2);
        Location tile32 = new Location(0,1);
        Location tile33 = new Location(-1,2);
        placeTileAtLocation(tile31, tile32, tile33);

        Location tile41 = new Location(2,1);
        Location tile42 = new Location(2,0);
        Location tile43 = new Location(1,1);
        placeTileAtLocation(tile41, tile42, tile43);

        setUpGameBoard();
        Player playerOne = gameBoard.getPlayer(PlayerID.PLAYER_ONE);
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, playerOne, terrains);

        assertEquals(null, tilePlacementPhase);
    }

    @Test
    public void shouldReturnNullDueToThereBeingNoSettlementOnTheMap() {
        Player playerOne = gameBoard.getPlayer(PlayerID.PLAYER_ONE);
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, playerOne, terrains);

        assertEquals(null, tilePlacementPhase);
    }

    private void findSettlementAtLocation(Location location) throws Exception {
        GamePiece standardVillage = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase;
        buildPhase = new BuildPhase(standardVillage, location);
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);
    }

    private void setUpGameBoard() {
        gameBoardState = new GameBoardState(gameBoard.getPlayer(PlayerID.PLAYER_ONE),
                gameBoard.getPlayer(PlayerID.PLAYER_TWO),
                1,
                gameBoard.getPlacedHexagons(),
                gameBoard.getGamePieceMap(),
                gameBoard.getPlaceableLocations(),
                gameBoard.getNukeableVolcanoLocations());
    }

    private void placeTileAtLocation(Location l1, Location l2, Location l3) throws Exception {
        Tile adjacentTile = tileBuilder.getTileWithLocations(l1, l2, l3);
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, adjacentTile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void setpUpSettlementOneAndSettlementTwo() throws Exception {
        Location settlementOne = new Location(1,0);
        findSettlementAtLocation(settlementOne);

        Location settlementTwo1 = new Location(-1, 1);
        Location settlementTwo2 = new Location(-1, 0);
        findSettlementAtLocation(settlementTwo1);
        findSettlementAtLocation(settlementTwo2);
    }
}

