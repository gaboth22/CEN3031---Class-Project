package Cucumber.TotoroPlacement;

import GameBoard.GameBoardImpl;
import GamePieceMap.*;
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

public class TotoroPlacement {

    private GameBoardImpl gameBoard;
    private TilePlacementPhase tilePlacementPhase;
    private TileBuilder tileBuilder;
    private BuildPhase buildPhase;
    private PlayerID firstPlayer;
    private boolean totoroFailed;

    @Given("^an existing settlement of size 5 or more$")
    public void creatingSettlementOfSizeFiveOrMore() throws Exception {
        initializeInstances();

        Location tile11 = new Location(0,2);
        Location tile12 = new Location(-1,2);
        Location tile13 = new Location(0,1);

        createAndPlaceTileAt(tile11, tile12, tile13);

        Location settlementFoundation = new Location(-1, 0);
        findSettlementAtSpecificLocation(settlementFoundation);

        Location expansion1 = new Location(-1,1);
        Location expansion2 = new Location(0,1);
        Location expansion3 = new Location(-1,2);
        Location expansion4 = new Location(1,0);

        findSettlementAtSpecificLocation(expansion1);
        findSettlementAtSpecificLocation(expansion2);
        findSettlementAtSpecificLocation(expansion3);
        findSettlementAtSpecificLocation(expansion4);

    }

    @When("^a player places a totoro on an adjacent hex$")
    public void placingATotoroAdjacentToSettlement() throws Exception {
        Location locationToPlaceTotoro = new Location(1,-1);
        totoroFailed = attemptToPlaceTotoroAtLocation(locationToPlaceTotoro);
    }

    @Then("^the totoro should be successfully placed$")
    public void checkIfSettlementSizeIsNow6() {
        assertFalse(totoroFailed);
    }

    @When("^a player places a totoro on an adjacent hex that is a volcano$")
    public void placingATotoroOnAnAdjacentHexThatIsAVolcano() throws Exception {
        Location locationToPlaceTotoro = new Location(0,0);
        totoroFailed = attemptToPlaceTotoroAtLocation(locationToPlaceTotoro);
    }

    @Then("^the totoro shouldn't be added to the settlement$")
    public void settlementSizeShouldStillBe5() {
        assertTrue(totoroFailed);
    }

    @Given("^an existing settlement of size 4 or less$")
    public void creatingASettlementOfSize1() throws Exception {
        initializeInstances();

        Location settlementFoundation = new Location(-1, 0);
        findSettlementAtSpecificLocation(settlementFoundation);
    }

    @When("^a player places a totoro adjacent to this settlement$")
    public void placingATotoroNextToSettlement() throws Exception {
        Location locationToPlaceTotoro = new Location(0,0);
        totoroFailed = attemptToPlaceTotoroAtLocation(locationToPlaceTotoro);
    }

    @Then("^the totoro should not be successfully placed$")
    public void settlementSizeShouldStillBe1() {
        assertTrue(totoroFailed);
    }

    @Given("^a player has two settlements, with one settlement having a totoro$")
    public void createTwoSettlementsWithOriginalHavingTotoro() throws Exception {

        initializeInstances();

        Location tile11 = new Location(-3,2);
        Location tile12 = new Location(-2,2);
        Location tile13 = new Location(-2,1);
        Location tile21 = new Location(0,2);
        Location tile22 = new Location(-1,2);
        Location tile23 = new Location(0,1);
        Location tile31 = new Location(3,-3);
        Location tile32 = new Location(3,-2);
        Location tile33 = new Location(2,-2);
        Location tile41 = new Location(3, -1);
        Location tile42 = new Location(2, 0);
        Location tile43 = new Location(2, -1);

        createAndPlaceTileAt(tile11, tile12, tile13);
        createAndPlaceTileAt(tile21, tile22, tile23);
        createAndPlaceTileAt(tile31, tile32, tile33);
        createAndPlaceTileAt(tile41, tile42, tile43);

        Location settlement11 = new Location(0,1);
        Location settlement12 = new Location(-2,1);
        Location settlement13 = new Location(-2,2);
        Location settlement14 = new Location(-1,1);
        Location settlement15 = new Location(-1,2);

        findSettlementAtSpecificLocation(settlement11);
        findSettlementAtSpecificLocation(settlement12);
        findSettlementAtSpecificLocation(settlement13);
        findSettlementAtSpecificLocation(settlement14);
        findSettlementAtSpecificLocation(settlement15);

        Location totoroSettlement1Location = new Location(-1,0);
        attemptToPlaceTotoroAtLocation(totoroSettlement1Location);

        Location settlement21 = new Location(2,-2);
        Location settlement22 = new Location(3,-2);
        Location settlement23 = new Location(2,-1);
        Location settlement24 = new Location(1,-1);
        Location settlement25 = new Location(2,0);

        findSettlementAtSpecificLocation(settlement21);
        findSettlementAtSpecificLocation(settlement22);
        findSettlementAtSpecificLocation(settlement23);
        findSettlementAtSpecificLocation(settlement24);
        findSettlementAtSpecificLocation(settlement25);
    }

