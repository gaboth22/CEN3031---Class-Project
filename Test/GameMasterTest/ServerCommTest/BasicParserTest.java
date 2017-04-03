package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.BasicParser;
import org.junit.Assert;
import org.junit.Test;

public class BasicParserTest {

    @Test
    public void theBasicParserShouldReturnTheRightString() {
        String messageToParse = "Hello 5 4 3 2 1";
        String parsed = BasicParser.getSubstringFromStringAtIndex(messageToParse, 1);
        Assert.assertEquals(parsed, "5");
    }

    @Test
    public void theBasicShouldReturnTheRightInteger() {
        String messageToParse = "Hello 3 2 Bye 1";
        int parsed = BasicParser.getIntegerFromStringAtIndex(messageToParse, 4);
        Assert.assertEquals(parsed, 1);
    }
}
