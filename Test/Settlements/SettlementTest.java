package Settlements;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Movement.AxialMovement;
import Player.PlayerID;
import Location.Location;
import Settlements.Creation.Settlement;
import Settlements.SettlementException.IncorrectPlayerException;
import Settlements.SettlementException.NotAdjacentToSettlementException;
import Settlements.SettlementException.PieceAlreadyInSettlementException;
import Settlements.SettlementException.SettlementException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class SettlementTest {

    Settlement settlement;
    AxialMovement coordinateSystem;
    AdjacentLocationArrayGetter adjacent;

    Location locationToAdd;
    GamePiece gamePiece;

    Set<Location> testSet;

    @Before
    public void setUp() throws Exception {
        coordinateSystem = new AxialMovement();
        settlement = new Settlement();
    }

    void addThreeVillagersToSettlement() throws SettlementException {
        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        locationToAdd = new Location(0,0);
        settlement.markPieceInSettlement(locationToAdd, villager);

        locationToAdd = new Location(0,1);
        settlement.markPieceInSettlement(locationToAdd, villager);

        locationToAdd = new Location(0,2);
        settlement.markPieceInSettlement(locationToAdd, villager);
    }

    @After
    public void tearDown() throws Exception {
        settlement = null;
        coordinateSystem = null;
    }

    @Test
    public void anEmptySettlementHasNoTotoroOrTigerSanctuary() throws Exception {
        Assert.assertFalse(settlement.hasTigerPlayground());
        Assert.assertFalse(settlement.hasTotoroSanctuary());
    }

    @Test
    public void addingAdjacentSettlersShouldBeValid() throws Exception {
        addThreeVillagersToSettlement();
        thenTheLocationsShouldBeInTheSettlement();
    }

    private void thenTheLocationsShouldBeInTheSettlement() {
        Assert.assertTrue(settlement.locationIsInSettlement(new Location(0,0)));
        Assert.assertTrue(settlement.locationIsInSettlement(new Location(0,1)));
        Assert.assertTrue(settlement.locationIsInSettlement(new Location(0,2)));
    }

    @Test
    public void theFirstGamePieceCanBeAddedAnywhere() throws Exception {
        givenIWantToPlaceATigerAt(new Location(0,0));
        whenIAddThePieceToTheSettlement();
    }

    @Test(expected = NotAdjacentToSettlementException.class)
    public void theNextPiecesMustBeAdjacentToTheSettlement() throws Exception {
        givenIWantToPlaceAVillagerAt(new Location(0,0));
        whenIAddThePieceToTheSettlement();
        givenIWantToPlaceAVillagerAt(new Location(5,5));
        whenIAddThePieceToTheSettlement();
    }

    @Test(expected = IncorrectPlayerException.class)
    public void allPiecesMustBeTheSamePlayerID() throws Exception {
        addThreeVillagersToSettlement();
        givenIWantToPlaceAVillagerAt(new Location(1,0), PlayerID.PLAYER_TWO);
        whenIAddThePieceToTheSettlement();
    }

    @Test(expected = PieceAlreadyInSettlementException.class)
    public void aLocationCannotBeAddedTwice() throws Exception {
        addThreeVillagersToSettlement();
        givenIWantToPlaceATigerAt(new Location(0,0));
        whenIAddThePieceToTheSettlement();
    }

    @Test
    public void aTotoroCanBeAddedToASettlement() throws Exception {
        addThreeVillagersToSettlement();
        givenIWantToPlaceATotoroAt(new Location(0,3));
        whenIAddThePieceToTheSettlement();
        thenTheTotoroShouldBeAt(new Location(0,3));
    }

    private void thenTheTotoroShouldBeAt(Location location) {
        Assert.assertTrue(settlement.hasTotoroSanctuary());
        Assert.assertEquals(TypeOfPiece.TOTORO, settlement.getTypeOfPieceAt(location));
    }

    @Test
    public void getSetOfLocationsInSettlementShouldReturnAllLocations() throws Exception {
        addThreeVillagersToSettlement();
        thenTheSetShouldBeValid();
        andTheSetShouldContainNoOtherLocations();
    }

    private void thenTheSetShouldBeValid() {
        Set<Location> set = settlement.getSetOfLocationsInSettlement();
        Assert.assertTrue(set.contains(new Location(0,0)));
        Assert.assertTrue(set.contains(new Location(0,1)));
        Assert.assertTrue(set.contains(new Location(0,2)));
    }

    private void andTheSetShouldContainNoOtherLocations() {
        Set<Location> set = settlement.getSetOfLocationsInSettlement();
        Assert.assertFalse(set.contains(new Location(0,3)));
    }

    @Test
    public void changesToTheReturnedSetAreNotReflectedInTheSettlement() throws Exception {
        addThreeVillagersToSettlement();

    }

    private Set<Location> whenIChangeTheReturnedSet() {
        testSet = settlement.getSetOfLocationsInSettlement();
        testSet.remove(new Location(0,0));
        testSet.remove(new Location(0,2));
        return testSet;
    }

    private void thenANewSetReturnedByTheSettlementWillNotBeEqualToTheChangedSet() {
        Set<Location> originalSet = settlement.getSetOfLocationsInSettlement();
        Assert.assertFalse(originalSet.equals(testSet));
    }

    @Test
    public void aTigerCanBeAddedToASettlement() throws Exception {
        givenIWantToPlaceATigerAt(new Location(0,0));
        whenIAddThePieceToTheSettlement();
        thenTheTigerShouldBeAt(new Location(0,0));
    }

    private void thenTheTigerShouldBeAt(Location location) {
        Assert.assertTrue(settlement.hasTigerPlayground());
        Assert.assertEquals(TypeOfPiece.TIGER, settlement.getTypeOfPieceAt(location));
    }

    @Test
    public void addingThreeTotorosShouldIncrementTheCounter() throws Exception {
        addThreeVillagersToSettlement();
        thenTheNumberOfVillagersShouldBeThree();
    }

    private void thenTheNumberOfVillagersShouldBeThree() {
        Assert.assertEquals(3, settlement.getNumberOfHexesInSettlement());
    }

    @Test
    public void theSettlementOwnerShouldBeCorrect() throws Exception {
        givenIWantToPlaceAVillagerAt(new Location(5,5), PlayerID.PLAYER_TWO);
        whenIAddThePieceToTheSettlement();
        thenThePlayerIdShouldBe(PlayerID.PLAYER_TWO);
    }

    private void thenThePlayerIdShouldBe(PlayerID playerID) {
        Assert.assertEquals(playerID, settlement.getSettlementOwner());
    }

    @Test
    public void anEmptySettlementShouldHaveNoOwner() {
        thenThePlayerIdShouldBe(null);
    }

    @Test
    public void settlementsWithSameInsertionShouldBeEqual() throws Exception {
        Settlement settlementOne = new Settlement();
        Settlement settlementTwo = new Settlement();
        locationToAdd = new Location(0,0);
        settlementOne.markPieceInSettlement(locationToAdd, new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER));
        locationToAdd = new Location(0,0);
        settlementTwo.markPieceInSettlement(locationToAdd, new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER));
        Assert.assertEquals(settlementOne, settlementTwo);
    }

    @Test
    public void settlementsWithoutSameInsertionShouldNotEqual() throws Exception {
        Settlement settlementOne = new Settlement();
        Settlement settlementTwo = new Settlement();
        locationToAdd = new Location(0,0);
        settlementOne.markPieceInSettlement(locationToAdd, new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER));
        locationToAdd = new Location(0,0);
        settlementTwo.markPieceInSettlement(locationToAdd, new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER));

        locationToAdd = new Location(0,1);
        settlementTwo.markPieceInSettlement(locationToAdd, new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        Assert.assertNotEquals(settlementOne, settlementTwo);
    }

    @Test
    public void emptySettlementsShouldBeEqual() throws Exception {
        Settlement settlement = new Settlement();
        Settlement settlementTwo = new Settlement();
        Assert.assertEquals(settlement, settlementTwo);
    }

    private void givenIWantToPlaceAVillagerAt(Location location, PlayerID playerID) {
        locationToAdd = location;
        gamePiece = new GamePiece(playerID, TypeOfPiece.VILLAGER);
    }

    private void givenIWantToPlaceAVillagerAt(Location location) {
        locationToAdd = location;
        gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
    }

    private void givenIWantToPlaceATotoroAt(Location location) {
        locationToAdd = location;
        gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
    }

    private void givenIWantToPlaceATigerAt(Location location) {
        locationToAdd = location;
        gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
    }

    private void whenIAddThePieceToTheSettlement() throws SettlementException {
        settlement.markPieceInSettlement(locationToAdd, gamePiece);
    }
}
