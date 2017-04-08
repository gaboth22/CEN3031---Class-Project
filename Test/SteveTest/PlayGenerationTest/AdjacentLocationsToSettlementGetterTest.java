package SteveTest.PlayGenerationTest;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Movement.*;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.PlayGeneration.SmartTilePlacer.AdjacentLocationsToSettlementGetter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjacentLocationsToSettlementGetterTest {

    private AdjacentLocationsToSettlementGetter adjLocToSetGetter;
    private Settlement settlementOfSize1;
    private Settlement settlementOfSize2;
    private Settlement settlementOfSize3;
    private Movement mov = new AxialMovement();
    private List<Location> expectedAdjacentLocationsToSize1Settlement;
    private List<Location> expectedAdjacentLocationsToSize2Settlement;
    private List<Location> expectedAdjacentLocationsToSize3Settlement;

    @Before
    public void initializeInstances() throws Exception {
        adjLocToSetGetter = new AdjacentLocationsToSettlementGetter();
        settlementOfSize1 = new Settlement();
        settlementOfSize2 = new Settlement();
        settlementOfSize3 = new Settlement();

        Location forSize1Settlement = new Location(0, 0);
        Location forSize2Settlement = new Location(0, 0);
        Location forSize3Settlement = new Location(0, 0);
        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);

        settlementOfSize1.markPieceInSettlement(forSize1Settlement, villager);

        for(int i = 0; i < 2; i++) {
            settlementOfSize2.markPieceInSettlement(forSize2Settlement, villager);
            forSize2Settlement = mov.up(forSize1Settlement);
        }

        for(int i = 0; i < 3; i++) {
            settlementOfSize3.markPieceInSettlement(forSize3Settlement, villager);
            forSize3Settlement = mov.up(forSize3Settlement);
        }

        Location[] adjacentToOrigin = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(forSize1Settlement);
        expectedAdjacentLocationsToSize1Settlement = Arrays.asList(adjacentToOrigin);

        expectedAdjacentLocationsToSize2Settlement = new ArrayList<Location>();
        expectedAdjacentLocationsToSize2Settlement.add(new Location(0, 2));
        expectedAdjacentLocationsToSize2Settlement.add(new Location(1, 1));
        expectedAdjacentLocationsToSize2Settlement.add(new Location(1, 0));
        expectedAdjacentLocationsToSize2Settlement.add(new Location(1, -1));
        expectedAdjacentLocationsToSize2Settlement.add(new Location(0, -1));
        expectedAdjacentLocationsToSize2Settlement.add(new Location(-1, 0));
        expectedAdjacentLocationsToSize2Settlement.add(new Location(-1, 1));
        expectedAdjacentLocationsToSize2Settlement.add(new Location(-1, 2));

        expectedAdjacentLocationsToSize3Settlement = new ArrayList<Location>();
        expectedAdjacentLocationsToSize3Settlement.add(new Location(0, 3));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(1, 2));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(1, 1));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(1, 0));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(1, -1));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(0, -1));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(-1, 0));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(-1, 1));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(-1, 2));
        expectedAdjacentLocationsToSize3Settlement.add(new Location(-1, 3));
    }

    @Test
    public void gottenAdjacentLocationsShouldBeCorrectForSize1Settlement() {
        List<Location> gottenAdjacentLocations = adjLocToSetGetter.getAdjacentLocationsToSettlement(settlementOfSize1);

        Assert.assertEquals(expectedAdjacentLocationsToSize1Settlement.size(), gottenAdjacentLocations.size());
        for(int i = 0; i < expectedAdjacentLocationsToSize1Settlement.size(); i++) {
            Assert.assertTrue(gottenAdjacentLocations.contains(expectedAdjacentLocationsToSize1Settlement.get(i)));
        }
    }

    @Test
    public void gottenAdjacentLocationsShouldBeCorrectForSize2Settlement() {
        List<Location> gottenAdjacentLocations = adjLocToSetGetter.getAdjacentLocationsToSettlement(settlementOfSize2);

        Assert.assertEquals(expectedAdjacentLocationsToSize2Settlement.size(), gottenAdjacentLocations.size());
        for(int i = 0; i < expectedAdjacentLocationsToSize2Settlement.size(); i++) {
            Assert.assertTrue(gottenAdjacentLocations.contains(expectedAdjacentLocationsToSize2Settlement.get(i)));
        }
    }

    @Test
    public void gottenAdjacentLocationsShouldBeCorrectForSize3Settlement() {
        List<Location> gottenAdjacentLocations = adjLocToSetGetter.getAdjacentLocationsToSettlement(settlementOfSize3);

        Assert.assertEquals(expectedAdjacentLocationsToSize3Settlement.size(), gottenAdjacentLocations.size());
        for(int i = 0; i < expectedAdjacentLocationsToSize3Settlement.size(); i++) {
            Assert.assertTrue(gottenAdjacentLocations.contains(expectedAdjacentLocationsToSize3Settlement.get(i)));
        }
    }
}
