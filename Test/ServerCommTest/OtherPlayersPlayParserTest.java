package ServerCommTest;

;import GameMaster.ServerComm.Parsers.OtherPlayersPlayParser;
import org.junit.Assert;
import org.junit.Test;

public class OtherPlayersPlayParserTest {
    private String receivedMessage;
    private String parsedPlay;

    @Test
    public void theRightPlayStringShouldBeParsedForSettlement() {
        String play = "PLACE <tile> AT <x> <y> <z> <orientation> FOUND SETTLEMENT AT <x> <y> <z>";
        String message = "GAME B MOVE 1 PLAYER <pid> " + play;
        givenTheMessageReceivedIs(message);
        whenIDoTheParsing();
        thenTheParsedPlayShouldBe(play);
    }

    private void givenTheMessageReceivedIs(String message) {
        receivedMessage = message;
    }

    private void whenIDoTheParsing() {
        parsedPlay = OtherPlayersPlayParser.getPlay(receivedMessage);
    }

    private void thenTheParsedPlayShouldBe(String expectedPlay) {
        Assert.assertEquals(expectedPlay, parsedPlay);
    }

    @Test
    public void theRightPlayStringShouldBeParsedForExpansion() {
        String play = "PLACE <tile> AT <x> <y> <z> <orientation> EXPAND SETTLEMENT AT <x> <y> <z> <terrain>";
        String message = "GAME B MOVE 1 PLAYER <pid> " + play;
        givenTheMessageReceivedIs(message);
        whenIDoTheParsing();
        thenTheParsedPlayShouldBe(play);
    }

    @Test
    public void theRightPlayStringShouldBeParsedForTotoroSanctuary() {
        String play = "PLACE <tile> AT <x> <y> <z> <orientation> BUILD TOTORO SANCTUARY AT <x> <y> <z>";
        String message = "GAME B MOVE 1 PLAYER <pid> " + play;
        givenTheMessageReceivedIs(message);
        whenIDoTheParsing();
        thenTheParsedPlayShouldBe(play);
    }

    @Test
    public void theRightPlayStringShouldBeParsedForTigerPlayground() {
        String play = "PLACE <tile> AT <x> <y> <z> <orientation> BUILD TIGER PLAYGROUND AT <x> <y> <z>";
        String message = "GAME B MOVE 1 PLAYER <pid> " + play;
        givenTheMessageReceivedIs(message);
        whenIDoTheParsing();
        thenTheParsedPlayShouldBe(play);
    }

    @Test
    public void theRightPlayStringShouldBeParseForUnableToBuild() {
        String play = "PLACE <tile> AT <x> <y> <z> <orientation> UNABLE TO BUILD";
        String message = "GAME B MOVE 1 PLAYER <pid> " + play;
        givenTheMessageReceivedIs(message);
        whenIDoTheParsing();
        thenTheParsedPlayShouldBe(play);
    }

    @Test
    public void theParserShouldNotGetAWrongString() {
        String play = "PLACE <tile> AT <x> <y> <z> <orientation> UNABLE TO BUILD";
        String message = "GAME B MOVE 1 PLAYER <pid> " + play;
        givenTheMessageReceivedIs(message);
        whenIDoTheParsing();
        thenThePlayShouldNotBe("NOT THE PLAY FROM THE STRING");
    }

    private void thenThePlayShouldNotBe(String notExpected) {
        Assert.assertNotEquals(notExpected, parsedPlay);
    }

}
