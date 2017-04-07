package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.RoundCountParser;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class RoundCountParserTest {
    private String messageReceived;
    private String parsedRoundCount;

    @Test
    public void roundCountShouldBeReturnedCorrectly() {
        String message = "NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCH";
        givenTheMessageReceivedIs(message);
        whenIParseTheMessage();
        thenTheRoundCountShouldBe("<rounds>");
    }

    private void givenTheMessageReceivedIs(String message) {
        messageReceived = message;
    }

    private void whenIParseTheMessage() {
        parsedRoundCount = RoundCountParser.getRoundCount(messageReceived);
    }

    private void thenTheRoundCountShouldBe(String expectedCount) {
        Assert.assertEquals(expectedCount, parsedRoundCount);
    }

    @Test
    public void theRoundCountShouldNotBeIncorrect() {
        String message = "NEW CHALLENGE <cid> YOU WILL PLAY 10 MATCH";
        givenTheMessageReceivedIs(message);
        whenIParseTheMessage();
        thenTheRoundCountShouldNotBe("15");
    }

    private void thenTheRoundCountShouldNotBe(String notExpected) {
        Assert.assertNotEquals(notExpected, parsedRoundCount);
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldThrowIfStringIsNull() {
        givenTheMessageReceivedIs(null);
        whenIParseTheMessage();
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldThrowWhenStringIsTooShort() {
        String shortString = "1 2 3 4";
        givenTheMessageReceivedIs(shortString);
        whenIParseTheMessage();
    }
}
