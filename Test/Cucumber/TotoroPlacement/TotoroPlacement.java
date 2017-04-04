package Cucumber.TotoroPlacement;

import GameBoard.BuildPhaseHelper;
import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.SettlementException.SettlementException;
import Tile.Tile.Tile;
import TileBuilder.TileBuilder;
import TileMap.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static junit.framework.TestCase.assertEquals;

public class TotoroPlacement {

    private PlayerID firstPlayer;
    private TileMap tileMap;
    private GamePieceMap gamePieceMap;
    private BuildPhase buildPhase;
    private TileBuilder tileBuilder;
    private BuildPhaseHelper buildPhaseHelper;
    private Player activePlayer;
    private Settlement settlement;
    private Settlement secondSettlement;
    private boolean totoroBuildSucceeds;
    private boolean totoroJoinsBothSettlements;

    @Given("^an existing settlement of size 5 or more$")
    public void creatingSettlementOfSizeFiveOrMore()
            throws InvalidTileLocationException, LocationOccupiedException, LocationNotEmptyException, SettlementException {

        initializeInstances();

        Location originTileLoc1 = new Location(0,0);
        Location originTileLoc2 = new Location(0,1);
        Location originTileLoc3 = new Location(1,0);

        Tile initialTile = tileBuilder.getTileWithLocations(originTileLoc1, originTileLoc2, originTileLoc3);
        tileMap.insertTile(initialTile);

        Location adjTileLoc1 = new Location(0,2);
        Location adjTileLoc2 = new Location(1,2);
        Location adjTileLoc3 = new Location(1,1);

        Tile adjacentTile = tileBuilder.getTileWithLocations(adjTileLoc1, adjTileLoc2, adjTileLoc3);
        tileMap.insertTile(adjacentTile);

        Location northTileLoc1 = new Location(0,3);
        Location northTileLoc2 = new Location(0,4);
        Location northTileLoc3 = new Location(1,3);

        Tile northernMostTile = tileBuilder.getTileWithLocations(northTileLoc1, northTileLoc2, northTileLoc3);
        tileMap.insertTile(northernMostTile);

        findSettlementAtSpecificLocation(originTileLoc2);

        expandVillagerAtSpecificLocation(originTileLoc3, settlement);
        expandVillagerAtSpecificLocation(adjTileLoc3, settlement);
        expandVillagerAtSpecificLocation(adjTileLoc2, settlement);
        expandVillagerAtSpecificLocation(northTileLoc3, settlement);
    }

    @When("^a player places a totoro on an adjacent hex$")
    public void placingATotoroAdjacentToSettlement() throws SettlementException, LocationNotEmptyException {

        GamePiece totoroToPlace = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);
        Location totoroPlacementLocation = new Location(0, 4);

        buildPhase = new BuildPhase(totoroToPlace, totoroPlacementLocation, settlement);
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        totoroBuildSucceeds = buildPhaseHelper.attemptTotoroPlacement(buildPhase, tileMap, gamePieceMap, activePlayer);