    @When("^the player attempts to add a totoro adjacent to both settlements$")
    public void placesATotoroToConnectBothSettlements() throws Exception {
        Location totoroSettlement2Location = new Location(1, 0);
        totoroFailed = attemptToPlaceTotoroAtLocation(totoroSettlement2Location);
    }

    @Then("^the totoro should be placed joining both settlements$")
    public void totoroShouldBeSuccessfullyPlaced() {
        assertFalse(totoroFailed);
    }

    @Given("^a player has a settlement that is a tiger playground and is size 5 or greater$")
    public void createSettlementSize5WithTigerPlayGround() throws Exception {

        initializeInstances();

        Location tile11 = new Location(-1,2);
        Location tile12 = new Location(0,2);
        Location tile13 = new Location(0,1);
        Location tile21 = new Location(2,-1);
        Location tile22 = new Location(2,0);
        Location tile23 = new Location(3,-1);
        Location tile31 = new Location(2, 1);
        Location tile32 = new Location(1, 1);
        Location tile33 = new Location(1, 2);

        createAndPlaceTileAt(tile11, tile12, tile13);
        createAndPlaceTileAt(tile21, tile22, tile23);
        createAndPlaceTileAt(tile31, tile32, tile33);

        Location level2Tile11 = new Location(2, -1);
        Location level2Tile12 = new Location(1, 0);
        Location level2Tile13 = new Location(1, -1);
        Location level2Tile21 = new Location(0, 0);
        Location level2Tile22 = new Location(0, 1);
        Location level2Tile23 = new Location(-1, 1);

        createAndNukeTileAt(level2Tile11, level2Tile12, level2Tile13);
        createAndNukeTileAt(level2Tile21, level2Tile22, level2Tile23);

        Location level3Tile11 = new Location(0, 0);
        Location level3Tile12 = new Location(1, 0);
        Location level3Tile13 = new Location(1, -1);

        createAndNukeTileAt(level3Tile11, level3Tile12, level3Tile13);

        Location settlement1 = new Location(3,-1);
        Location settlement2 = new Location(2,0);
        Location settlement3 = new Location(1,1);
        Location settlement4 = new Location(1,2);
        Location settlement5 = new Location(0,2);

        Location tigerLocation = new Location(1,0);

        findSettlementAtSpecificLocation(settlement1);
        findSettlementAtSpecificLocation(settlement2);
        findSettlementAtSpecificLocation(settlement3);
        findSettlementAtSpecificLocation(settlement4);
        findSettlementAtSpecificLocation(settlement5);

        attemptToPlaceTigerAtLocation(tigerLocation);
    }

    @When("^the player places a totoro adjacent to that settlement$")
    public void addingTotoroToTigerPlayGround() throws Exception {
        Location totoroPlacingLocation = new Location(1, -1);
        totoroFailed = attemptToPlaceTotoroAtLocation(totoroPlacingLocation);
    }

    @Then("^the totoro should become part of the settlement$")
    public void settlementSizeShouldHaveTigerAndTotoro() {
        assertFalse(totoroFailed);
    }

    private void initializeInstances() {
        gameBoard = new GameBoardImpl();
        tileBuilder = new TileBuilder();
        firstPlayer = PlayerID.PLAYER_ONE;
        totoroFailed = false;
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

    private boolean attemptToPlaceTotoroAtLocation(Location location) throws Exception {
        GamePiece totoroToPlace = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);

        buildPhase = new BuildPhase(totoroToPlace, location);
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        try{
            gameBoard.doBuildPhase(buildPhase);
        }
        catch(BuildPhaseException e) {
            totoroFailed = true;
        }

        return totoroFailed;
    }

    private void attemptToPlaceTigerAtLocation(Location location) throws Exception {
        GamePiece tigerToPlace = new GamePiece(firstPlayer, TypeOfPiece.TIGER);

        buildPhase = new BuildPhase(tigerToPlace, location);
        buildPhase.setBuildType(BuildType.PLACE_TIGER);
        gameBoard.doBuildPhase(buildPhase);
    }
}