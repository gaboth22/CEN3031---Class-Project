package Cucumber.TigerPlacement;

import GameBoard.GameBoardImpl;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TigerPlacement {
    private GameBoardImpl gameBoard;
    private TilePlacementPhase tilePlacementPhase;
    private TileBuilder tileBuilder;
    private BuildPhase buildPhase;
    private PlayerID firstPlayer;
    private boolean tigerPlacementFailed;

    @Given("^an existing settlement with an adjacent hex on level 3 or above$")
    public void creatingSettlementWithAdjacentTileLevel3ForTiger() throws Exception {
        initializeInstances();

        placeTilesToCreateAThirdLevel();

        Location settlement1 = new Location(1, 0);
        findSettlementAtSpecificLocation(settlement1);
    }

    @When("^a player attempts to place a tiger adjacent to the settlement on level 3 or greater$")
    public void attemptingToPlaceATigerOnLevel3() throws Exception {
        Location locationToPlaceTiger = new Location(0, 1);
        tigerPlacementFailed = attemptToPlaceTigerAtLocation(locationToPlaceTiger);
    }

    @Then("^the settlement should become a tiger playground$")
    public void tigerPlacementShouldBeSuccessful() {
        assertFalse(tigerPlacementFailed);
    }

    @When("^a player attempts to place the tiger on the adjacent settlement but the location is a volcano$")
    public void placingATigerOnAVolcano() throws Exception {
        Location placingTigerOnVolcano = new Location(0, 0);
        tigerPlacementFailed = attemptToPlaceTigerAtLocation(placingTigerOnVolcano);
    }

    @Then("^the tiger should not be successfully placed$")
    public void placementAttemptShouldFail() {
        assertTrue(tigerPlacementFailed);
    }

    @Given("^a settlement with no adjacent tile level three or greater$")
    public void level1SettlementWithNoAdjacentHexOnLevel3() throws Exception {
        initializeInstances();

        Location settlement1 = new Location(-1, 1);
        findSettlementAtSpecificLocation(settlement1);
    }

    @When("^a player attempts to join a tiger to that settlement$")
    public void attemptingToPlaceATigerOnLevel1() throws Exception {
        Location locationToPlaceTiger = new Location(-1, 0);
        tigerPlacementFailed = attemptToPlaceTigerAtLocation(locationToPlaceTiger);
    }

    @Then("^the settlement should not become a tiger playground$")
    public void tigerPlacementShouldFail() {
        assertTrue(tigerPlacementFailed);
    }

    @Given("^a settlement that already contains a tiger$")
    public void createsSettlementContainingTiger() throws Exception {
        initializeInstances();
        placeTilesToCreateAThirdLevel();

        Location settlement1 = new Location(1, 0);
        findSettlementAtSpecificLocation(settlement1);

        Location locationToPlaceFirstTiger = new Location(0, 1);
        attemptToPlaceTigerAtLocation(locationToPlaceFirstTiger);
    }

    @When("^a player attempts to add a tiger to the same settlement$")
    public void attemptsToAddSecondTigerToSettlement() throws Exception {
        Location locationToPlaceSecondTiger = new Location(-1, 1);
        tigerPlacementFailed = attemptToPlaceTigerAtLocation(locationToPlaceSecondTiger);
    }

    @Then("^the tiger should not be added to that settlement$")
    public void tigerPlacementFails() {
        assertTrue(tigerPlacementFailed);
    }

    @Given("^a settlement that contains a totoro and has an adjacent hex on level 3 or greater$")
    public void createsSettlementContainingTotoro() throws Exception {
        initializeInstances();

        placeTilesToCreateAThirdLevel();

        Location tile11 = new Location(1, 2);
        Location tile12 = new Location(2, 1);
        Location tile13 = new Location(1, 1);
        Location tile21 = new Location(3, -1);
        Location tile22 = new Location(2, 0);
        Location tile23 = new Location(2, -1);

        createAndPlaceTileAt(tile11, tile12, tile13);
        createAndPlaceTileAt(tile21, tile22, tile23);

        Location settlement1 = new Location(2, -1);
        Location settlement2 = new Location(2, 0);
        Location settlement3 = new Location(2, 1);
        Location settlement4 = new Location(1, 1);
        Location settlement5 = new Location(0, 2);

        findSettlementAtSpecificLocation(settlement1);
        findSettlementAtSpecificLocation(settlement2);
        findSettlementAtSpecificLocation(settlement3);
        findSettlementAtSpecificLocation(settlement4);
        findSettlementAtSpecificLocation(settlement5);

        GamePiece totoroToPlace = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);
        Location locationToPlaceTotoro = new Location(0, 1);

        buildPhase = new BuildPhase(totoroToPlace, locationToPlaceTotoro);
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        gameBoard.doBuildPhase(buildPhase);
    }

    @When("^a player tries to add a tiger to the settlement containing the totoro$")
    public void addsTigerToSettlementContainingTotoro() throws Exception {
        Location locationToPlaceTiger = new Location(-1, 1);
        tigerPlacementFailed = attemptToPlaceTigerAtLocation(locationToPlaceTiger);
    }

    @Then("^the settlement should contain both a totoro and a tiger$")
    public void tigerPlaygroundSucceeds() {
        assertFalse(tigerPlacementFailed);
    }

    @Given("^a player has two settlements, with one settlement having a tiger$")
    public void createsTwoSettlementsOneContainingATiger() throws Exception {
        initializeInstances();

        placeTilesToCreateAThirdLevel();

        Location settlement1 = new Location(1, 0);
        Location settlement2 = new Location(-1, 0);
        Location locationToPlaceFirstTiger = new Location(0, 1);

        findSettlementAtSpecificLocation(settlement1);
        attemptToPlaceTigerAtLocation(locationToPlaceFirstTiger);

        findSettlementAtSpecificLocation(settlement2);
    }

    @When("^the player attempts to add a tiger adjacent to both settlements$")
    public void attemptsToJoinBothSettlementsWithATiger() throws Exception{
        Location locationToPlaceSecondTiger = new Location(-1, 1);
        tigerPlacementFailed = attemptToPlaceTigerAtLocation(locationToPlaceSecondTiger);
    }

    @Then("^the tiger should be placed joining both settlements$")
    public void tigerSuccessfullyJoinsBothSettlements() {
        assertFalse(tigerPlacementFailed);
    }

    private void initializeInstances() {
        gameBoard = new GameBoardImpl();
        tileBuilder = new TileBuilder();
        firstPlayer = PlayerID.PLAYER_ONE;
        tigerPlacementFailed = false;
    }

    private void createAndPlaceTileAt(Location l1, Location l2, Location l3) throws Exception {
        Tile adjacentTile = tileBuilder.getTileWithLocations(l1, l2, l3);
        tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, adjacentTile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void createAndNukeTileAt(Location l1, Location l2, Location l3) throws Exception {
        Tile nukingTile = tileBuilder.getTileWithLocations(l1, l2, l3);
        tilePlacementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, nukingTile);
        tilePlacementPhase.setTilePlacementType(TilePlacementType.NUKE);
        gameBoard.doTilePlacementPhase(tilePlacementPhase);
    }

    private void findSettlementAtSpecificLocation(Location location) throws Exception {
        GamePiece standardVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);

        buildPhase = new BuildPhase(standardVillage, location);
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);
    }

    private boolean attemptToPlaceTigerAtLocation(Location location) throws Exception {
        GamePiece tigerToPlace = new GamePiece(firstPlayer, TypeOfPiece.TIGER);

        buildPhase = new BuildPhase(tigerToPlace, location);
        buildPhase.setBuildType(BuildType.PLACE_TIGER);

        try {
            gameBoard.doBuildPhase(buildPhase);
        }
        catch(BuildPhaseException e) {
            tigerPlacementFailed = true;
        }

        return tigerPlacementFailed;
    }

    private void placeTilesToCreateAThirdLevel() throws Exception {
        Location tile11 = new Location(-1, 2);
        Location tile12 = new Location(0, 2);
        Location tile13 = new Location(0, 1);
        Location tile21 = new Location(0, -2);
        Location tile22 = new Location(0, -1);
        Location tile23 = new Location(1, -2);

        createAndPlaceTileAt(tile11, tile12, tile13);
        createAndPlaceTileAt(tile21, tile22, tile23);

        Location level2Tile11 = new Location(-1, 2);
        Location level2Tile12 = new Location(0, 1);
        Location level2Tile13 = new Location(-1, 1);
        Location level2Tile21 = new Location(0, 0);
        Location level2Tile22 = new Location(0, -1);
        Location level2Tile23 = new Location(1, -1);

        createAndNukeTileAt(level2Tile11, level2Tile12, level2Tile13);
        createAndNukeTileAt(level2Tile21, level2Tile22, level2Tile23);

        Location level3Tile11 = new Location(0, 0);
        Location level3Tile12 = new Location(0, 1);
        Location level3Tile13 = new Location(-1, 1);

        createAndNukeTileAt(level3Tile11, level3Tile12, level3Tile13);
    }
}