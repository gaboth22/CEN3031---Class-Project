package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.PlayerIdParser;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class PlayerIdParserTest {
    private String messageFromServer;
    private String parsedId;

    @Test
    public void shouldGetTheRightPlayerIdFromTheParser() {
        String messageFromServer = "WAIT FOR THE TOURNAMENT TO BEGIN 1";
        givenTheMessageFromTheServerIs(messageFromServer);
        whenIParseTheMessage();
        thenTheIdShouldBe("1");
    }

    private void givenTheMessageFromTheServerIs(String message) {
        messageFromServer = message;
    }

    private void whenIParseTheMessage() {
        parsedId = PlayerIdParser.getPlayerId(messageFromServer);
    }

    private void thenTheIdShouldBe(String expectedId) {
        Assert.assertEquals(expectedId, parsedId);
    }

    @Test
    public void shouldNotGetTheWrongIdFromParser() {
        String messageFromServer = "WAIT FOR THE TOURNAMENT TO BEGIN 1";
        givenTheMessageFromTheServerIs(messageFromServer);
        whenIParseTheMessage();
        thenTheIdShouldNotBe("3");
    }

    private void thenTheIdShouldNotBe(String notExpected) {
        Assert.assertNotEquals(notExpected, parsedId);
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldThrowIfNullStringIsPassed() {
        PlayerIdParser.getPlayerId(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void shouldThrowIfStringPassedIsNotTheRightLenght() {
        PlayerIdParser.getPlayerId("HELLO 1 2 3");
    }
}