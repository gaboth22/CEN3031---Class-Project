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

    @Test
    public void splitBySpaceShouldNotSplitWhenNoSpacesPresent() {
        String testString = "ThereIsNoWayThisStringWillSplit";
        String[] split = Split.byWhiteSpace(testString);
        Assert.assertEquals(testString, split[0]);
    }

    @Test
    public void splitBySpaceShouldSplitCorrectly() {
        String testString = "abcd abcd abcd abcd abcd abcd abcd abcd";
        final int EXPECTED_NUMBER_OF_SPLITS = 8;
        String[] split = Split.byWhiteSpace(testString);
        for(int i = 0; i < EXPECTED_NUMBER_OF_SPLITS; i++) {
            Assert.assertEquals("abcd", split[i]);
        }
    }
}
