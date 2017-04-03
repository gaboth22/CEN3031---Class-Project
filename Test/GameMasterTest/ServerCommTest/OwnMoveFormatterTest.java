package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.OwnMoveFormatter;
import org.junit.Assert;
import org.junit.Test;

public class OwnMoveFormatterTest {
    private String expected = "GAME B MOVE 5 PLACE <tile> AT <x> <y> <z> " +
                              "<orientation> FOUND SETTLEMENT AT <x> <y> <z>";

    @Test
    public void rightMessageShouldBeConstructed() {
        String steveMove = "PLACE <tile> AT <x> <y> <z> <orientation> FOUND SETTLEMENT AT <x> <y> <z>";
        String gameId = "B";
        String moveNumber = "5";
        String formatted = OwnMoveFormatter.getFormattedMove(steveMove, gameId, moveNumber);
        Assert.assertEquals(expected, formatted);
    }
}
