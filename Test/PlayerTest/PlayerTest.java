package PlayerTest;

import Player.Player;
import Player.PlayerID;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {
    private Player uut;
    private PlayerID id;

    public void givenThePlayerIsInitializedWithId(PlayerID id) {
        this.uut = new Player(id);
        this.id = id;
    }

    public void theIdOfThePlayerShouldBe(PlayerID expectedId) {
        Assert.assertEquals(expectedId, uut.getID());
    }

    @Test
    public void thePlayerIdShouldInitializeCorrectly() {
        givenThePlayerIsInitializedWithId(PlayerID.PLAYER_ONE);
        theIdOfThePlayerShouldBe(this.id);
    }
}
