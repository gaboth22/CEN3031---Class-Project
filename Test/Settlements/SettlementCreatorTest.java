package Settlements;

import GamePieceMap.*;
import Location.Location;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import org.junit.*;

public class SettlementCreatorTest {

    GamePieceMap pieceMap;

    Location locationToCheck;

    @Before
    public void setUpPieceMap() throws Exception {
        pieceMap = new GamePieceMap();

        Location[] listOfPlayerOneLocations = listOfPlayerOneLocations();
        GamePiece[] listOfPlayerOneGamePieces = listOfPlayerOneGamePieces();
        insertPlayerPieces(listOfPlayerOneLocations, listOfPlayerOneGamePieces);

        Location[] listOfPlayerTwoLocations = listOfPlayerTwoLocations();
        GamePiece[] listOfPlayerTwoGamePieces = listOfPlayerTwoGamePieces();
        insertPlayerPieces(listOfPlayerTwoLocations, listOfPlayerTwoGamePieces);
    }

    @Test
    public void shouldReturnSettlementOfSizeZeroAtEmptyLocation() {
        locationToCheck = new Location(1,0);
        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);
        Assert.assertEquals(0, settlement.getNumberOfHexesInSettlement());
    }

    @Test
    public void shouldReturnSettlementOfSizeThreeAtOriginSettlement() {
        locationToCheck = new Location(0,0);
        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);
        Assert.assertEquals(3, settlement.getNumberOfHexesInSettlement());
    }

    @Test
    public void shouldReturnSettlementOfSizeSix() {
        locationToCheck = new Location(6,2);
        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);
        Assert.assertEquals(6, settlement.getNumberOfHexesInSettlement());
    }

    @Test
    public void shouldGiveValidTotoroAndTigerLocation() {
        locationToCheck = new Location(3,-3);
        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        Assert.assertEquals(TypeOfPiece.TOTORO, settlement.getTypeOfPieceAt(locationToCheck));

        locationToCheck = new Location(2,-2);
        Assert.assertEquals(TypeOfPiece.TIGER, settlement.getTypeOfPieceAt(locationToCheck));
    }

    @Test
    public void differentLocationsWithTheSameSettlementShouldGiveTheSameSettlement() {
        locationToCheck = new Location(3,-3);
        Settlement settlementOne = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        locationToCheck = new Location(2,-2);
        Settlement settlementTwo = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        Assert.assertEquals(settlementOne, settlementTwo);
    }

    @Test
    public void differentSettlementsInConflictedAreasShouldGiveTheSameSettlement() {
        locationToCheck = new Location(4,3);
        Settlement settlementOne = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        locationToCheck = new Location(3,4);
        Settlement settlementTwo = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        Assert.assertEquals(settlementOne, settlementTwo);
    }

    @Test
    public void emptyLocationsAreEqual() {
        locationToCheck = new Location(0,4);
        Settlement settlementOne = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        locationToCheck = new Location(0,5);
        Settlement settlementTwo = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        Assert.assertEquals(settlementOne, settlementTwo);
    }

    @Test
    public void emptyLocationIsNotEqualToSettlement() {
        locationToCheck = new Location(0,0);
        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        locationToCheck = new Location(-1,-1);
        Settlement settlement1 = SettlementCreator.getSettlementAt(pieceMap, locationToCheck);

        Assert.assertNotEquals(settlement, settlement1);
    }

    @Test
    public void settlementReturnedShouldBeEqualToConstructedSettlement() throws Exception {
        Settlement settlement = buildVillageFour();
        locationToCheck = new Location(2,-2);

        Assert.assertEquals(settlement, SettlementCreator.getSettlementAt(pieceMap, locationToCheck));
    }

    private Settlement buildVillageFour() throws Exception {
        GamePiece[] pieces = gamePieceVillageFour();
        Location[] locations = locationVillageFour();

        Settlement settlement = new Settlement();
        for(int i = 0; i < pieces.length; i++) {
            settlement.markPieceInSettlement(locations[i], pieces[i]);
        }
        return settlement;
    }

    private GamePiece[] gamePieceVillageFour() {
        PlayerID playerID = PlayerID.PLAYER_TWO;
        return new GamePiece[] {
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.TOTORO),
                new GamePiece(playerID, TypeOfPiece.TIGER)
        };
    }

    private Location[] locationVillageFour() {
        return new Location[]{
            new Location(2, 1),
            new Location(2, 0),
            new Location(3, -1),
            new Location(2, -1),
            new Location(3, -2),
            new Location(3, -3),
            new Location(2, -2)
        };
    }

    @Test
    public void villageFiveShouldHaveCorrectPositions() throws Exception {
        Settlement settlement = SettlementCreator.getSettlementAt(pieceMap, new Location(-5,-5));

        Assert.assertEquals(TypeOfPiece.VILLAGER, settlement.getTypeOfPieceAt(new Location(-5,-5)));
        Assert.assertEquals(TypeOfPiece.TIGER, settlement.getTypeOfPieceAt(new Location(-4, -5)));
    }

    @After
    public void tearDown() throws Exception {
        pieceMap = null;
    }

    private void insertPlayerPieces(Location[] locations, GamePiece[] pieces) throws Exception {
        for(int i = 0; i < locations.length; i++) {
            pieceMap.insertAPieceAt(locations[i], pieces[i]);
        }
    }

    private Location[] listOfPlayerOneLocations() {
        return new Location[] {
                //Village One: Three Villagers
                new Location(0,0),
                new Location(0,1),
                new Location(0,2),

                //Village Two: Five Villagers, One Totoro
                new Location(3,3),
                new Location(3,4),
                new Location(4,3),
                new Location(4,4),
                new Location(5,3),
                new Location(6,2)
        };
    }

    private GamePiece[] listOfPlayerOneGamePieces() {
        PlayerID playerID = PlayerID.PLAYER_ONE;
        return new GamePiece[] {
                //Village One: Three Villagers
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Two: Five Villagers, One Totoro
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.TOTORO)
        };
    }

    private Location[] listOfPlayerTwoLocations() {
        return new Location[] {
                //Village One: Two Villagers
                new Location(5,4),
                new Location(6,3),

                //Village Two: Two Villagers
                new Location(3,2),
                new Location(4,2),

                //Village Three: One Villager
                new Location(2,4),

                //Village Four: Five Villagers, One Totoro, One Tiger
                new Location(2,1),
                new Location(2,0),
                new Location(3,-1),
                new Location(2,-1),
                new Location(3,-2),
                new Location(3,-3),
                new Location(2,-2),

                //Village Five: One Villager, One Tiger
                new Location(-5,-5),
                new Location(-4,-5)
        };
    }

    private GamePiece[] listOfPlayerTwoGamePieces() {
        PlayerID playerID = PlayerID.PLAYER_TWO;
        return new GamePiece[] {
                //Village One: Two Villagers
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Two: Two Villagers
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Three: One Villager
                new GamePiece(playerID, TypeOfPiece.VILLAGER),

                //Village Four: Five Villagers, One Totoro, One Tiger
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.TOTORO),
                new GamePiece(playerID, TypeOfPiece.TIGER),

                //Village Five:
                new GamePiece(playerID, TypeOfPiece.VILLAGER),
                new GamePiece(playerID, TypeOfPiece.TIGER)
        };
    }

}
