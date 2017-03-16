package PieceTest;

import Piece.Totoro;
import Player.PlayerID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TotoroTest {
    private Totoro uut;
    private PlayerID totoroOwnerId;

    @Before
    public  void initializeOwner() {
        this.totoroOwnerId = PlayerID.PLAYER_TWO;
    }

    public void givenTheTotoroHasBeenInitialized() {
        this.uut = new Totoro(this.totoroOwnerId);
    }

    public void theTotoroOwnerShouldBe(PlayerID expectedOwnerId) {
        Assert.assertEquals(expectedOwnerId, uut.getOwnerId());
    }

    @Test
    public void theTotoroOwnerShouldBeSetCorrectly() {
        givenTheTotoroHasBeenInitialized();
        theTotoroOwnerShouldBe(this.totoroOwnerId);
    }
}
