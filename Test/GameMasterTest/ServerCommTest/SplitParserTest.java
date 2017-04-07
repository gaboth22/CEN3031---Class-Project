package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.Split;
import org.junit.Assert;
import org.junit.Test;

public class SplitParserTest {

    @Test
    public void splitByShouldNotSplitWhenNoPlusIsPresent() {
        String testString = "There is no plus in this string";
        String[] split = Split.byPlus(testString);
        Assert.assertEquals(testString, split[0]);
    }

    @Test
    public void splitByPlusShouldSplitCorrectly() {
        String testString = "a+a+a+a+a";
        String[] split = Split.byPlus(testString);
        for(int i = 0; i < split.length; i++) {
            Assert.assertEquals("a", split[i]);
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullInputShouldThrowNull() {
        String testString = null;
        String[] split = Split.byPlus(testString);
    }
}
