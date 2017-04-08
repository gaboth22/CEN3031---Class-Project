package Settlements.Helper;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Location.Location;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class AdjacentLocationsToSettlementTest {

    private Settlement testSettlement;
    private Location[] locationsAdjacent;

    @Test
    public void theLocationsAdjacentToAnEmptySettlementIsEmpty() {
        testSettlement = new Settlement();
        whenIGetTheAdjacentLocations();
        Assert.assertEquals(0, locationsAdjacent.length);
    }

    @Test
    public void theLocationsAdjacentToASizeOneSettlementIsSix() throws Exception {
        givenIHaveASizeOneSettlement();
        whenIGetTheAdjacentLocations();
        thenTheLocationsShouldContain(locationsThatShouldBeAdjacentToOneHexSettlement());
    }

    private void thenTheLocationsShouldContain(Set<Location> expectedLocations) {

        for(Location location : locationsAdjacent) {
            if(expectedLocations.contains(location)) {
                expectedLocations.remove(location);
            }
            else {
                Assert.fail("The Location " + location + " is not adjacent to the settlement given: " + testSettlement);
            }
        }
        Assert.assertEquals(0, expectedLocations.size());
    }

    @Test
    public void theLocationsAdjacentToASizeThreeSettlement() throws Exception {
        givenIHaveASizeThreeSettlement();
        whenIGetTheAdjacentLocations();
        thenTheLocationsShouldContain(locationsThatShouldBeAdjacentToTheTriHexSettlement());
    }

    private Set<Location> locationsThatShouldBeAdjacentToTheTriHexSettlement() {
        Set<Location> locations = new HashSet<Location>();
        locations.add(new Location(-1,0));
        locations.add(new Location(0,-1));
        locations.add(new Location(1,-1));
        locations.add(new Location(-1,1));
        locations.add(new Location(-1,2));
        locations.add(new Location(0,2));
        locations.add(new Location(1,1));
        locations.add(new Location(2,0));
        locations.add(new Location(2,-1));
        return locations;
    }

    private void givenIHaveASizeThreeSettlement() throws Exception {
        testSettlement = new Settlement();
        markPieceInSettlement(new Location(0,0), PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        markPieceInSettlement(new Location(0,1), PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
        markPieceInSettlement(new Location(1,0), PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
    }

    private void markPieceInSettlement(Location location, PlayerID playerID, TypeOfPiece typeOfPiece) throws Exception {
        testSettlement.markPieceInSettlement(location, new GamePiece(playerID, typeOfPiece));
    }

    private void whenIGetTheAdjacentLocations() {
        locationsAdjacent = AdjacentLocationsToSettlement.getLocationsAdjacentToSettlement(testSettlement);
    }

    private void givenIHaveASizeOneSettlement() throws Exception {
        testSettlement = new Settlement();
        testSettlement.markPieceInSettlement(new Location(0,0), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
    }

    private Set<Location> locationsThatShouldBeAdjacentToOneHexSettlement() {
        Set<Location> locations = new HashSet<Location>();
        locations.add(new Location(0,1));
        locations.add(new Location(1,0));
        locations.add(new Location(-1,0));
        locations.add(new Location(0,-1));
        locations.add(new Location(1,-1));
        locations.add(new Location(-1,1));
        return locations;
    }
}
