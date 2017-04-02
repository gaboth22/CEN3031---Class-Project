package ServerCommTest;

import GameMaster.ServerComm.Parsers.RoundCountParser;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class RoundCountParserTest {
    private String messageReceived;
    private int parsedRoundCount;

    @Test
    public void roundCountShouldBeReturnedCorrectly() {
        String message = "BEGIN ROUND 1 OF 20";
        givenTheMessageReceivedIs(message);
        whenIParseTheMessage();
        thenTheRoundCountShouldBe(20);
    }

    private void givenTheMessageReceivedIs(String message) {
        messageReceived = message;
    }

    private void whenIParseTheMessage() {
        parsedRoundCount = RoundCountParser.getRoundCount(messageReceived);
    }

    private void thenTheRoundCountShouldBe(int expectedCount) {
        Assert.assertEquals(expectedCount, parsedRoundCount);
    }

    @Test
    public void theRounCoundShouldNotBeIncorrect() {
        String message = "BEGIN ROUND 1 OF 10";
        givenTheMessageReceivedIs(message);
        whenIParseTheMessage();
        thenTheRoundCountShouldNotBe(20);
    }

    private void thenTheRoundCountShouldNotBe(int notExpected) {
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
