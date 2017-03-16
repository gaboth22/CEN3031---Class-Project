import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MeepleTest {
    private Meeple uut;
    private Player meepleOwner;

    @Before
    public  void initializeOwner() {
        this.meepleOwner = new Player(PlayerID.PLAYER_ONE);
    }

    public void givenTheMeepleHasBeenInitialized() {
        this.uut = new Meeple(this.meepleOwner);
    }

    public void theMeepleOwnerShouldBe(Player expectedOwner) {
        Assert.assertEquals(expectedOwner, uut.owner);
    }

    @Test
    public void theMeepleOwnerShouldBeSetCorrectly() {
        givenTheMeepleHasBeenInitialized();
        theMeepleOwnerShouldBe(this.meepleOwner);
    }
}
