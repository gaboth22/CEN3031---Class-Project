package GameBoardTest;

import GameBoard.GameBoard;
import GameBoard.GameBoardImpl;
import GamePieceMap.*;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Tile.Tile.TileImpl;
import TileMap.Hexagon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Tile.Tile.Tile;
import Location.*;
import Terrain.Terrain.*;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import static org.junit.Assert.*;

public class GameBoardImplTest {
    private GameBoardImpl gameBoard;


    @Before
    public void setUpGameBoard() throws Exception {
        gameBoard = new GameBoardImpl();
    }

    @After
    public void tearDown() throws Exception {
        gameBoard = null;
    }

    @Test
    public void FirstTileCorrectlyCreatedAndInsertedTest() throws Exception {
        Tile firstTile = gameBoard.getFirstTile();
        Location[] locationsOfFirstTile = firstTile.getArrayOfTerrainLocations();
        Terrain[] terrainsInFirstTile = firstTile.getArrayOfTerrains();
        Map<Location, Hexagon> placedLocations = gameBoard.getPlacedHexagons();

        for(int i = 0; i < locationsOfFirstTile.length; i++){
            assertTrue(placedLocations.containsKey(locationsOfFirstTile[i]));
        }

        for(int i = 0; i < terrainsInFirstTile.length; i++){
            Terrain terrainToCheck = placedLocations.get(locationsOfFirstTile[i]).getTerrain();
            assertTrue(terrainToCheck == terrainsInFirstTile[i]);
        }
    }

    @Test
    public void SuccessfulSimpleTilePlacement() throws Exception {
        GameBoardImpl gameBoard = new GameBoardImpl();
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
        Map<Location, Hexagon> hexesOnMap = gameBoard.getPlacedHexagons();
        checkSimpleTileLocationsArePlacedCorrectly(hexesOnMap, getSimpleTileToPlace());
    }

    private void checkSimpleTileLocationsArePlacedCorrectly(Map<Location, Hexagon> placedLocations, Tile placedTile) {
        Location[] locationsOfFirstTile = placedTile.getArrayOfTerrainLocations();
        Terrain[] terrainsInFirstTile = placedTile.getArrayOfTerrains();

        for(int i = 0; i < locationsOfFirstTile.length; i++){
            assertTrue(placedLocations.containsKey(locationsOfFirstTile[i]));
        }

        for(int i = 0; i < terrainsInFirstTile.length; i++){
            Terrain terrainToCheck = placedLocations.get(locationsOfFirstTile[i]).getTerrain();
            assertTrue(terrainToCheck == terrainsInFirstTile[i]);
        }
    }

    private Tile getSimpleTileToPlace() {
        Location[] locations = new Location[] {
                new Location(1, 1),
                new Location(0,1),
                new Location(0,2)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    @Test(expected = TilePlacementPhaseException.class)
    public void FailingSimpleTilePlacement() throws Exception {
        GameBoardImpl gameBoard = new GameBoardImpl();
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, getFailingSimpleTileToPlace());
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    public Tile getFailingSimpleTileToPlace(){
        Location[] locations = new Location[] {
                new Location(0,0),
                new Location(0,1),
                new Location(1,0)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.ROCKY,
                Terrain.ROCKY
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    @Test
    public void SuccessfulFoundationBuildPhaseTest() throws Exception {
        GameBoardImpl gameBoard = new GameBoardImpl();
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);

        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);

        GamePieceMap placedGamePieces = gameBoard.getGamePieceMap();
        checkSettlementSuccessfullyFounded(placedGamePieces, buildPhase);
    }

    private void checkSettlementSuccessfullyFounded(GamePieceMap placedGamePieces, BuildPhase buildPhase) {
        GamePiece placedPiece = placedGamePieces.getPieceAtLocation(buildPhase.getLocationToPlacePieceOn());
        GamePiece expectedPiece = buildPhase.getGamePiece();
        assertEquals(expectedPiece, placedPiece);
    }

    @Test(expected = BuildPhaseException.class)
    public void FailingFoundationBuildPhaseTest() throws Exception {
        GameBoardImpl gameBoard = new GameBoardImpl();
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(0,0));
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);
    }

    @Test
    public void SuccessfulExpansionBuildPhaseTest() throws Exception {
        GameBoardImpl gameBoard = new GameBoardImpl();

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(1,0));
        buildPhaseOne.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseOne);

        List<Settlement> settlement = gameBoard.getPlayerOneSettlements();
        GamePiece gamePieceTwo = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseTwo = new BuildPhase(gamePieceTwo, new Location(0,1), settlement.get(0));
        buildPhaseTwo.setBuildType(BuildType.EXPAND);
        gameBoard.doBuildPhase(buildPhaseTwo);
    }

    @Test(expected = BuildPhaseException.class)
    public void FailedExpansionBuildPhaseTest() throws Exception {
        GameBoardImpl gameBoard = new GameBoardImpl();

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(1,0));
        buildPhaseOne.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseOne);

        List<Settlement> settlement = gameBoard.getPlayerOneSettlements();
        GamePiece gamePieceTwo = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseTwo = new BuildPhase(gamePieceTwo, new Location(0,0), settlement.get(0));
        buildPhaseTwo.setBuildType(BuildType.EXPAND);
        gameBoard.doBuildPhase(buildPhaseTwo);
    }

    public void TotoroPlacementSuccessTest() throws Exception{
        GameBoard gameBoard = new GameBoardImpl();

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSecondSimpleTile());

        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location)
    }

    private Tile getSecondSimpleTile(){
        Location[] locations = new Location[] {
                new Location(1, 1),
                new Location(0,1),
                new Location(0,2)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    @Test
    public void getCurrentTurn() throws Exception {

    }

    @Test
    public void getPlacedHexagons() throws Exception {

    }

    @Test
    public void hasTileAt() throws Exception {

    }

    @Test
    public void getPlayer() throws Exception {

    }

    @Test
    public void getPlayerOneScore() throws Exception {
    }

    @Test
    public void getPlayerTwoScore() throws Exception {

    }

    @Test
    public void getPlayerOneSettlements() throws Exception {

    }

    @Test
    public void getPlayerTwoSettlements() throws Exception {

    }

    @Test
    public void getPlaceableLocations() throws Exception {

    }

    @Test
    public void getGamePieceMap() throws Exception {

    }

}