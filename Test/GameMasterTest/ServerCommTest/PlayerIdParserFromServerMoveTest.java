package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.PlayerIdParserFromServerMove;
import org.junit.Assert;
import org.junit.Test;

public class PlayerIdParserFromServerMoveTest {

    @Test
    public void parserShouldProduceTheRightPlayerId() {
        String received = "GAME <gid> MOVE <#> PLAYER 21 <move>";
        String parsed = PlayerIdParserFromServerMove.getPlayerId(received);
        Assert.assertEquals("21", parsed);
    }
}
