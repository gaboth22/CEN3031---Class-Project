package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.MoveNumberParser;
import org.junit.Assert;
import org.junit.Test;

public class MoveNumberParserTest {
    String incomingMessage;
    String parsedMoveNumber;

    @Test
    public void theParserShouldGetTheRightMoveNumber() {
        String message = "MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE 5 PLACE <tile>";
        givenTheStringContainingTheMoveNumberIs(message);
        whenIDoTheParsing();
        thenIShouldGetTheMoveNumber("5");
    }

    private void givenTheStringContainingTheMoveNumberIs(String message) {
        incomingMessage = message;
    }

    private void whenIDoTheParsing() {
        parsedMoveNumber = MoveNumberParser.getMoveNumber(incomingMessage);
    }

    private void thenIShouldGetTheMoveNumber(String expectedMoveNumber) {
        Assert.assertEquals(expectedMoveNumber, parsedMoveNumber);
    }
}
