package ServerCommTest;

import GameMaster.ServerComm.Parsers.GameIdParser;
import org.junit.Assert;
import org.junit.Test;

public class GameIdParserTest {
    private String receivedMessage;
    private String parsedGameId;

    @Test
    public void theParserShouldReturnTheRightForOwnMoveGameId() {
        String message = "MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 3 PLACE <tile>";
        givenTheMessageReceivedIs(message);
        whenIDoTheParsingForOwnMove();
        thenTheGameIdShouldBe("A");
    }

    private void givenTheMessageReceivedIs(String message) {
        receivedMessage = message;
    }

    private void whenIDoTheParsingForOwnMove() {
        parsedGameId = GameIdParser.getGameIdForOwnMove(receivedMessage);
    }

    private void thenTheGameIdShouldBe(String expectedId) {
        Assert.assertEquals(expectedId, parsedGameId);
    }

    @Test
    public void theParserShouldReturnTheRightGameIdForOtherPlayerMove() {
        String message = "GAME B MOVE 10 PLAYER <pid> <place> <build>";
        givenTheMessageReceivedIs(message);
        whenIDoTheParsingForOtherPlayerGameId();
        thenTheGameIdShouldBe("B");
    }

    private void whenIDoTheParsingForOtherPlayerGameId() {
        parsedGameId = GameIdParser.getGameIdForOtherPlayersMove(receivedMessage);
    }

    @Test
    public void theParserShouldNotReturnTheWrongGameIdForOwnMove() {
        String message = "MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 3 PLACE <tile>";
        givenTheMessageReceivedIs(message);
        whenIDoTheParsingForOwnMove();
        thenTheGameIdShouldNotBe("B");
    }

    private void thenTheGameIdShouldNotBe(String notExpected) {
        Assert.assertNotEquals(notExpected, parsedGameId);
    }

    @Test
    public void theParserShouldNotReturnTheWrongGameIdForOtherPlayerMove() {
        String message = "GAME B MOVE 10 PLAYER <pid> <place> <build>";
        givenTheMessageReceivedIs(message);
        whenIDoTheParsingForOtherPlayerGameId();
        thenTheGameIdShouldNotBe("A");
    }
}
