package SteveTest.PlayGenerationTest;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Movement.*;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.PlayGeneration.SmartTilePlacer.SizeFourSettlementListGetter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class SizeFourSettlementListGetterTest {

    private SizeFourSettlementListGetter setGetter;
    private List<Settlement> allSettlements;
    private Movement mov;
    private int allSettlementCount;
    private int sizeFourSettlementCount;

    @Before
    public void initializeInstances() throws Exception {
        setGetter = new SizeFourSettlementListGetter();
        mov = new AxialMovement();
    }

    @Test
    public void shouldGetTheRightSettlementCountForSizeFourOnlySettlements() throws Exception {
        givenTheTotalSettlementCountIs(5);
        givenThereAreTheseManySizeFourSettlementsInTotal(2);
        whenIConstructAllTheSettlements();
        int expectedNumberOfFoundSizeFourSettlements = 2;
        thenTheSizeFourSettlementListGetterShouldFind(expectedNumberOfFoundSizeFourSettlements);
    }

    private void givenTheTotalSettlementCountIs(int count) {
        allSettlementCount = count;
    }

    private void givenThereAreTheseManySizeFourSettlementsInTotal(int theseMany) {
        sizeFourSettlementCount = theseMany;
    }

    private void whenIConstructAllTheSettlements() throws Exception {
        allSettlements = new ArrayList<>(allSettlementCount);
        List<GamePiece> pieces;

        for(int i = 0; i < allSettlementCount - sizeFourSettlementCount; i++) {
            pieces = getTheseManyGamePieces(2);
            Settlement s = new Settlement();
            insertPiecesIntoSettlement(s, pieces);
            allSettlements.add(s);
        }

        for(int i = 0; i < sizeFourSettlementCount; i++) {
            pieces = getTheseManyGamePieces(4);
            Settlement s = new Settlement();
            insertPiecesIntoSettlement(s, pieces);
            allSettlements.add(s);
        }
    }

    private List<GamePiece> getTheseManyGamePieces(int theseMany) {
        ArrayList<GamePiece> pieces = new ArrayList<>(theseMany);

        for(int i = 0; i < theseMany; i++) {
            pieces.add(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        }

        return pieces;
    }

    private void insertPiecesIntoSettlement(Settlement s, List<GamePiece> pieces) throws Exception {
        Location loc = new Location(0,0);

        for(GamePiece piece : pieces) {
            s.markPieceInSettlement(loc, piece);
            loc = mov.up(loc);
        }
    }

    private void thenTheSizeFourSettlementListGetterShouldFind(int expectedSizeFourCount) {
        List<Settlement> sizeFourSets = setGetter.getSettlementsOfSizeFourOnly(allSettlements);
        Assert.assertEquals(expectedSizeFourCount, sizeFourSets.size());
    }

    @Test
    public void shouldGetTheRightCountWhenTotalCountEqualsAllSettlements() throws Exception {
        givenTheTotalSettlementCountIs(5);
        givenThereAreTheseManySizeFourSettlementsInTotal(5);
        whenIConstructAllTheSettlements();
        int expectedNumberOfFoundSizeFourSettlements = 5;
        thenTheSizeFourSettlementListGetterShouldFind(expectedNumberOfFoundSizeFourSettlements);
    }

    @Test
    public void shouldGetTHeRightCountWhenTotalCountDoesNotHaveAnySizeFourSettlement() throws Exception {
        givenTheTotalSettlementCountIs(10);
        givenThereAreTheseManySizeFourSettlementsInTotal(0);
        whenIConstructAllTheSettlements();
        int expectedNumberOfFoundSizeFourSettlements = 0;
        thenTheSizeFourSettlementListGetterShouldFind(expectedNumberOfFoundSizeFourSettlements);
    }
}
