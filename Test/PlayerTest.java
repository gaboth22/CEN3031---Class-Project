import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {
    private Player uut;

    public void givenThePlayerIsInitializedWithId(PlayerID id) {
        this.uut = new Player(id);
    }

    public void theIdOfThePlayerShouldBe(PlayerID expectedId) {
        Assert.assertEquals(expectedId, uut.id);
    }

    @Test
    public void thePlayerIdShouldInitializeCorrectly() {
        givenThePlayerIsInitializedWithId(PlayerID.PLAYER_ONE);
        theIdOfThePlayerShouldBe(PlayerID.PLAYER_ONE);
    }
}
