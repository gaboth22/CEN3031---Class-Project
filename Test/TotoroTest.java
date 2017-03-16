import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TotoroTest {
    private Totoro uut;
    private Player totoroOwner;

    @Before
    public  void initializeOwner() {
        this.totoroOwner = new Player(PlayerID.PLAYER_TWO);
    }

    public void givenTheTotoroHasBeenInitialized() {
        this.uut = new Totoro(this.totoroOwner);
    }

    public void theTotoroOwnerShouldBe(Player expectedOwner) {
        Assert.assertEquals(expectedOwner, uut.owner);
    }

    @Test
    public void theTotoroOwnerShouldBeSetCorrectly() {
        givenTheTotoroHasBeenInitialized();
        theTotoroOwnerShouldBe(this.totoroOwner);
    }
}
