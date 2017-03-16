import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

public class SettlementTest {
    private Settlement uut;

    public void givenTheSettlementIsInitializedAsArrayList() {
        this.uut = new Settlement(new ArrayList<Terrain>());
    }

    public void givenTheSettlementIsInitializedAsLinkedList() {
        this.uut = new Settlement(new LinkedList<Terrain>());
    }

    public void theInnerListShouldBeAnArrayList() {
        Assert.assertTrue(uut.settlement instanceof ArrayList);
    }

    public void theInnerListShouldBeALinkedList() {
        Assert.assertTrue(uut.settlement instanceof LinkedList);
    }

    @Test
    public void theSettlementShouldInitializeCorrectlyUsingArrayList() {
        givenTheSettlementIsInitializedAsArrayList();
        theInnerListShouldBeAnArrayList();
    }

    @Test
    public void theSettlementShouldInitializeCorrectlyUsingLinkedList() {
        givenTheSettlementIsInitializedAsLinkedList();
        theInnerListShouldBeALinkedList();
    }
}