        if(totoroBuildSucceeds){
            insertTotoroInMaps(totoroPlacementLocation, totoroToPlace);
        }
    }

    @Then("^the totoro becomes part of the settlement$")
    public void checkIfSettlementSizeIsNow6() {
       assertEquals(6, buildPhase.getSettlement().getNumberOfHexesInSettlement());
    }


    @When("^a player places a totoro on an adjacent hex that is a volcano$")
    public void placingATotoroOnAnAdjacentHexThatIsAVolcano() throws SettlementException, LocationNotEmptyException {

        GamePiece totoroToPlaceOnVolcano = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);
        Location volcanoLocation = new Location(0, 2);

        buildPhase = new BuildPhase(totoroToPlaceOnVolcano, volcanoLocation, settlement);
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        totoroBuildSucceeds = buildPhaseHelper.attemptTotoroPlacement(buildPhase, tileMap, gamePieceMap, activePlayer);

        if(totoroBuildSucceeds) {
            insertTotoroInMaps(volcanoLocation, totoroToPlaceOnVolcano);
        }
    }

    @Then("^the totoro shouldn't be added to the settlement$")
    public void settlementSizeShouldStillBe5() {
        assertEquals(5, buildPhase.getSettlement().getNumberOfHexesInSettlement());
    }

    @Given("^an existing settlement of size 4 or less$")
    public void creatingASettlementOfSize1()
            throws InvalidTileLocationException, LocationOccupiedException, LocationNotEmptyException, SettlementException {

        initializeInstances();

        Location originTileLoc1 = new Location(0,0);
        Location originTileLoc2 = new Location(0,1);
        Location originTileLoc3 = new Location(1,0);

        Tile initialTile = tileBuilder.getTileWithLocations(originTileLoc1, originTileLoc2, originTileLoc3);
        tileMap.insertTile(initialTile);

        findSettlementAtSpecificLocation(originTileLoc2);
    }

    @When("^a player places a totoro adjacent to this settlement$")
    public void placingATotoroNextToSettlement()
            throws LocationNotEmptyException, SettlementException {

        GamePiece totoroToPlaceOnVolcano = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);
        Location volcanoLocation = new Location(1, 0);

        buildPhase = new BuildPhase(totoroToPlaceOnVolcano, volcanoLocation, settlement);
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        totoroBuildSucceeds = buildPhaseHelper.attemptTotoroPlacement(buildPhase, tileMap, gamePieceMap, activePlayer);

        if(totoroBuildSucceeds) {
            insertTotoroInMaps(volcanoLocation, totoroToPlaceOnVolcano);
        }
    }

    @Then("^the totoro should not be successfully placed$")
    public void settlementSizeShouldStillBe1() {
        assertEquals(1, buildPhase.getSettlement().getNumberOfHexesInSettlement());
    }


    @Given("^a player has two settlements, with one settlement having a totoro$")
    public void createTwoSettlementsWithOriginalHavingTotoro()
            throws LocationOccupiedException, LocationNotEmptyException, SettlementException, InvalidTileLocationException {

        creatingSettlementOfSizeFiveOrMore();
        placingATotoroAdjacentToSettlement();

        secondSettlement = new Settlement();
        totoroJoinsBothSettlements = false;

        Location settlement2tile11 = new Location(0,-1);
        Location settlement2tile12 = new Location(1,-1);
        Location settlement2tile13 = new Location(1,-2);

        Tile settlement2Tile1 = tileBuilder.getTileWithLocations(settlement2tile11, settlement2tile12, settlement2tile13);
        tileMap.insertTile(settlement2Tile1);

        Location settlement2tile21 = new Location(0,-2);
        Location settlement2tile22 = new Location(1,-3);
        Location settlement2tile23 = new Location(0,-3);

        Tile settlement2Tile2 = tileBuilder.getTileWithLocations(settlement2tile21, settlement2tile22, settlement2tile23);
        tileMap.insertTile(settlement2Tile2);

        Location settlement2tile31 = new Location(1,-5);
        Location settlement2tile32 = new Location(0,-4);
        Location settlement2tile33 = new Location(1,-4);

        Tile settlement2Tile3 = tileBuilder.getTileWithLocations(settlement2tile31, settlement2tile32, settlement2tile33);
        tileMap.insertTile(settlement2Tile3);

        GamePiece standardVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);

        buildPhase = new BuildPhase(standardVillage, settlement2tile13);
        buildPhase.setBuildType(BuildType.FOUND);
        buildPhaseHelper.attemptSettlementFoundation(buildPhase, tileMap, gamePieceMap, activePlayer);
        gamePieceMap.insertAPieceAt(settlement2tile13, standardVillage);
        secondSettlement.locationIsInSettlement(settlement2tile13);
        secondSettlement.markPieceInSettlement(settlement2tile13, standardVillage);
        activePlayer.decrementVillagerCount(1);

        expandVillagerAtSpecificLocation(settlement2tile22, secondSettlement);
        expandVillagerAtSpecificLocation(settlement2tile23, secondSettlement);
        expandVillagerAtSpecificLocation(settlement2tile33, secondSettlement);
        expandVillagerAtSpecificLocation(settlement2tile32, secondSettlement);
    }

    @When("^the player attempts to add a totoro adjacent to both settlements$")
    public void placesATotoroToConnectBothSettlements()
            throws SettlementException, LocationNotEmptyException {

        GamePiece totoroToPlace = new GamePiece(firstPlayer, TypeOfPiece.TOTORO);
        Location totoroPlacementLocation = new Location(1, -1);

        buildPhase = new BuildPhase(totoroToPlace, totoroPlacementLocation, secondSettlement);
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        totoroJoinsBothSettlements = buildPhaseHelper.attemptTotoroPlacement(buildPhase, tileMap, gamePieceMap, activePlayer);

        if(totoroJoinsBothSettlements) {
            gamePieceMap.insertAPieceAt(totoroPlacementLocation, totoroToPlace);
            secondSettlement.locationIsInSettlement(totoroPlacementLocation);
            secondSettlement.markPieceInSettlement(totoroPlacementLocation, totoroToPlace);
            activePlayer.decrementTotoroCount();
        }
    }

    @Then("^the totoro should be successfully placed$")
    public void totoroShouldBeSuccessfullyPlaced() {
        assertEquals(settlement.getNumberOfHexesInSettlement(), secondSettlement.getNumberOfHexesInSettlement());
    }

    @Given("^a player has a settlement that is a tiger playground$")
    public void given()
            throws InvalidTileLocationException, LocationOccupiedException, LocationNotEmptyException, SettlementException {

        initializeInstances();

        Location level1Tile11 = new Location(0,0);
        Location level1Tile12 = new Location(0,1);
        Location level1Tile13 = new Location(1,0);

        Tile level1Tile1 = tileBuilder.getTileWithLocations(level1Tile11, level1Tile12, level1Tile13);
        tileMap.insertTile(level1Tile1);

        Location level1Tile21 = new Location(0,2);
        Location level1Tile22 = new Location(1,2);
        Location level1Tile23 = new Location(1,1);

        Tile level1Tile2 = tileBuilder.getTileWithLocations(level1Tile21, level1Tile22, level1Tile23);
        tileMap.insertTile(level1Tile2);

        Location level1Tile31 = new Location(0,3);
        Location level1Tile32 = new Location(0,4);
        Location level1Tile33 = new Location(1,3);

        Tile level1Tile3 = tileBuilder.getTileWithLocations(level1Tile31, level1Tile32, level1Tile33);
        tileMap.insertTile(level1Tile3);


        Location level2Tile11 = new Location(0,3);
        Location level2Tile12 = new Location(1,3);
        Location level2Tile13 = new Location(1,2);

        Tile level2Tile1 = tileBuilder.getTileWithLocations(level2Tile11, level2Tile12, level2Tile13);
        tileMap.insertTile(level2Tile1);

        Location level2Tile21 = new Location(0,2);
        Location level2Tile22 = new Location(1,1);
        Location level2Tile23 = new Location(0,1);

        Tile level2Tile2 = tileBuilder.getTileWithLocations(level2Tile21, level2Tile22, level2Tile23);
        tileMap.insertTile(level2Tile2);

        Location level3Tile11 = new Location(0,3);
        Location level3Tile12 = new Location(1,2);
        Location level3Tile13 = new Location(0,2);

        Tile level3Tile1 = tileBuilder.getTileWithLocations(level3Tile11, level3Tile12, level3Tile13);
        tileMap.insertTile(level3Tile1);

        findSettlementAtSpecificLocation(level1Tile13);

        expandVillagerAtSpecificLocation(level1Tile12, settlement);
        expandVillagerAtSpecificLocation(level1Tile23, settlement);
        expandVillagerAtSpecificLocation(level2Tile21, settlement);

        GamePiece tigerToPlace = new GamePiece(firstPlayer, TypeOfPiece.TIGER);
        Location tigerPlacementLocation = new Location(1, 2);

        boolean tigerSuccessfullyPlaced;

        buildPhase = new BuildPhase(tigerToPlace, tigerPlacementLocation, settlement);
        buildPhase.setBuildType(BuildType.PLACE_TIGER);

        tigerSuccessfullyPlaced = buildPhaseHelper.attemptTigerPlacement(buildPhase, tileMap, gamePieceMap, activePlayer);

        if(tigerSuccessfullyPlaced) {
            gamePieceMap.insertAPieceAt(tigerPlacementLocation, tigerToPlace);
            settlement.locationIsInSettlement(tigerPlacementLocation);
            settlement.markPieceInSettlement(tigerPlacementLocation, tigerToPlace);
            activePlayer.decrementTigerCount();
        }

        expandVillagerAtSpecificLocation(level2Tile12, settlement);
    }

    @When("^the player places a totoro adjacent to that settlement$")
    public void addingTotoroToTigerPlayGround() throws SettlementException, LocationNotEmptyException {
        placingATotoroAdjacentToSettlement();
    }

    @Then("^the totoro should become part of the settlement$")
    public void settlementSizeShouldHaveTigerAndTotoro() {
        assertEquals(7, settlement.getNumberOfHexesInSettlement());
    }

    private void initializeInstances() {
        firstPlayer = PlayerID.PLAYER_ONE;
        tileMap = new HexagonMap();
        gamePieceMap = new GamePieceMap();
        buildPhaseHelper = new BuildPhaseHelper();
        tileBuilder = new TileBuilder();
        activePlayer = new Player(firstPlayer);
        settlement = new Settlement();
        totoroBuildSucceeds = false;
    }

    private void findSettlementAtSpecificLocation(Location location)
            throws SettlementException, LocationNotEmptyException {

        GamePiece standardVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);

        buildPhase = new BuildPhase(standardVillage, location);
        buildPhase.setBuildType(BuildType.FOUND);
        buildPhaseHelper.attemptSettlementFoundation(buildPhase, tileMap, gamePieceMap, activePlayer);
        gamePieceMap.insertAPieceAt(location, standardVillage);
        settlement.locationIsInSettlement(location);
        settlement.markPieceInSettlement(location, standardVillage);
    }

    private void expandVillagerAtSpecificLocation(Location location, Settlement settlement)
            throws LocationNotEmptyException, SettlementException {

        GamePiece standardVillage = new GamePiece(firstPlayer, TypeOfPiece.VILLAGER);

        buildPhase = new BuildPhase(standardVillage, location, settlement);
        buildPhase.setBuildType(BuildType.EXPAND);
        buildPhaseHelper.attemptSettlementExpansion(buildPhase, tileMap, gamePieceMap, activePlayer);
        gamePieceMap.insertAPieceAt(location, standardVillage);
        settlement.locationIsInSettlement(location);
        settlement.markPieceInSettlement(location, standardVillage);
    }

    private void insertTotoroInMaps(Location volcanoLocation, GamePiece totoroToPlaceOnVolcano)
            throws LocationNotEmptyException, SettlementException {

        gamePieceMap.insertAPieceAt(volcanoLocation, totoroToPlaceOnVolcano);
        settlement.locationIsInSettlement(volcanoLocation);
        settlement.markPieceInSettlement(volcanoLocation, totoroToPlaceOnVolcano);
    }
}