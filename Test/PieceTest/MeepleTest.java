package PieceTest;

import Piece.Meeple;
import Player.PlayerID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MeepleTest {
    private Meeple uut;
    private PlayerID meepleOwnerId;

    @Before
    public  void initializeOwner() {
        this.meepleOwnerId = PlayerID.PLAYER_ONE;
    }

    public void givenTheMeepleHasBeenInitialized() {
        this.uut = new Piece.Meeple(this.meepleOwnerId);
    }

    public void theMeepleOwnerIdShouldBe(PlayerID expectedOwnerId) {
        Assert.assertEquals(expectedOwnerId, uut.getOwnerId());
    }

    @Test
    public void theMeepleOwnerIdShouldBeSetCorrectly() {
        givenTheMeepleHasBeenInitialized();
        theMeepleOwnerIdShouldBe(this.meepleOwnerId);
    }
}
