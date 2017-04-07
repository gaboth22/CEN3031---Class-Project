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

        assertEquals(1, gameBoard.getPlayerOneScore());
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

    @Test
    public void TotoroPlacementSuccessTest() throws Exception{
        GameBoard gameBoard = getSuccessfulTotoroGameBoard();

        GamePiece totoroToPlace = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
        BuildPhase buildPhaseTotoro = new BuildPhase(totoroToPlace, new Location(0,-2));
        buildPhaseTotoro.setBuildType(BuildType.PLACE_TOTORO);
        gameBoard.doBuildPhase(buildPhaseTotoro);

        assertEquals(205, gameBoard.getPlayerOneScore());
    }

    private GameBoard getSuccessfulTotoroGameBoard() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        TilePlacementPhase tilePlacementPhaseTwo = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSecondSimpleTile());
        tilePlacementPhaseTwo.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseTwo);

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(0,2));
        buildPhaseOne.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseOne);

        GamePiece gamePieceTwo = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseTwo = new BuildPhase(gamePieceTwo, new Location(0,1));
        buildPhaseTwo.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseTwo);

        GamePiece gamePieceThree = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseThree = new BuildPhase(gamePieceThree, new Location(1,0));
        buildPhaseThree.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseThree);

        GamePiece gamePieceFour = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseFour = new BuildPhase(gamePieceFour, new Location(1,-1));
        buildPhaseFour.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseFour);

        GamePiece gamePieceFive = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseFive = new BuildPhase(gamePieceFive, new Location(0,-1));
        buildPhaseFive.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseFive);

        return gameBoard;
    }


    @Test(expected = BuildPhaseException.class)
    public void TotoroPlacementFailureTest() throws Exception{
        GameBoard gameBoard = getFailingTotoroGameBoard();

        GamePiece totoroToPlace = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
        BuildPhase buildPhaseTotoro = new BuildPhase(totoroToPlace, new Location(0,-2));
        buildPhaseTotoro.setBuildType(BuildType.PLACE_TOTORO);
        gameBoard.doBuildPhase(buildPhaseTotoro);
    }

    private GameBoard getFailingTotoroGameBoard() throws Exception{
        GameBoard gameBoard = new GameBoardImpl();

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        TilePlacementPhase tilePlacementPhaseTwo = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSecondSimpleTile());
        tilePlacementPhaseTwo.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseTwo);

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(0,2));
        buildPhaseOne.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseOne);

        GamePiece gamePieceTwo = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseTwo = new BuildPhase(gamePieceTwo, new Location(0,1));
        buildPhaseTwo.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseTwo);

        GamePiece gamePieceThree = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseThree = new BuildPhase(gamePieceThree, new Location(1,0));
        buildPhaseThree.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseThree);

        return gameBoard;
    }

    private Tile getSecondSimpleTile(){
        Location[] locations = new Location[] {
                new Location(1, -2),
                new Location(0,-2),
                new Location(0,-1)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    @Test
    public void TigerPlacementSuccessTest() throws Exception{
        GameBoard gameBoard = getSuccessfulTigerGameBoard();

        GamePiece tigerToPlace = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
        BuildPhase buildPhaseTiger = new BuildPhase(tigerToPlace, new Location(0,-1));
        buildPhaseTiger.setBuildType(BuildType.PLACE_TIGER);
        gameBoard.doBuildPhase(buildPhaseTiger);

        assertEquals(76, gameBoard.getPlayerOneScore());
    }

    private GameBoard getSuccessfulTigerGameBoard() throws Exception {
        GameBoardImpl gameBoard = new GameBoardImpl();

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        TilePlacementPhase tilePlacementPhaseTwo = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForNuking());
        tilePlacementPhaseTwo.setTilePlacementType(TilePlacementType.NUKE);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseTwo);

        TilePlacementPhase tilePlacementPhaseThree = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSecondSimpleTile());
        tilePlacementPhaseThree.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseThree);

        TilePlacementPhase tilePlacementPhaseFour
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateSecondTileForNuking());
        tilePlacementPhaseFour.setTilePlacementType(TilePlacementType.NUKE);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseFour);

        TilePlacementPhase tilePlacementPhaseFive
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateThirdTileForNuking());
        tilePlacementPhaseFive.setTilePlacementType(TilePlacementType.NUKE);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseFive);

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(-1,0));
        buildPhaseOne.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseOne);

        return gameBoard;
    }

    @Test(expected = BuildPhaseException.class)
    public void TigerPlacementFailureTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();

        GamePiece tigerToPlace = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
        BuildPhase buildPhaseTiger = new BuildPhase(tigerToPlace, new Location(1,-1));
        buildPhaseTiger.setBuildType(BuildType.PLACE_TIGER);
        gameBoard.doBuildPhase(buildPhaseTiger);
    }

    private Tile generateTileForNuking() {
        Location[] locations = new Location[] {
                new Location(0, 0),
                new Location(0,1),
                new Location(1,0)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateSecondTileForNuking(){
        Location[] locations = new Location[] {
                new Location(1, -2),
                new Location(0,-1),
                new Location(1,-1)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateThirdTileForNuking(){
        Location[] locations = new Location[] {
                new Location(0, 0),
                new Location(1,-1),
                new Location(0,-1)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    @Test
    public void CurrentTurnTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        assertEquals(1, gameBoard.getCurrentTurn());

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(-1,0));
        buildPhaseOne.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseOne);

        assertEquals(2, gameBoard.getCurrentTurn());
    }

    @Test
    public void getPlacedHexagonsTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        Map<Location, Hexagon> placedHexagons = gameBoard.getPlacedHexagons();
        assertTrue(placedHexagons.containsKey(new Location(0,0)));
        assertTrue(placedHexagons.containsKey(new Location(1,0)));
        assertTrue(placedHexagons.containsKey(new Location(1,-1)));
        assertTrue(placedHexagons.containsKey(new Location(-1,1)));
        assertTrue(placedHexagons.containsKey(new Location(-1,0)));
    }

    @Test
    public void getPlayerScoreTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        assertEquals(0, gameBoard.getPlayerOneScore());
        assertEquals(0, gameBoard.getPlayerTwoScore());
    }

    @Test
    public void getPlayerSettlementsTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        List<Settlement> playerOneSettlements = gameBoard.getPlayerOneSettlements();
        List<Settlement> playerTwoSettlements = gameBoard.getPlayerOneSettlements();

        assertEquals(0, playerOneSettlements.size());
        assertEquals(0, playerTwoSettlements.size());

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(1,0));
        buildPhaseOne.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseOne);

        playerOneSettlements = gameBoard.getPlayerOneSettlements();
        playerTwoSettlements = gameBoard.getPlayerTwoSettlements();

        assertEquals(1, playerOneSettlements.size());
        assertEquals(0, playerTwoSettlements.size());

        TilePlacementPhase tilePlacementPhaseOne = new TilePlacementPhase(PlayerID.PLAYER_ONE, getSimpleTileToPlace());
        tilePlacementPhaseOne.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhaseOne);

        GamePiece gamePieceTwo = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseTwo = new BuildPhase(gamePieceTwo, new Location(0,1));
        buildPhaseTwo.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseTwo);

        playerOneSettlements = gameBoard.getPlayerOneSettlements();
        playerTwoSettlements = gameBoard.getPlayerTwoSettlements();

        assertEquals(1, playerOneSettlements.size());
        assertEquals(0, playerTwoSettlements.size());

        GamePiece gamePieceThree = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseThree = new BuildPhase(gamePieceThree, new Location(0,2));
        buildPhaseThree.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseThree);

        playerOneSettlements = gameBoard.getPlayerOneSettlements();
        playerTwoSettlements = gameBoard.getPlayerTwoSettlements();

        assertEquals(1, playerOneSettlements.size());
        assertEquals(1, playerTwoSettlements.size());

        GamePiece gamePieceFour = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseFour = new BuildPhase(gamePieceFour, new Location(-1,0));
        buildPhaseFour.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhaseFour);

        playerOneSettlements = gameBoard.getPlayerOneSettlements();
        playerTwoSettlements = gameBoard.getPlayerTwoSettlements();

        assertEquals(1, playerOneSettlements.size());
        assertEquals(2, playerTwoSettlements.size());
    }

    @Test
    public void getPlaceableLocationsTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        List<Location> placeableLocations = gameBoard.getPlaceableLocations();
        assertTrue(placeableLocations.contains(new Location(0,1)));
        assertTrue(placeableLocations.contains(new Location(1,1)));
        assertTrue(placeableLocations.contains(new Location(2,0)));
        assertTrue(placeableLocations.contains(new Location(2,-1)));
        assertTrue(placeableLocations.contains(new Location(2,-2)));
        assertTrue(placeableLocations.contains(new Location(1,-2)));
        assertTrue(placeableLocations.contains(new Location(0,-1)));
        assertTrue(placeableLocations.contains(new Location(-1,-1)));
        assertTrue(placeableLocations.contains(new Location(-2,0)));
        assertTrue(placeableLocations.contains(new Location(-2,1)));
        assertTrue(placeableLocations.contains(new Location(-2,2)));
        assertTrue(placeableLocations.contains(new Location(-1,2)));
    }

    @Test
    public void getNukeableVocanoLocationsTest() throws Exception{
        GameBoard gameBoard = new GameBoardImpl();
        TilePlacementPhase tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE,getSimpleTileToPlace());
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);

        List<Location> nukeableLocations = gameBoard.getNukeableVolcanoLocations();
        assertTrue(nukeableLocations.contains(new Location(0,0)));
        assertTrue(nukeableLocations.contains(new Location(1,1)));
        assertFalse(nukeableLocations.contains(new Location(-1,1)));
    }
}